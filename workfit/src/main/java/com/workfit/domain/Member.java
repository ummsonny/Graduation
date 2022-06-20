package com.workfit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter @Setter
public class Member implements UserDetails {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private  Long id;

    private String email;
    private String password;
    private String userName;

    private String gender;
    @Embedded
    private BodyInfo bodyInfo;

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Plan> plans = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member",cascade = CascadeType.ALL)
    private List<Role> roles = new ArrayList<>(); // 회원이 가지고 있는 권한 정보들

    /**
     * 연관관계 메소드
     */
    public void addPlans(Plan plan){

        plans.add(plan);
        plan.setMember(this);
    }
    public void addRoles(Role role){
        roles.add(role);
        role.setMember(this);
    }

    /**
     * 보안관련 메서드
     */
    @Override //여기 json나감
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() { //security에서 사용하는 회원 상태 값이다.
        return this.userName;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
