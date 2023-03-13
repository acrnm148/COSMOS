package com.cosmos.back.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
@Builder
@Data
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

    public static Image createImage(String imageUrl, Long coupleId) {
        Image image = new Image();
        LocalDateTime now = LocalDateTime.now();


        image.setImageUrl(imageUrl);
        image.setCoupleId(coupleId);
        image.setCreatedTime(now.toString().substring(0, 10).replace("-", ""));

        return image;
    }
}
