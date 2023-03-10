package com.cosmos.back.model.place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name = "sido_code")
@Getter
// 시도
public class Sido {

    @Id
    @Column(name = "code")
    private String sidoCode;

    @Column(name = "name")
    private String sidoName;

    @OneToMany(mappedBy = "sido")
    @JsonIgnore
    private List<Gugun> guguns = new ArrayList<>();
}
