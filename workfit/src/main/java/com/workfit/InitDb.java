package com.workfit;


import com.workfit.domain.*;
import com.workfit.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.ExerCateInit();
        initService.Init2();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService{
        private final EntityManager em;
        private final ExerciseService exerciseService;

        public void ExerCateInit(){

            Category category1 = new Category();
            category1.setCategoryName("복근");
            em.persist(category1);

            Category category2 = new Category();
            category2.setCategoryName("하체");
            em.persist(category2);

            Category category3 = new Category();
            category3.setCategoryName("상체");
            em.persist(category3);

            Category category4 = new Category();
            category4.setCategoryName("전신");
            em.persist(category4);

            String[] arr1 = new String[5];
            arr1[0]="스탠딩 사이드 크런치";
            arr1[1]="스탠딩 니업";
            arr1[2]="라잉 레그 레이즈";
            arr1[3]="바이시클 크런치";
            arr1[4]="크런치";
            for(int i = 0; i<5; i++){
                Exercise exercise = new Exercise();
                exercise.setExerciseName(arr1[i]);
                exercise.setCategory(category1);
                em.persist(exercise);
            }

            String[] arr2 = new String[6];
            arr2[0]="스텝 포워드 다이나믹 런지";
            arr2[1]="스텝 백워드 다이나믹 런지";
            arr2[2]="사이드 런지";
            arr2[3]="크로스 런지";
            arr2[4]="시저크로스";
            arr2[5]="힙쓰러스트";
            for(int i = 0; i<6; i++){
                Exercise exercise = new Exercise();
                exercise.setExerciseName(arr2[i]);
                exercise.setCategory(category2);
                em.persist(exercise);
            }

            String[] arr3 = new String[3];
            arr3[0]="니푸쉬업";
            arr3[1]="푸시업";
            arr3[2]="Y-Exercise";
            for(int i = 0; i<3; i++){
                Exercise exercise = new Exercise();
                exercise.setExerciseName(arr3[i]);
                exercise.setCategory(category3);
                em.persist(exercise);
            }

            String[] arr4 = new String[3];
            arr4[0]="버피 테스트";
            arr4[1]="플랭크";
            arr4[2]="굿모닝";
            for(int i = 0; i<3; i++){
                Exercise exercise = new Exercise();
                exercise.setExerciseName(arr4[i]);
                exercise.setCategory(category4);
                em.persist(exercise);
            }
        }

        public void Init2(){
            Member member1 = new Member();
            member1.setUserName("유형수");
            member1.setBodyInfo(new BodyInfo(90L,180L));
            em.persist(member1);

            //PlanExercise는 cascade가 있으므로 em.persist안해도됨
            PlanExercise planExercise1 = new PlanExercise();
            Exercise exercise = exerciseService.findByName("푸시업");
            planExercise1.setExercise(exercise);
            planExercise1.setReps(20);

            PlanExercise planExercise2 = new PlanExercise();
            Exercise exercise2 = exerciseService.findByName("사이드 런지");
            planExercise2.setExercise(exercise2);
            planExercise2.setReps(20);

            Plan plan1 = new Plan();
            plan1.setMember(member1);
            plan1.addPlanExercise(planExercise1);
            plan1.addPlanExercise(planExercise2);
            plan1.setLocalDate(LocalDate.of(2022, 5, 5));
            em.persist(plan1);

            //==============================================//

            Member member2 = new Member();
            member2.setUserName("이한준");
            member2.setBodyInfo(new BodyInfo(85L,195L));
            em.persist(member2);

            //PlanExercise는 cascade가 있으므로 em.persist안해도됨
            PlanExercise planExercise3 = new PlanExercise();
            Exercise exercise3 = exerciseService.findByName("스탠딩 니업");
            planExercise3.setExercise(exercise3);
            planExercise3.setReps(30);

            PlanExercise planExercise4 = new PlanExercise();
            Exercise exercise4 = exerciseService.findByName("라잉 레그 레이즈");
            planExercise4.setExercise(exercise4);
            planExercise4.setReps(45);

            Plan plan2 = new Plan();
            plan2.setMember(member2);
            plan2.addPlanExercise(planExercise3);
            plan2.addPlanExercise(planExercise4);
            plan2.setLocalDate(LocalDate.of(2022, 7, 15));
            em.persist(plan2);

            //PlanExercise는 cascade가 있으므로 em.persist안해도됨
            PlanExercise planExercise5 = new PlanExercise();
            Exercise exercise5 = exerciseService.findByName("굿모닝");
            planExercise5.setExercise(exercise5);
            planExercise5.setReps(35);

            PlanExercise planExercise6 = new PlanExercise();
            Exercise exercise6 = exerciseService.findByName("플랭크");
            planExercise6.setExercise(exercise6);
            planExercise6.setReps(47);

            Plan plan3 = new Plan();
            plan3.setMember(member2);
            plan3.addPlanExercise(planExercise5);
            plan3.addPlanExercise(planExercise6);
            plan3.setLocalDate(LocalDate.of(2022, 7, 28));
            em.persist(plan3);


        }
    }


}
