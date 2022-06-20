package com.workfit.api;


import com.workfit.advice.exception.CEmailSigninFailedException;
import com.workfit.advice.exception.CUserNotFoundException;
import com.workfit.config.security.JwtTokenProvider;
import com.workfit.domain.BodyInfo;
import com.workfit.domain.Member;
import com.workfit.domain.Role;
import com.workfit.dto.member.*;
import com.workfit.dto.security.CommonResult;
import com.workfit.dto.security.ListResult;
import com.workfit.dto.security.SingleResult;
import com.workfit.service.MemberService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider; // jwt 토큰 생성
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화


    /**
     * 1. 회원 가입
     * 저장하고자 하는 회원의 정보는 body에 담겨서 온다.
     */
//    @PostMapping("user/signUp")
//    public ResponseEntity<CreateMemberResponse> signUp(@RequestBody @Valid CreateMemberRequest request){
//        Member member = new Member();
//        member.setUserName(request.getName());
//        Long id = memberService.join(member);
//
//        return ResponseEntity.status(HttpStatus.OK).body(new CreateMemberResponse(id));
//    }
    //@ApiOperation(value = "가입", notes = "회원가입을 한다.")
    @PostMapping("user/signUp")
    public CommonResult signUp(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setEmail(request.getEmail());
        member.setPassword(passwordEncoder.encode(request.getPassword()));
        member.setUserName(request.getName());
        member.addRoles(new Role("ROLE_USER"));
        memberService.join(member);

        return memberService.getSuccessResult();
    }

    /**
     * 2. 로그인
     */
    @PostMapping(value = "user/signIn")
    public ListResult<Object> signIn(@RequestBody @Valid LoginMemberRequest request) {
        Member member = memberService.findByEmail(request.getEmail());//.orElseThrow(CEmailSigninFailedException::new);
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            // matches : 평문, 암호문 패스워드 비교 후 boolean 결과 return
            try{
                throw new CEmailSigninFailedException();
            }catch (CEmailSigninFailedException e){
                return memberService.getListResult(Arrays.asList("아이디를 확인 하세요","비밀번호를 다시 입력하세요"));
            }
        }
        BodyInfo bodyInfo;
        if(request.getFlag().equals("no")){
            bodyInfo = member.getBodyInfo();
        }else{
            bodyInfo = new BodyInfo(null, null);
        }
        List<String> roles = member.getRoles().stream().map(role -> new String(role.getRoleName())).collect(Collectors.toList());
        return memberService.getListResult(new ArrayList<Object>(Arrays.asList(bodyInfo,jwtTokenProvider.createToken(String.valueOf(member.getId()),roles))));
    }

    /**
     * 3-1. 모든 회원 정보 조회
     */
//    @GetMapping("user")
//    public ResponseEntity<Result<List<ReadMemberResponse>>> getAllUser(){
//        List<Member> findMembers = memberService.findAll();
//        List<ReadMemberResponse> collect = findMembers.stream()
//                .map(m -> new ReadMemberResponse(m.getUsername()))
//                .collect(Collectors.toList());
//        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(collect.size(), collect));
//    }

//    @GetMapping("user/infos")
//    public ResponseEntity<Result<List<ReadMemberResponse>>> getAllUser(){
//        List<Member> findMembers = memberService.findAll();
//        List<ReadMemberResponse> collect = findMembers.stream()
//                .map(m -> new ReadMemberResponse(m.getUsername()))
//                .collect(Collectors.toList());
//        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(collect.size(), collect));
//    }
//    @Data
//    @AllArgsConstructor
//    static class Result<T>{
//        private int count;
//        private T data;
//    }

    /**
     * 3-2. 단건 회원 정보 조회
     */
//    @GetMapping("user/{id}")
//    public ResponseEntity<ReadMemberResponse> getUser(@PathVariable Long id){
//        Member member = memberService.findOne(id);
//        return ResponseEntity.status(HttpStatus.OK).body(new ReadMemberResponse(member.getUsername()));
//    }
    @GetMapping("user/info")
    public SingleResult<ReadMemberResponse> findUserById() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        Member member = memberService.findByEmail(email);
        // 결과 데이터가 단일건인 경우 getSingleResult를 이용하여 결과를 출력
        return memberService.getSingleResult(new ReadMemberResponse(member));
    }


    /**
     * 4. 회원 정보 수정 API - 이름
     * 수정하고자 하는 회원의 id는 uri로 오고
     * 수정하고자 하는 값은 body로 온다.
     */
    @PatchMapping("user/{id}")
    public ResponseEntity<UpdateMemberResponse> updateMember(@PathVariable("id") Long id , @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return ResponseEntity.status(HttpStatus.OK).body(new UpdateMemberResponse(findMember.getId(), findMember.getUsername()));
    }
}