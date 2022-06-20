package com.workfit.dto.member;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor //모든 필드값을 이용한 생성자
@NoArgsConstructor //기본 생성자
public class LoginMemberRequest {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String flag;
}
