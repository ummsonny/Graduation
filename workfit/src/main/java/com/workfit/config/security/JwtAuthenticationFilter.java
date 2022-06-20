package com.workfit.config.security;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean{

    private final JwtTokenProvider jwtTokenProvider; // JWT 토큰을 생성 및 검증 모듈 클래스

    // Request로 들어오는 Jwt Token의 유효성을 검증 (jwtTokenProvider.validateToken)하는
    // filter를 filterChain에 등록한다.
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {



        // resolveToken : Request의 Header에서 token 파싱
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
//        System.out.println(token);

        // validateToken : Jwt 토큰의 유효성 + 만료일자 확인
        if (token != null && jwtTokenProvider.validateToken(token)) {

            // getAuthentication : Jwt 토큰으로 인증 정보 조회
            Authentication auth = jwtTokenProvider.getAuthentication(token);

            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}
