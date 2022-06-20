package com.workfit.dto.member;


import com.workfit.domain.BodyInfo;
import com.workfit.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class ReadMemberResponse {

    private String name;
    private String email;
    private String gender;
    private BodyInfo bodyInfo;
    private List<String> authorities;

    public ReadMemberResponse(Member member){
        this.name = member.getUsername();
        this.email = member.getEmail();
        this.gender = member.getGender();
        this.bodyInfo = member.getBodyInfo();
        this.authorities = member.getAuthorities().stream().map(o->o.getAuthority()).collect(Collectors.toList());
    }

}
