package todo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import todo.domain.entity.UserEntity;
import todo.domain.entity.UserRepository;

@Slf4j @Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // user 등록
    public UserEntity create(final UserEntity userEntity){
        // 유효성 검사1: 빈값으로 들어왔을 때 제어
        if(userEntity == null || userEntity.getUsername() == null){
            throw new RuntimeException("Invalid arguments");
        }
        // 유효성 검사2: 중복값 허용X
        final String username = userEntity.getUsername();
        if(userRepository.existsByUsername(username)){
            log.info("Username already exists {}", username);
            throw new RuntimeException("Username already exists");
        }
        return userRepository.save(userEntity);
    }
    
    // 유저 정보 반환 (조건: 이름과 비밀번호 일치 여부)
    public UserEntity getByCredentials(final String username, final String password, final PasswordEncoder encoder){

        final UserEntity entity = userRepository.findByUsername(username);

        if( entity != null && encoder.matches(password, entity.getPassword()) ){
            return entity;
        }
        return null;
    }

}
