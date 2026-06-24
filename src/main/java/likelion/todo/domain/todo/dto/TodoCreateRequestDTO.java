package likelion.todo.domain.todo.dto;

import java.time.LocalDateTime;

public record TodoCreateRequestDTO(
        LocalDateTime date,
        String content
) {
}