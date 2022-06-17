package com.workfit.domain;


import lombok.Data;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private  Long id;
    private String categoryName;
}
