package com.workfit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private  Long id;

    private String email;
    private String password;
    private String provider;
    private String providerId;
    private String userName;
    private String gender;

    @Embedded
    private BodyInfo bodyInfo;


    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Plan> plans = new ArrayList<>();

    /**
     * 연관관계 메소드
     */

    public void addPlans(Plan plan){

        plans.add(plan);
        plan.setMember(this);
    }
}
