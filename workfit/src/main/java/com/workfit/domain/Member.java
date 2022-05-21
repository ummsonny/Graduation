package com.workfit.domain;

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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "physical_id")
    private Physical physical;

    @OneToMany(mappedBy = "member") // order 테이블의 member에 매핑된 거울일 뿐이다
    private List<Plan> plans = new ArrayList<>();

    /**
     * 연관관계 메소드
     */

    public void setPhysical(Physical physical){
        this.physical = physical;
        physical.setMember(this); // Order 객체이므로 바로 setOrder()한다
    }
}
