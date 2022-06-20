package com.workfit.service;

import com.workfit.domain.Member;
import com.workfit.dto.security.CommonResult;
import com.workfit.dto.security.ListResult;
import com.workfit.dto.security.SingleResult;
import com.workfit.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원가입
     */
    @Transactional//변경 default가 false이므로 안적어줌
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member){//회원이 이미 있는지 체크
        List<Member> findMembers =
                memberRepository.findByNames(member.getUsername());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public Member findById(Long memberId){return memberRepository.findById(memberId);}
    public Member findByEmail(String email){return memberRepository.findByEmail(email);}
    public Member findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
    public List<Member> findAll(){
        return  memberRepository.findAll();
    }

    public Member findByName(String username) {
        return memberRepository.findByName(username);
    }

    @Transactional
    public void update(Long id, String name){
        Member member = memberRepository.findById(id);
        member.setUserName(name); //변경감지
    }


    /**
     * security
     */
    @Getter
    @RequiredArgsConstructor
    @AllArgsConstructor
    // enum으로 API 요청 결과에 대한 code, message를 정의한다.
    public enum CommonResponse {
        SUCCESS(0, "성공하였습니다. HappyPepe! XD"),
        FAIL(-1, "실패하였습니다. SadPepe :(");

        private int code;
        private String msg;
    }
    // 단일건 결과를 처리하는 메소드
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    // 다중건 결과를 처리하는 메소드
    public <T> ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }

    // 성공 결과만 처리하는 메소드
    public CommonResult getSuccessResult() { // CommonResult 응답 결과를 알려주는 클래스
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    // 실패 결과만 처리하는 메소드
    public CommonResult getFailResult(int code, String msg) {
        CommonResult result = new CommonResult();
        result.setSuccess(false); // setSuccess : 응답 성공 여부 (true/false)
        result.setCode(code); // setCode : 응답 코드 번호 >= 0 정상, < 0 비정상
        result.setMsg(msg); // setMsg 응답 메시지
        return result;
    }

    // 결과 모델에 API 요청 성공 데이터를 세팅해주는 메소드
    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
}