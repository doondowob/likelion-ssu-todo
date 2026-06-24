package likelion.todo.domain.todo.dto;

import java.time.LocalDateTime;

public record TodoUpdateRequestDTO(
        LocalDateTime date,
        String content
) {
}