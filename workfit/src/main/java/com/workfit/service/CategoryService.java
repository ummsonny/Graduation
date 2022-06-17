package com.workfit.service;


import com.workfit.domain.Category;
import com.workfit.domain.Exercise;
import com.workfit.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void saveCategory(Category category){ // 저장과 수정 둘다 하는 메서드임
        categoryRepository.save(category);
    }

    public Category findOne(Long categoryId){
        return categoryRepository.findOne(categoryId);
    }

    public Category findByName(String name){
        return categoryRepository.findByName(name);
    }

    @Transactional
    public void remove(Long id){
        categoryRepository.remove(id);
    }
}
