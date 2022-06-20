package com.workfit.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Plan {

    @Id
    @GeneratedValue
    @Column(name = "plan_id")
    private  Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<PlanExercise> planExercises = new ArrayList<>();

    private Long max_weight;
    private Long min_weight;

    @CreatedDate
    private LocalDate localDate;

    private Double bmi;
//    @Embedded
//    private BodyInfo bodyInfo;


    /**
     * 연관관계 메소드
     */
    public void addPlanExercise(PlanExercise planExercise){
        planExercises.add(planExercise);
        planExercise.setPlan(this);
    }

    public void update(int reps) {
        for(PlanExercise planExercise : planExercises){
            planExercise.update(reps);
        }
    }

    /**
     * 계획은 생성이 단순한게 아니라
     *  멤버 , 계획운동 등 연관관계가 복잡한 상태이다.
     * 이럴때 createPlan처럼 별도 생성메서드가 있으면 좋다.
     *  앞으로 뭔가 생성하는 부분을 바꿔야 한다면 여기만 수정하면 되므로 아주 간단해짐
     *  setter로 복잡하게 밖에서 할 필요가 없다.
     */
    //==생성 메서드==//
    public static Plan createOrder(Member member, List<PlanExercise> planExercises, LocalDate localDate){
        Plan plan = new Plan();
        plan.setMember(member);

        for(PlanExercise planExercise : planExercises){
            plan.addPlanExercise(planExercise);
        }
        plan.setLocalDate(localDate);

        Double b = Math.pow(BigDecimal.valueOf(member.getBodyInfo().getHeight()).divide(new BigDecimal(100),2, RoundingMode.HALF_EVEN).doubleValue(),2);
        Double result = BigDecimal.valueOf(member.getBodyInfo().getWeight()).divide(new BigDecimal(b),2,RoundingMode.HALF_EVEN).doubleValue();
        plan.setBmi(result); //이거 오류난다.

        return plan;
    }
}
