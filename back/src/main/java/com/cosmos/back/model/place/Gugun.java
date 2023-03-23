package com.cosmos.back.model.place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Table(name = "gugun_code")
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 구군
public class Gugun {

    @Id
    @Column(name = "code")
    private String gugunCode;

    @Column(name = "name")
    private String gugunName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sido_code")
    @JsonIgnore
    private Sido sido;
}

