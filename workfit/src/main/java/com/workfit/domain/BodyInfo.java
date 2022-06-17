package com.workfit.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class BodyInfo {

    private Long weight;
    private Long height;


    protected BodyInfo(){}

    public BodyInfo(Long weight, Long height) {
        this.weight = weight;
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BodyInfo bodyInfo = (BodyInfo) o;
        return Objects.equals(getWeight(), bodyInfo.getWeight()) && Objects.equals(getHeight(), bodyInfo.getHeight());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getWeight(), getHeight());
    }
}
