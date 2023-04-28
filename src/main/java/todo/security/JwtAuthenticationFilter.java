package todo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
// JWT 토큰 인증을 위한 필터(JwtAuthenticationFilter)
    @Autowired private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    //  HTTP 요청 헤더에서 토큰 정보를 추출한 후, 토큰이 유효한지 검사
        try{
            // 1. 요청 토큰 가져오기
            String token = parseBearerToken(request);
                log.info("Filter is running.");
            
            // 2. 토큰 검사
            if(token != null && !token.equalsIgnoreCase("null")){
                // 검사 결과 유효하면, userId 가져오기 (위조된 경우 예외 처리)
                String userId = tokenProvider.validateAndGetUserId(token);
                    log.info("Authenticated user ID: " + userId);
                // AbstractAuthenticationToken 객체를 생성한 후, SecurityContextHolder에 등록하는 작업
                // why? SecurityContextHolder는 인증된 사용자 정보를 보관하는 곳으로, 이 정보는 Spring Security의 인증/인가 처리에 사용됨
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId, null, AuthorityUtils.NO_AUTHORITIES
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (Exception e){ // 위조 된 경우, 예외처리
            log.info("Could not set user authentication in security context" + e);
        }
        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request){
    // Authorization 헤더에서 Bearer 토큰을 추출 메소드
        
        String bearerToken = request.getHeader("Authorization");
        // Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴함
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
