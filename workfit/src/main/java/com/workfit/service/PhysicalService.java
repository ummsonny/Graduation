package com.workfit.service;

import com.workfit.controller.dto.PhysicalDto;
import com.workfit.domain.Member;
import com.workfit.domain.Physical;
import com.workfit.repository.MemberRepository;
import com.workfit.repository.PhysicalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhysicalService {

    private final PhysicalRepository physicalRepository;

    private final MemberRepository memberRepository;


    // PhysicalDto
    @Transactional
    public void savePhysical(PhysicalDto physicalDto){

        Member member = memberRepository.getById(physicalDto.getMemberId());

        Physical physical = Physical.builder()
                .memberName(physicalDto.getMemberName())
                .height(physicalDto.getHeight())
                .gender(physicalDto.getGender())
                .member(member)
                .build();

        physicalRepository.save(physical);
    }

    @Transactional
    public void updatePhysical(PhysicalDto physicalDto){

        Member member = memberRepository.getById(physicalDto.getMemberId());

        Physical physical = member.getPhysical();

        if(physicalDto.getMemberName().length() != 0){
            physical.setMemberName(physicalDto.getMemberName());
        }
        if(physicalDto.getHeight() != null){
            physical.setHeight(physicalDto.getHeight());
        }
        if(physicalDto.getGender().length() != 0){
            physical.setGender(physicalDto.getGender());
        }

    }

}
