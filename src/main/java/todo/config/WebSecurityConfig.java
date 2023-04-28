package todo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import todo.security.JwtAuthenticationFilter;


@Configuration @EnableWebSecurity @Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
// Spring Security를 사용하여 웹 보안을 구성하는 Java 파일
    @Autowired private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.cors() // CORS(Cross-Origin Resource Sharing) 활성화
                .and()
                .csrf()
                    .disable() // CSRF(Cross-Site Request Forgery) 공격 방지 기능 비활성화
                .httpBasic()
                    .disable() // token 사용하고 있기 때문에 basic 인증 disable 처리
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 기반 아님을 선언
                .and()
                .authorizeRequests()// 권한 부여 규칙을 설정
                    .antMatchers("/", "/todo/**", "/auth/**").permitAll() // 지정된 경로의 접근을 모두 허용
                .anyRequest()
                    .authenticated(); // 위에 설정한 경로 외 모든 경로 인증 옵션 설정
        http.addFilterBefore( // Spring Security Filter Chain에서 사용할 필터를 추가하는 메소드
                jwtAuthenticationFilter, CorsFilter.class
        ); // JwtAuthenticationFilter 클래스를 CorsFilter 앞에 적용

        /*
        jwtAuthenticationFilter 필터를 CorsFilter 이전에 추가
        1) CorsFilter: CORS (Cross-Origin Resource Sharing) 정책 관련 이슈를 처리하기 위한 필터
        2) JwtAuthenticationFilter: JWT를 사용한 인증 처리를 하는 필터
        JwtAuthenticationFilter 필터를 추가할 때 CORS 처리를 해주지 않으면 클라이언트에서 서버로 API 요청을 보낼 때 CORS 오류가 발생할 수 있음.
        따라서, jwtAuthenticationFilter 필터를 CorsFilter 이전에 추가하여,
        CORS 관련 이슈를 미리 처리하고, JWT 기반 인증 처리를 진행하도록 설정함.
        */
    }
}
