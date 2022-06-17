package com.workfit.api;


import com.workfit.domain.Member;
import com.workfit.dto.member.*;
import com.workfit.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    /**
     * 1. 회원 가입
     * 저장하고자 하는 회원의 정보는 body에 담겨서 온다.
     */
    @PostMapping("user/signUp")
    public ResponseEntity<CreateMemberResponse> signUp(@RequestBody @Valid CreateMemberRequest request){
        Member member = new Member();
        member.setUserName(request.getName());
        Long id = memberService.join(member);

        return ResponseEntity.status(HttpStatus.OK).body(new CreateMemberResponse(id));
    }

    /**
     * 2-1. 모든 회원 정보 조회
     */
    @GetMapping("user")
    public ResponseEntity<Result<List<ReadMemberResponse>>> getAllUser(){
        List<Member> findMembers = memberService.findAll();
        List<ReadMemberResponse> collect = findMembers.stream()
                .map(m -> new ReadMemberResponse(m.getUserName()))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(new Result<>(collect.size(), collect));
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data;
    }

    /**
     * 2-2. 회원 정보 조회
     */
    @GetMapping("user/{id}")
    public ResponseEntity<ReadMemberResponse> getUser(@PathVariable Long id){
        Member member = memberService.findOne(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ReadMemberResponse(member.getUserName()));
    }

    /**
     * 3. 회원 정보 수정 API - 이름
     * 수정하고자 하는 회원의 id는 uri로 오고
     * 수정하고자 하는 값은 body로 온다.
     */
    @PatchMapping("user/{id}")
    public ResponseEntity<UpdateMemberResponse> updateMember(@PathVariable("id") Long id , @RequestBody @Valid UpdateMemberRequest request){
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return ResponseEntity.status(HttpStatus.OK).body(new UpdateMemberResponse(findMember.getId(), findMember.getUserName()));
    }


}
