package com.workfit.repository;


import com.workfit.domain.Category;
import com.workfit.domain.Exercise;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final EntityManager em;

    public void save(Category category){
        em.persist(category);
    }

    public Category findOne(Long id){
        return em.find(Category.class, id);
    }

    public Category findByName(String name){
        return em.createQuery("select c from Category c where c.categoryName =: name", Category.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    public void remove(Long id){
        Category category = em.find(Category.class, id);
        em.remove(id);
    }
}
