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

    @Autowired
    private TodoService todoService;

    @GetMapping("/list.do") @CrossOrigin(origins="http://localhost:3000")
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId){
        // 1. 서비스 메소드의 retrieve 메소드를 사용해서 todo 리스트 가져오기
        List<TodoEntity> entities = todoService.retrieve(userId);
        List<TodoDto> dtos = entities.stream().map(TodoDto::new).collect(Collectors.toList());
        ResponseDTO<TodoDto> response = ResponseDTO.<TodoDto>builder().data(dtos).build();
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
