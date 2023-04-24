package todo.controller;

import todo.domain.dto.TodoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import todo.service.TodoService;

import java.util.List;

@RestController
@RequestMapping("/todo") @Slf4j
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("") @CrossOrigin(origins="http://localhost:3000")
    public List<TodoDto> get(){
        log.info("get");
        List<TodoDto> todoList = todoService.getTodo();
        return todoList;
    }

    @PostMapping("") @CrossOrigin(origins="http://localhost:3000")
    public boolean post(@RequestBody TodoDto dto){
        log.info("controller post" + dto);
        boolean result = todoService.writeTodo(dto);
        return result;
    }
    @PutMapping("") @CrossOrigin(origins="http://localhost:3000")
    public boolean put(@RequestBody TodoDto dto){
        log.info("controller put" + dto);
        boolean result = todoService.updateTodo(dto);
        return result;
    }

    @DeleteMapping("") @CrossOrigin(origins="http://localhost:3000")
    public boolean delete(@RequestParam int tno){
        log.info("controller delete" + tno);
        boolean result = todoService.deleteTodo(tno);
        return result;
    }
}
