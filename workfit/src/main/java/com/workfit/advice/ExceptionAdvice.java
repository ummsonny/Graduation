package com.workfit.advice;

import com.workfit.advice.exception.CEmailSigninFailedException;
import com.workfit.dto.security.CommonResult;
import com.workfit.repository.MemberRepository;
import com.workfit.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
public class ExceptionAdvice {

    private final MemberService memberService; // 결과에 대한 정보를 도출하는 클래스 명시
    private final MessageSource messageSource;

    @ExceptionHandler(CEmailSigninFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult emailSigninFailed(HttpServletRequest request, CEmailSigninFailedException e) {
        return memberService.getFailResult(Integer.valueOf(getMessage("emailSigninFailed.code")), getMessage("emailSigninFailed.msg"));
    }

    // code 정보에 해당하는 메시지를 조회한다.
    private String getMessage(String code) {
        return getMessage(code, null);
    }

    // code 정보, 추가 argument로 현재 locale에 맞는 메시지를 조회한다.
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}
