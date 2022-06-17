package com.workfit.domain;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
public class Exercise {

    @Id
    @GeneratedValue
    @Column(name = "exercise_id")
    private  Long id;
    private String exerciseName;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;


}
