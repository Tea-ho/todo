package todo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
// CORS (Cross-Origin Resource Sharing) 설정을 담당하는 클래스인 WebMvcConfigurer를 구현하는 클래스

    // 1. 캐시 만료시간 상수 선언 (1시간)
    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**") // 범위: 모든 사이트로 설정
                .allowedOrigins("http://localhost:3000", "http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                // "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS" 메소드 허용
                .allowedHeaders("*") // 해석: 모든 헤더를 허용
                .allowCredentials(true) // 해석: 자격증명 허용
                .maxAge(MAX_AGE_SECS); // 해석: 캐시 만료 시간 지정
    }
}
