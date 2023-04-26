package todo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import todo.domain.dto.ResponseDTO;
import todo.domain.dto.TodoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import todo.domain.entity.TodoEntity;
import todo.service.TodoService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo") @Slf4j
public class TodoController {

    @Autowired private TodoService todoService;

    @GetMapping("/list.do") @CrossOrigin(origins="http://localhost:3000")
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){
        // 1. 서비스 메소드의 retrieve 메소드를 사용해서 todo 리스트 가져오기
        List<TodoEntity> entities = todoService.retrieve(userId);

        // 2. 자바 스트림을 이용하여 리턴된 엔티티 리스트 todoDto 리스트로 변환
        List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());
        // 해석1: entities type은 List<TodoEntity>으로, TodoEntity 객체들이 담긴 리스트를 뜻함
        // 해석2: 이 리스트를 stream() 메소드를 사요아여 스트림으로 변환
        // 해석3: 이후 map() 메소드를 이용하여 스트림 안의 모든 요소에 대해 TodoDto::new 생성자 참조를 적용
        // 해석4: 이 작업을 통해 TodoDto 생성자를 호출하여 TodoEntity 객체를 TodoDto 객체로 변환 진행
        // 해석5: collect() 메소드를 사용하여 변환된 TodoDto 객체를 List<TodoDto> 타입으로 수집
        // 해석6: toList() 메소드를 사용하여 리스트로 수집할 수 있음.
        // 요약: entities 리스트를 스트림으로 변환하고,각 요소에 대해 TodoDto 생성자를 적용하여 TodoEntity 객체를 TodoDto객체로 변환한 뒤,
        // 그 결과를 List<TodoDto>로 수집하여 반환

        // 3. 변환된 todoDto 리스트를 이용하여 ResponseDTO 초기화 진행
        ResponseDTO<TodoDto> response = ResponseDTO.<TodoDto>builder().data(dtos).build();
            System.out.println("list.do result");
            System.out.println(ResponseEntity.ok(response));

        // 4. ResponseDTO 반환
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create.do") @CrossOrigin(origins="http://localhost:3000")
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDto dto){
        log.info("controller post" + dto);
        try{
            TodoEntity entity = dto.toEntity(); // --- 확인 필요
            entity.setId(null); // --- id null로 초기화(생성 당시에는 id가 없어야 함)
            entity.setUserId(userId); // --- Authentication Bearer Token을 통해 받은 userID 전달
            List<TodoEntity> entities = todoService.create(entity);
            List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(java.util.stream.Collectors.toList());
            ResponseDTO<TodoDto> response = ResponseDTO.<TodoDto>builder().data(dtos).build();
            return ResponseEntity.ok(response);
        } catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDto> response = ResponseDTO.<TodoDto>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PutMapping("/update.do") @CrossOrigin(origins="http://localhost:3000")
    public ResponseEntity<?> updateTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDto dto){
        TodoEntity entity = dto.toEntity();
        entity.setUserId(userId);
        List<TodoEntity> entities = todoService.updateTodo(entity);
        List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());
        ResponseDTO<TodoDto> response = ResponseDTO.<TodoDto>builder().data(dtos).build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete.do") @CrossOrigin(origins="http://localhost:3000")
    public ResponseEntity<?> delete(@AuthenticationPrincipal String userId, @RequestBody TodoDto dto){
        try{
            TodoEntity entity = dto.toEntity();
            entity.setUserId(userId);
            List<TodoEntity> entities = todoService.deleteTodo(entity);
            List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());
            ResponseDTO<TodoDto> response = ResponseDTO.<TodoDto>builder().data(dtos).build();
            return ResponseEntity.ok(response);
        } catch (Exception e){
            String error = e.getMessage();
            ResponseDTO<TodoDto> response = ResponseDTO.<TodoDto>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}
