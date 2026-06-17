package likelion.todo.domain.todo.entity;

import jakarta.persistence.*;
import likelion.todo.domain.member.entity.Member;
import likelion.todo.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private LocalDateTime date;

    @Column(name = "is_checked")
    private boolean isChecked;

    @Column(length = 20)
    private String emoji;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Todo(String content, LocalDateTime date, Member mebmer) {
        this.content = content;
        this.date = date;
        this.isChecked = isChecked;
        this.emoji = emoji;
        this.member = member;
    }

}
