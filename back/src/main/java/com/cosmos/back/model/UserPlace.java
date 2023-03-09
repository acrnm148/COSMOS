package com.cosmos.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.cosmos.back.model.place.Place;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "userplace")
@Builder
@Data
public class UserPlace {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_place_id")
    private Long id;

    // (유저 - 장소) - 유저
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_seq")
    @JsonIgnore
    private User user;

    // (유저 - 장소) - 장소
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "place_id")
    @JsonIgnore
    private Place place;

    // 연관관계 메서드
    public void setUser(User user) {
        this.user = user;
        user.getUserPlaces().add(this);
    }

    // 연관관계 메서드
    public void setPlace(Place place) {
        this.place = place;
        place.getUserPlaces().add(this);
    }

    // 생성 메서드
    public static UserPlace createUserPlace (User user, Place place) {
        UserPlace userPlace = new UserPlace();

        userPlace.setUser(user);
        userPlace.setPlace(place);

        return userPlace;
    }
}
