package com.workfit.repository;


import com.workfit.domain.Exercise;
import com.workfit.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.lang.annotation.Retention;

@Repository
@RequiredArgsConstructor
public class ExerciseRepository {

    private final EntityManager em;

    public void save(Exercise exercise){
        em.persist(exercise);
    }

    public Exercise findOne(Long id){
        return em.find(Exercise.class, id);
    }

    public Exercise findByName(String name){
        return em.createQuery("select e from Exercise e where e.exerciseName =: name", Exercise.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public void remove(Long id){
        Exercise ex = em.find(Exercise.class, id);
        em.remove(ex);
    }
}
