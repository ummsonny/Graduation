package com.workfit.domain;

import com.workfit.domain.exercise.ExerciseExample;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Plan {

    @Id
    @GeneratedValue
    @Column(name = "plan_id")
    private  Long id;

    @CreatedDate
    private LocalDate localDate;
    private Long weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // FK로 선언한 것이다 // 여기가 변경되면 member_id FK 값이 다른 멤버로 변경된다
    private Member member;

    @Builder
    public Plan(Long weight, Member member) {
        this.weight = weight;
        this.member = member;
    }

    //    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
//    private List<ExerciseExample> exercises = new ArrayList<>();

    @OneToMany(mappedBy = "plan") // order 테이블의 member에 매핑된 거울일 뿐이다
    private List<Daily> dailies = new ArrayList<>();


    /**
     * 연관관계 메소드
     */

    public void addDailies(Daily daily){
        dailies.add(daily);
        daily.setPlan(this);
    }
}
