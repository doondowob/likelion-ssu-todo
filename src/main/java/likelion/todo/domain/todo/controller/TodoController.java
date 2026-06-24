package likelion.todo.domain.todo.controller;

import likelion.todo.domain.todo.dto.TodoCreateRequestDTO;
import likelion.todo.domain.todo.dto.TodoResponseDTO;
import likelion.todo.domain.todo.dto.TodoReviewRequestDTO;
import likelion.todo.domain.todo.dto.TodoUpdateRequestDTO;
import likelion.todo.domain.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members/{member_id}/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponseDTO> createTodo(
            @PathVariable("member_id") Long memberId,
            @RequestBody TodoCreateRequestDTO req) {

        return ResponseEntity.ok(todoService.createTodo(memberId, req));
    }

    @GetMapping
    public ResponseEntity<List<TodoResponseDTO>> getTodos(
            @PathVariable("member_id") Long memberId) {

        return ResponseEntity.ok(todoService.getTodos(memberId));
    }

    @GetMapping("/daily")
    public ResponseEntity<List<TodoResponseDTO>> getTodosByDate(
            @PathVariable("member_id") Long memberId,
            @RequestParam(value = "month", required = false) Integer month,
            @RequestParam(value = "day", required = false) Integer day) {

        return ResponseEntity.ok(todoService.getTodosByDate(memberId, month, day));
    }

    @PatchMapping("/{todo_id}")
    public ResponseEntity<TodoResponseDTO> updateTodo(
            @PathVariable("member_id") Long memberId,
            @PathVariable("todo_id") Long todoId,
            @RequestBody TodoUpdateRequestDTO req) {

        return ResponseEntity.ok(todoService.updateTodo(memberId, todoId, req));
    }

    @DeleteMapping("/{todo_id}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable("member_id") Long memberId,
            @PathVariable("todo_id") Long todoId) {

        todoService.deleteTodo(memberId, todoId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{todo_id}/reviews")
    public ResponseEntity<TodoResponseDTO> reviewTodo(
            @PathVariable("member_id") Long memberId,
            @PathVariable("todo_id") Long todoId,
            @RequestBody TodoReviewRequestDTO req) {

        return ResponseEntity.ok(todoService.reviewTodo(memberId, todoId, req));
    }

    @PatchMapping("/{todo_id}/check")
    public ResponseEntity<TodoResponseDTO> toggleTodoCheck(
            @PathVariable("member_id") Long memberId,
            @PathVariable("todo_id") Long todoId) {

        return ResponseEntity.ok(todoService.toggleTodoCheck(memberId, todoId));
    }

}