package todo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import todo.domain.dto.ResponseDTO;
import todo.domain.dto.TodoDto;
import todo.domain.dto.UserDto;
import todo.domain.entity.TodoEntity;
import todo.domain.entity.UserEntity;
import todo.security.TokenProvider;
import todo.service.UserService;

@RestController
@RequestMapping("/auth") @Slf4j
public class UserController {
    @Autowired private UserService userService;
    @Autowired private TokenProvider tokenProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    // 1. 유저 등록
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto){
        try{
            // 1. 유효성 검사 진행(null값 X)
            if(userDto == null || userDto.getPassword() == null){
                throw new RuntimeException("Invalid Password value.");
            }
            // 2. request 정보로 유저 객체 생성 후 초기화 작업
            UserEntity user = UserEntity.builder()
                    .username(userDto.getUsername())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .build();
            
            // 3. 서비스 이용하여 리포지터리에 유저 정보 저장 후 반환
            UserEntity registeredUser = userService.create(user);
            UserDto responseUserDto = UserDto.builder()
                    .id(registeredUser.getId())
                    .username(registeredUser.getUsername())
                    .build();
            return ResponseEntity.ok().body(responseUserDto);
        } catch (Exception e){ // 예외 발생 시, error 정보 반환
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .badRequest()
                    .body(responseDTO);
        }
    }

    // 2. 로그인
    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDto userDto){
        
        // 1. Request 정보로 user 정보 찾기 (encoding된 password 비교에 사용)
        UserEntity user = userService.getByCredentials(
                userDto.getUsername(), userDto.getPassword(), passwordEncoder
        );
        // 2. 유효성 검사
        if(user != null){ // 유저 정보 찾으면 저장된 정보 반환
            final String token = tokenProvider.create(user);
            final UserDto responseUserDto = UserDto.builder()
                    .username(user.getUsername())
                    .id(user.getId())
                    .token(token)
                    .build();
            return ResponseEntity.ok().body(responseUserDto);
        } else { // 유저 정보가 없으면, error 처리
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login failed.")
                    .build();
            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
