package com.workfit.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

import java.sql.Timestamp;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
public class Physical {

    @Id
    @GeneratedValue
    @Column(name = "physical_id")
    private  Long id;

    @OneToOne(mappedBy = "physical", fetch = LAZY)
    private Member member;

    private String memberName;
    private Long height;
    private String gender;

    @Builder
    public Physical(Member member, String memberName, Long height, String gender) {
        this.member = member;
        this.memberName = memberName;
        this.height = height;
        this.gender = gender;
    }
}
