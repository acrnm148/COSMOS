package com.cosmos.back.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
@Builder
@Getter
@Setter
public class Image {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "image_id")
    private Long id;

    @Column(name = "image_url")
    private String imageUrl; // 이미지 링크

    @Column(name = "couple_id")
    private Long coupleId; // 커플ID

    @JsonFormat(pattern = "yyyyMMdd")
    @Column(name = "created_time")
    private String createdTime; // 사진 생성 시간

    // 리뷰 상태 - 리뷰
    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "review_id")
    @JsonIgnore
    private Review review;

    // 연관관계 메서드
    public void setReview(Review review) {
        this.review = review;
        review.getReviewImages().add(this);
    }

    public static Image createImage(String imageUrl, Long coupleId, Review review) {
        Image image = new Image();
        LocalDateTime now = LocalDateTime.now();
        image.setImageUrl(imageUrl);
        image.setCoupleId(coupleId);
        image.setCreatedTime(now.toString().substring(0, 10).replace("-", ""));
        image.setReview(review);

        return image;
    }
}
