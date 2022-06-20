package com.workfit.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // rest api이므로 기본설정 안함. 기본설정은 비인증 시 로그인 폼 화면으로 리다이렉트 된다.
                .httpBasic().disable()
                // rest api 이므로 csrf 보안이 필요 없음. disable
                .csrf().disable()
                // jwt token으로 생성하므로 세션은 필요 없으므로 생성 안함.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests() // 다음 리퀘스트에 대한 사용권한 체크
                        // 가입 및 인증 주소는 누구나 접근 가능
                        .antMatchers("/*/signIn", "/*/signUp").permitAll()
                        // 모든 회원 조회는 관리자만 가능
                        .antMatchers("/user/infos").hasRole("ADMIN")
                        // 그 외 나머지 요청은 모두 인증된 회원만 접근 가능
                        .anyRequest().hasRole("USER")
                .and()
                    // jwt token 필터를 -> id/password 인증 필터 전에 넣는다.
                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                            UsernamePasswordAuthenticationFilter.class);


    }

}
