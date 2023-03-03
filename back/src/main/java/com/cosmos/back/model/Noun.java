package com.cosmos.back.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "noun")
public class Noun {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "noun_id")
    private Long id;

    private String contents; // 내용
}
