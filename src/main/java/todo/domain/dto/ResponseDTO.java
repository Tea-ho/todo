package todo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
// HTTP 응답으로 사용할 DTO
@Builder@NoArgsConstructor@AllArgsConstructor@Data
public class ResponseDTO<T> {
// HTTP 응답으로 사용할 DTO
// 특이점: 제네릭 타입 T를 사용하여 데이터 목록에 대한 타입을 동적으로 설정할 수 있음.
    private String error; // 에러 메시지
    private List<T> data; // 데이터 목록

}
