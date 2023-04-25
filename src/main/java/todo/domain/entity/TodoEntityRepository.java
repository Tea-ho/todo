package todo.domain.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoEntityRepository extends JpaRepository<TodoEntity, String>{

    List<TodoEntity> findByUserId(String userId);
    @Query(value = "select * from todo where id = :userId", nativeQuery = true)
    List<TodoEntity> findByUserIdQuery(String userId);
}