package todo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data@AllArgsConstructor@NoArgsConstructor@Builder
@Entity@Table(name="todouser")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    @Column(nullable = true)
    private String username;
    @Column(nullable = true)
    private String password;
    @Column
    private String role;
    @Column
    private String authProvider;
}
