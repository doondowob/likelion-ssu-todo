package likelion.todo.domain.todo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import likelion.todo.domain.todo.entity.Todo;

import java.time.LocalDateTime;

public record TodoResponseDTO(
        @JsonProperty("todo_id")
        Long todoId,
        String member,
        LocalDateTime date,
        String content,
        @JsonProperty("is_checked")
        boolean isChecked,
        String emoji
) {
    public static TodoResponseDTO from(Todo todo) {
        return new TodoResponseDTO(
                todo.getId(),
                todo.getMember().getUsername(),
                todo.getDate(),
                todo.getContent(),
                todo.isChecked(),
                todo.getEmoji()
        );
    }
}