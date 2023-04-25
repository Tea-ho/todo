package todo.domain.dto;

import todo.domain.entity.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoDto { //

    //할일 pk 번호
    private String id;

    //할일 내용
    private String title;

    //할일 여부
    private boolean done;

    public TodoDto(final TodoEntity todoEntity) {
        this.id = todoEntity.getId();
        this.title = todoEntity.getTitle();
        this.done = todoEntity.isDone();
    }

    //toEntity[저장용]
    public TodoEntity toEntity() {
        return TodoEntity.builder()
                .id(this.id)
                .title(this.title)
                .done(this.done)
                .build();
    }


}