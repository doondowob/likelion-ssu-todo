package likelion.todo.domain.todo.repository;

import likelion.todo.domain.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    List<Todo> findByMemberId(Long memberId);
    @Query("SELECT t FROM Todo t " +
            "WHERE t.member.id = :memberId " +
            "AND MONTH(t.date) = :month " +
            "AND DAY(t.date) = :day")
    List<Todo> findByMemberIdAndMonthAndDay(@Param("memberId") Long memberId,
                                            @Param("month") int month,
                                            @Param("day") int day);
}