package com.workfit.service;


import com.workfit.domain.Member;
import com.workfit.repository.MemberRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    @Test
    public void 회원가입() throws Exception{

        //Given
        Member member = new Member();
        member.setUserName("형수");

        //When
        Long saveId = memberService.join(member);

        //then
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_검증() throws Exception{
        //Given
        Member member1 = new Member();
        member1.setUserName("한준");
        Member member2 = new Member();
        member2.setUserName("한준");

        //when
        memberService.join(member1);
        memberService.join(member2);//예외가 발생한다.

        //Then
        fail("예외가 발생해야 한다.");
    }

}
