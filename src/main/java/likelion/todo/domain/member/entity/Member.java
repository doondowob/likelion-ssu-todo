package likelion.todo.domain.member.entity;

import jakarta.persistence.*;
import likelion.todo.global.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String username;

    @Column(length = 100)
    private String password;

    @Builder
    public Member(String username, String password) {
        this.username = username;
        this.password = password;
  }



}
