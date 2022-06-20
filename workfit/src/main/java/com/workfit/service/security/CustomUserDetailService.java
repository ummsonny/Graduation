package com.workfit.service.security;

import com.workfit.advice.exception.CUserNotFoundException;
import com.workfit.domain.Member;
import com.workfit.domain.Role;
import com.workfit.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member loadUserByUsername(String userPk) {//Member대신 원래 userdetails였음
        Member member = memberRepository.findById(Long.valueOf(userPk));//.orElseThrows(CUserNotFoundException::new);
        for(Role role:member.getRoles()){
            role.getRoleName();
        }
        return member;
    }

}
