package todo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import todo.security.JwtAuthenticationFilter;


@EnableWebSecurity @Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.cors()
                .and()
                .csrf()
                    .disable() // csrf 미사용으로 disable 처리
                .httpBasic()
                    .disable() // token 사용하고 있기 때문에 basic 인증 disable 처리
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 기반 아님을 선언
                .and()
                .authorizeRequests()
                    .antMatchers("/", "/auth/**").permitAll() // 해당 경로 인증 필요없음.
                .anyRequest()
                    .authenticated(); // 위에 설정한 경로 외 모든 경로 인증 옵션 설정
        http.addFilterBefore(
                jwtAuthenticationFilter, CorsFilter.class
        );
    }


}
