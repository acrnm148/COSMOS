package com.cosmos.back.model;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "noun")
@Builder
@Getter
@Setter
public class Noun {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "noun_id")
    private Long id;

    private String contents; // 내용

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.id).append(" ").append(this.contents);
        return sb.toString();
    }
}
