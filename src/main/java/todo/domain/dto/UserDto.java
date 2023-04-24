package todo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import todo.domain.entity.UserEntity;

import javax.persistence.Column;
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String token;

    public UserEntity toEntity(){
        return UserEntity.builder()
                .id(this.id)
                .username(this.username)
                .password(this.password)
                .build();
    }
}
