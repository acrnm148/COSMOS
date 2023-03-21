package com.cosmos.back.model.place;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Builder
@Table(name = "sido_code")
@Getter
@NoArgsConstructor
@AllArgsConstructor
// 시도
public class Sido {

    @Id
    @Column(name = "code")
    private String sidoCode;

    @Column(name = "name")
    private String sidoName;

    /*
    @OneToMany(mappedBy = "sido")
    @JsonIgnore
    private List<Gugun> guguns = new ArrayList<>();
    */
}
