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

    // 0. 유효성 검사 메소드
    private void validate(final TodoEntity entity) { // 유효성 검사
        if(entity==null){ // empty entity checking
            log.info("Entity cannot be null.");
            throw new RuntimeException("Entity cannot be null.");
        }
        if(entity.getUserId()==null){ // getUserId null 상태 checking
            log.info("Unknown user.");
            throw new RuntimeException("Unknown user.");
        }
    }

    // 1. create 메소드 (기능: todo 입력내용 entity에 저장)
    @Transactional
    public List<TodoEntity> create(final TodoEntity entity) {
        validate(entity);
        // 해석: 들어온 entity 유효성 검사 진행
        todoEntityRepository.save(entity);
        // 해석: entity Repository에 저장(DB저장)
        log.info("service create : " + entity.getId());
        return todoEntityRepository.findByUserId(entity.getUserId());
        // 저장된 값 반환
    }

    // 2. 반환하는 메소드 (기능: userId 받아서 일치하는 entity 반환)
    @Transactional
    public List<TodoEntity> retrieve(final String userId){
        return todoEntityRepository.findByUserId(userId);
    }

    // 3. 업데이트 메소드 (기능: entity 받아서 변경된 내용 적용)
    @Transactional
    public List<TodoEntity> updateTodo(final TodoEntity entity){

        validate(entity);
        // 해석: 유효성 검사 진행

        final Optional<TodoEntity> todoEntityOptional = todoEntityRepository.findById(entity.getId());
        // 해석: null일 때 발생되는 오류 방지하기 위해 포장지로 감싸는 작업 진행
        
        todoEntityOptional.ifPresent(todo -> {
        // 해석: 존재한다면(null이 아니라면), 아래 코드 실행(entity 초기화 작업 및 repository(DB) 수정 작업)
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());
            todoEntityRepository.save(todo);
        });
        return retrieve(entity.getUserId());
        // 해석: 데이터 변경 후 랜더링 진행
    }
    // 4. 삭제 메소드 (기능: entity 정보와 일치하는 entity 삭제)
    @Transactional
    public List<TodoEntity> deleteTodo(final TodoEntity entity){
        validate(entity);
        // 해석: 유효성 검사 진행

        try{ // 해석: 유효성 검사 결과 적합하면, deleting 작업 진행
            todoEntityRepository.delete(entity);
        } catch (Exception e){
            log.info("service delete : " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        } // 해석: 오류 발생 시, 오류 메시지 log로 남기고, RuntimeException에 던지기 처리
        return retrieve(entity.getUserId());
        // 해석: entity 삭제처리하고 랜더링 진행
    }

    // 2. writetodo 메소드 할일 등록 (사용하지 않는 메소드)
    /*
    @Transactional
    public boolean writeTodo(TodoDto todoDto){
        log.info("service todo post : " +todoDto );
        TodoEntity entity = todoEntityRepository.save(todoDto.toEntity());

        if(entity.getId() != null ){
            return true;
        }

        return false;
    }*/
}