package todo.domain.entity;

import org.hibernate.annotations.GenericGenerator;
import todo.domain.BaseTime;
import todo.domain.dto.TodoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "todo")
public class TodoEntity extends BaseTime {
    //할일 번호
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = true) //할일 내용(null값 허용X)
    private String title;

    @Column
    private String userId;

    @Column
    @ColumnDefault("false") //컬럼 기본 값 false(할일 아직 수행X)
    private boolean done;


    //toDto[출력용]
    public TodoDto toDto(){
        return TodoDto.builder()
                .id(this.id)
                .title(this.title)
                .done(this.done)
                .build();
    }
}