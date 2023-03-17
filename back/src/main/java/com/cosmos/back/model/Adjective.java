package com.cosmos.back.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "adjective")
@Builder
@Data
public class Adjective {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "adjective_id")
    private Long id;

    private String contents; // 내용
}
