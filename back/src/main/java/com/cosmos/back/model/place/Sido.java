package com.cosmos.back.model.place;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "sido_code")
// 시도
public class Sido {

    @Id
    @Column(name = "code")
    private String sidoCode;

    @Column(name = "name")
    private String sidoName;

    @OneToMany(mappedBy = "sido")
    private List<Gugun> guguns = new ArrayList<>();
}
