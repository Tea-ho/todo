package todo.service;

import org.springframework.http.ResponseEntity;
import todo.domain.dto.TodoDto;
import todo.domain.entity.TodoEntity;
import todo.domain.entity.TodoEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service @Slf4j
public class TodoService {
    @Autowired
    TodoEntityRepository todoEntityRepository;

    @Transactional
    public List<TodoEntity> create(final TodoEntity entity) {
        validate(entity);
        todoEntityRepository.save(entity);
        log.info("service create : " + entity.getId());
        return todoEntityRepository.findByUserId(entity.getUserId());
    }

    private void validate(final TodoEntity entity) { // 유효성 검사

        if(entity==null){
            log.info("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }

        if(entity.getUserId()==null){
            log.info("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }

    }

    //할일 등록
    @Transactional
    public boolean writeTodo(TodoDto todoDto){
        log.info("service todo post : " +todoDto );
        TodoEntity entity = todoEntityRepository.save(todoDto.toEntity());

        if(entity.getId() != null ){
            return true;
        }

        return false;
    }

    //
    @Transactional
    public List<TodoEntity> retrieve(final String userId){
        return todoEntityRepository.findByUserId(userId);
    }



    //할일 수정
    @Transactional
    public List<TodoEntity> updateTodo(final TodoEntity entity){

        validate(entity); // 유효성 검사 진행

        final Optional<TodoEntity> todoEntityOptional = todoEntityRepository.findById(entity.getId());
        todoEntityOptional.ifPresent(todo -> {

            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
            todoEntityRepository.save(todo);
        });
        return retrieve(entity.getUserId());
    }
    //할일 삭제
    @Transactional
    public List<TodoEntity> deleteTodo(final TodoEntity entity){
        validate(entity); // 유효성 검사 진행
        try{
            todoEntityRepository.delete(entity);
        } catch (Exception e){
            log.info("service delete : " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return retrieve(entity.getUserId());
    }
}