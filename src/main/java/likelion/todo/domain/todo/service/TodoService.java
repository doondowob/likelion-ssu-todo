package likelion.todo.domain.todo.service;

import likelion.todo.domain.member.entity.Member;
import likelion.todo.domain.member.repository.MemberRepository;
import likelion.todo.domain.todo.dto.TodoCreateRequestDTO;
import likelion.todo.domain.todo.dto.TodoResponseDTO;
import likelion.todo.domain.todo.dto.TodoReviewRequestDTO;
import likelion.todo.domain.todo.dto.TodoUpdateRequestDTO;
import likelion.todo.domain.todo.entity.Todo;
import likelion.todo.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public TodoResponseDTO createTodo(Long memberId, TodoCreateRequestDTO req) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다."));

        Todo todo = Todo.builder()
                .content(req.content())
                .date(req.date())
                .member(member)
                .build();

        todoRepository.save(todo);

        return TodoResponseDTO.from(todo);
    }

    public List<TodoResponseDTO> getTodos(Long memberId) {

        if (!memberRepository.existsById(memberId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다.");
        }

        return todoRepository.findByMemberId(memberId).stream()
                .map(TodoResponseDTO::from)
                .toList();
    }

    public List<TodoResponseDTO> getTodosByDate(Long memberId, Integer month, Integer day) {

        if (!memberRepository.existsById(memberId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다.");
        }

        // month/day 중 하나만 온 경우 → 400
        if ((month == null) != (day == null)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "요청 형식이 올바르지 않습니다.");
        }

        // 둘 다 없으면 → 오늘 날짜로 default
        if (month == null && day == null) {
            LocalDate today = LocalDate.now();
            month = today.getMonthValue();
            day = today.getDayOfMonth();
        }

        return todoRepository.findByMemberIdAndMonthAndDay(memberId, month, day).stream()
                .map(TodoResponseDTO::from)
                .toList();
    }

    private Todo findTodoOfMember(Long memberId, Long todoId) {

        if (!memberRepository.existsById(memberId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "멤버를 찾을 수 없습니다.");
        }

        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "투두를 찾을 수 없습니다."));

        if (!todo.getMember().getId().equals(memberId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 멤버의 투두가 아닙니다.");
        }

        return todo;
    }

    @Transactional
    public TodoResponseDTO updateTodo(Long memberId, Long todoId, TodoUpdateRequestDTO req) {

        Todo todo = findTodoOfMember(memberId, todoId);

        todo.update(req.date(), req.content());

        return TodoResponseDTO.from(todo);
    }

    @Transactional
    public void deleteTodo(Long memberId, Long todoId) {

        Todo todo = findTodoOfMember(memberId, todoId);

        todoRepository.delete(todo);
    }

    @Transactional
    public TodoResponseDTO reviewTodo(Long memberId, Long todoId, TodoReviewRequestDTO req) {

        Todo todo = findTodoOfMember(memberId, todoId);

        todo.review(req.emoji());

        return TodoResponseDTO.from(todo);
    }

    @Transactional
    public TodoResponseDTO toggleTodoCheck(Long memberId, Long todoId) {

        Todo todo = findTodoOfMember(memberId, todoId);

        todo.toggleCheck();

        return TodoResponseDTO.from(todo);
    }

}