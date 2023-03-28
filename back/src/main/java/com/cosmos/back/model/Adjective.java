package com.cosmos.back.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "adjective")
@Builder
@Getter
@Setter
public class Adjective {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "adjective_id")
    private Long id;

    private String contents; // 내용
}
