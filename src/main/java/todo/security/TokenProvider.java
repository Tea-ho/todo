package todo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import todo.domain.entity.UserEntity;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service@Slf4j
public class TokenProvider {
    private static final String SECRET_KEY = "FlRpX30pMqDbiAkmlfArbrmVkDD4RqISskGZmBFax5oGVxzXXWUzTR5JyskiHMIV9M10icegkpi46AdvrcX1E6CmTUBc6IFbTPiD";

    public String create(UserEntity userEntity){
        Date expiryDate = Date.from( // 기한 설정: 현재로부터 +1일
                Instant.now().plus(1, ChronoUnit.DAYS)
        );
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY.getBytes())
                .setSubject(userEntity.getId())
                .setIssuer("demo app")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    public String validateAndGetUserId(String token){
        
        // parseClaimsJws 메서드가 Base 64로 디코딩 및 파싱 진행
        // 헤더와 페이로드를 setSigningKey로 넘어온 시크릿을 이용해 서명한 후, token 서명과 비교
        // 위조되지 않았다면, 페이로드 리턴, 위조면 예외 처리
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}
