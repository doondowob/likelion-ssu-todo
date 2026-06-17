package likelion.todo.domain.member.service;

import likelion.todo.domain.member.dto.MemberRegisterRequestDTO;
import likelion.todo.domain.member.dto.MemberRegisterResponseDTO;
import likelion.todo.domain.member.entity.Member;
import likelion.todo.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberRegisterResponseDTO registerMember(MemberRegisterRequestDTO req) {

        if(memberRepository.findByUsername(req.username()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 존재하는 아이디입니다.");
        }

        Member member = Member.builder().username(req.username()).password(req.password()).build();

        memberRepository.save(member);

        return new MemberRegisterResponseDTO(member.getId());

    }

}
