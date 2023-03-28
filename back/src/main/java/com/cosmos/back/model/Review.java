package com.cosmos.back.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "review")
@Builder
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private String contents; // 내용

    private Integer score; // 별점

    private String nickname; // 닉네임

    @JsonFormat(pattern = "yyyyMMdd")
    @Column(name = "created_time")
    private String createdTime; // 리뷰 생성 시간

    private String img1; // 이미지 1

    private String img2; // 이미지 1

    private String img3; // 이미지 1

    // 리뷰 내용 공개 여부
    @Column(name = "contents_open")
    private Boolean contentsOpen;

    // 리뷰 이미지 공개 여부
    @Column(name = "image_open")
    private Boolean imageOpen;

    // 리뷰 - 유저
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_seq")
    @JsonIgnore
    private User user;

    // 리뷰 - (리뷰 - 장소)
    @OneToMany(mappedBy = "review")
    private List<ReviewPlace> reviewPlaces = new ArrayList<>();

    // 리뷰 - 리뷰 상태(공통)
    @OneToMany(mappedBy = "review")
    private List<ReviewCategory> reviewCategories = new ArrayList<>();

    // 리뷰 - 리뷰 상태(개별)
    @OneToMany(mappedBy = "review")
    private List<IndiReviewCategory> indiReviewCategories = new ArrayList<>();

    // 연관관계 메서드
    public void setCreateUser(User user) {
        this.user = user;
        user.getReviews().add(this);
    }

    // 생성 메서드
    public static Review createReview (User user, String contents, Integer score, String formatedNow, List<String> urls, String nickName, Boolean contentsOpen, Boolean imageOpen) {
        Review review = new Review();

        review.setCreateUser(user);
        review.setContents(contents);
        review.setScore(score);
        review.setCreatedTime(formatedNow);
        review.setNickname(nickName);
        review.setContentsOpen(contentsOpen);
        review.setImageOpen(imageOpen);

        int urlSize = urls.size();
        for (int i = 0 ; i < urlSize; i++) {
            if (i == 0) {
                review.setImg1(urls.get(i));
            } else if (i == 1) {
                review.setImg2(urls.get(i));
            } else {
                review.setImg3(urls.get(i));
            }
        }

        return review;
    }

}
