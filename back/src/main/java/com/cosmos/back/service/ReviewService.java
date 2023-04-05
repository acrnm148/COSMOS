package com.cosmos.back.service;

import com.cosmos.back.annotation.RedisCached;
import com.cosmos.back.annotation.RedisCachedKeyParam;
import com.cosmos.back.annotation.RedisEvict;
import com.cosmos.back.dto.request.ReviewRequestDto;
import com.cosmos.back.dto.response.review.ReviewResponseDto;
import com.cosmos.back.dto.response.review.ReviewUserResponseDto;
import com.cosmos.back.model.*;
import com.cosmos.back.model.place.Place;
import com.cosmos.back.repository.image.ImageRepository;
import com.cosmos.back.repository.place.PlaceRepository;
import com.cosmos.back.repository.review.*;
import com.cosmos.back.repository.reviewplace.ReviewPlaceRepository;
import com.cosmos.back.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewCategoryRepository reviewCategoryRepository;
    private final IndiReviewCategoryRepository indiReviewCategoryRepository;
    private final PlaceRepository placeRepository;
    private final ReviewPlaceRepository reviewPlaceRepository;
    private final ReviewAdjectiveRepository reviewAdjectiveRepository;
    private final ReviewNounRepository reviewNounRepository;
    private final S3Service s3Service;
    private final ImageRepository imageRepository;
    private final NotificationService notificationService;

    //리뷰 쓰기
    @Transactional
    @RedisEvict(key = "review")
    public Long createReview(ReviewRequestDto dto, @RedisCachedKeyParam(key = "userSeq") Long userSeq) {

        User user = userRepository.findById(userSeq).orElseThrow(() -> new IllegalArgumentException("no such data")); // 유저
        Place place = placeRepository.findById(dto.getPlaceId()).orElseThrow(() -> new IllegalArgumentException("no such data"));

        String nickName = findReviewNickName(); // 닉네임

        LocalDate now = LocalDate.now(); // 현재 날짜 구하기 (시스템 시계, 시스템 타임존)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd"); // 포맷 작성
        String formatedNow = now.format(formatter); // 포맷으로 날짜 변경

        Review review = Review.createReview(user, dto.getContents(), dto.getScore(), formatedNow, nickName, dto.getContentsOpen(), dto.getImageOpen());
        Review new_review = reviewRepository.save(review);

        // image 테이블에 이미지 삽입
        for (int i = 0; i < dto.getImageUrls().size(); i++) {
            Image image = Image.createImage(dto.getImageUrls().get(i), user.getCoupleId(), review);
            imageRepository.save(image);
        }

        ReviewPlace reviewPlace = ReviewPlace.createReviewPlace(review, place);
        reviewPlaceRepository.save(reviewPlace);

        if (dto.getCategories() != null) {
            for (String category : dto.getCategories()) {
                ReviewCategory reviewCategory = ReviewCategory.createReviewCategory(category, review);
                reviewCategoryRepository.save(reviewCategory);
            }
        }

        if (dto.getIndiCategories() != null) {
            for (String category : dto.getIndiCategories()) {
                IndiReviewCategory indiReviewCategory = IndiReviewCategory.createIndiReviewCategory(category, review);
                indiReviewCategoryRepository.save(indiReviewCategory);
            }
        }

        // 알림 전송
        if (user.getCoupleUserSeq() != null && user.getCoupleUserSeq() != 0) {
            notificationService.send("makeReview", user.getCoupleUserSeq(), user.getUserName() + "님이 리뷰를 등록하셨습니다. ");
        }

        return new_review.getId();
    }

    // 리뷰 삭제하기
    @Transactional
    @RedisEvict(key = "review")
    public Long deleteReview (Long reviewId, @RedisCachedKeyParam(key = "userSeq") Long userSeq) {
        // 카테고리 삭제(공통)(1)
        Long executeReviewCategory = reviewRepository.deleteReviewCategoryQueryDsl(reviewId);
        // 카테고리 삭제(개별)(2)
        Long executeIndiReviewCategory = reviewRepository.deleteIndiReviewCategoryQueryDsl(reviewId);
        // 리뷰와 장소 연결 삭제(3)
        Long executeReviewPlace = reviewRepository.deleteReviewPlaceQueryDsl(reviewId);
        // S3내의 이미지 삭제 및 image 테이블 삭제
        List<Image> imageList = imageRepository.findByReviewId(reviewId);

        for (Image image : imageList) {
            System.out.println("image = " + image);
            String imageUrl = image.getImageUrl();
            String[] urls = imageUrl.split("/");
            String fileName = urls[urls.length - 1];
            s3Service.deleteFile(fileName);
//            imageRepository.deleteById(image.getId());
        }

        // 리뷰와 이미지 연결 삭제(4)
        Long executeReviewImages = reviewRepository.deleteReviewImagesQueryDsl(reviewId);

        // 리뷰 내용 삭제(5)
        Long executeReview = reviewRepository.deleteReviewQueryDsl(reviewId);



        // 존재하지 않는 review 일 때 error 처리
        if (executeReview == 0) {
            throw new IllegalStateException("존재하지 않는 리뷰입니다.");
        }

        return executeReview;
    }

    // 커플 및 유저의 특정 장소에 대한 리뷰 불러오기
    public List<ReviewResponseDto> findReviewsInPlaceUserCouple (Long userSeq, Long coupleId, Long placeId, Integer limit, Integer offset) {
        List<ReviewResponseDto> reviews = new ArrayList<>();

        // 커플일 경우
        if (coupleId != null) {
            // 커플에 해당하는 유저를 조회
            List<User> users = userRepository.findByCoupleId(coupleId);

            // 해당 커플의 유저를 한 명씩 돌면서 각자쓴 리뷰를 합친다.
            for (User u : users) {
                List<Review> data = reviewRepository.findReviewInPlaceUserCoupleQueryDsl(u.getUserSeq(), placeId, limit, offset);
                    for (Review r : data) {
                        ReviewResponseDto dto = ReviewResponseDto.builder()
                                .reviewId(r.getId())
                                .categories(r.getReviewCategories())
                                .indiReviewCategories(r.getIndiReviewCategories())
                                .score(r.getScore())
                                .contents(r.getContents())
                                .userId(u.getUserSeq())
                                .nickname(r.getNickname())
                                .images(r.getReviewImages())
                                .createdTime(r.getCreatedTime())
                                .contentsOpen(r.getContentsOpen())
                                .imageOpen(r.getImageOpen())
                                .build();
                        reviews.add(dto);
                    }
            }
        } else { // 솔로일 경우 자신의 리뷰를 불러온다.
            List<Review> data = reviewRepository.findReviewInPlaceUserCoupleQueryDsl(userSeq, placeId, limit, offset);
            for (Review r : data) {
                ReviewResponseDto dto = ReviewResponseDto.builder()
                        .reviewId(r.getId())
                        .categories(r.getReviewCategories())
                        .indiReviewCategories(r.getIndiReviewCategories())
                        .score(r.getScore())
                        .contents(r.getContents())
                        .userId(userSeq)
                        .nickname(r.getNickname())
                        .createdTime(r.getCreatedTime())
                        .images(r.getReviewImages())
                        .contentsOpen(r.getContentsOpen())
                        .imageOpen(r.getImageOpen())
                        .build();
                reviews.add(dto);
            }
        }
        return reviews;
    }


    // 장소별 리뷰 모두 불러오기
    public List<ReviewResponseDto> findReviewsInPlace (Long placeId, Integer limit, Integer offset) {
        List<Review> review = reviewRepository.findReviewInPlaceQueryDsl(placeId, limit, offset);

        List<ReviewResponseDto> list = new ArrayList<>();
        // ReviewResponseDto에 맞게 데이터 정제하여 List에 추가한다
        for (Review r : review) {
            ReviewResponseDto dto = ReviewResponseDto.builder()
                    .reviewId(r.getId())
                    .categories(r.getReviewCategories())
                    .indiReviewCategories(r.getIndiReviewCategories())
                    .score(r.getScore())
                    .contents(r.getContents())
                    .userId(r.getUser().getUserSeq())
                    .nickname(r.getNickname())
                    .createdTime(r.getCreatedTime())
                    .images(r.getReviewImages())
                    .contentsOpen(r.getContentsOpen())
                    .imageOpen(r.getImageOpen())
                    .build();
            list.add(dto);
        }

        return list;
    }

    // 내 리뷰 모두 불러오기
    @RedisCached(key = "review", expire = 240)
    public List<ReviewUserResponseDto> findReviewsInUser (@RedisCachedKeyParam(key = "userSeq") Long userSeq, Integer limit, Integer offset) {
        List<Review> review = reviewRepository.findReviewInUserQueryDsl(userSeq, limit, offset);

        List<ReviewUserResponseDto> list = new ArrayList<>();
        // Dto 형식에 맞춰서 Review 내용 중에 맞는 것을 골라 넣는다
        for (Review r : review) {
            ReviewUserResponseDto dto = ReviewUserResponseDto.builder()
                    .reviewId(r.getId())
                    .categories(r.getReviewCategories())
                    .indiReviewCategories(r.getIndiReviewCategories())
                    .score(r.getScore())
                    .contents(r.getContents())
                    .placeId(r.getReviewPlaces().get(0).getPlace().getId())
                    .images(r.getReviewImages())
                    .nickName(r.getNickname())
                    .createdTime(r.getCreatedTime())
                    .contentsOpen(r.getContentsOpen())
                    .imageOpen(r.getImageOpen())
                    .build();
            list.add(dto);
        }

        return list;
    }

    // 리뷰 수정
    @RedisEvict(key = "review")
    @Transactional
    public Long changeReview (Long reviewId, ReviewRequestDto dto, @RedisCachedKeyParam(key = "userSeq") Long userSeq) {
        User user = userRepository.findByUserSeq(userSeq);
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("no such data"));

        review.setContents(dto.getContents());
        review.setScore(dto.getScore());

        review.setContentsOpen(dto.getContentsOpen());
        review.setImageOpen(dto.getImageOpen());

        List<ReviewCategory> list = reviewCategoryRepository.findAllByReviewId(reviewId);
        List<IndiReviewCategory> list_indi = indiReviewCategoryRepository.findAllByReviewId(reviewId);

        // 사진 삭제 및 첨부
        List<Image> images = imageRepository.findByReviewId(reviewId);

        // 현재 존재하는 이미지들
        boolean[] imagesArray = new boolean[images.size()];
        // 새로 들어온 이미지들
        boolean[] dtoArray = new boolean[dto.getImageUrls().size()];

        // 같은 이미지 체크
        for (int i = 0; i < images.size(); i++) {
            for (int j = 0; j < dtoArray.length; j++) {
                if (images.get(i).getImageUrl().equals(dto.getImageUrls().get(j))) {
                    imagesArray[i] = true;
                    dtoArray[j] = true;
                    break;
                }
            }
        }

        // imagesArray false -> 기존에 있지만 새로운 것에는 없음 -> 삭제
        for (int i = 0; i < imagesArray.length; i++) {
            if (!imagesArray[i]) {
                String[] urls = images.get(i).getImageUrl().split("/");
                String fileName = urls[urls.length - 1];
                s3Service.deleteFile(fileName);
            }
        }

        // dtoArray false -> 기존에 없지만 새로운 것에는 있음 -> 저장
        for (int j = 0; j < dtoArray.length; j++) {
            if (!dtoArray[j]) {
                Image image = Image.createImage(dto.getImageUrls().get(j), user.getCoupleId(), review);
                imageRepository.save(image);
            }
        }

        for (ReviewCategory rc : list) {
            reviewCategoryRepository.deleteById(rc.getId());
        }

        for (String category : dto.getCategories()) {
            ReviewCategory reviewCategory = ReviewCategory.createReviewCategory(category, review);
            reviewCategoryRepository.save(reviewCategory);
        }

        for (IndiReviewCategory irc : list_indi) {
            indiReviewCategoryRepository.deleteById(irc.getId());
        }

        for (String category : dto.getIndiCategories()) {
            IndiReviewCategory indiReviewCategory = IndiReviewCategory.createIndiReviewCategory(category, review);
            indiReviewCategoryRepository.save(indiReviewCategory);
        }

        return review.getId();
    }

    // 리뷰 닉네임 생성하기
    public String findReviewNickName () {
        List<Adjective> adjectives = reviewAdjectiveRepository.findAll();
        List<Noun> nouns = reviewNounRepository.findAll();

        // 인덱스로 랜덤하게 추출하기 위해 배열 크기 측정
        Integer adjectivesSize = adjectives.size();
        Integer nounsSize = nouns.size();

        // 랜덤으로 형용사 명사의 인덱스를 가져온다.
        Integer randomAdjectiveIdx = (int) (Math.random() * adjectivesSize);
        Integer randomNounIdx = (int) (Math.random() * nounsSize);

        // 해당 숫자를 활용해서 각 테이블에서 문자 추출
        String randomAdjective = adjectives.get(randomAdjectiveIdx).getContents();
        String randomNoun = nouns.get(randomNounIdx).getContents();

        return randomAdjective + " " + randomNoun;
    }
}
