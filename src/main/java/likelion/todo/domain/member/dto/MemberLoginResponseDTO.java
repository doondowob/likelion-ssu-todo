package likelion.todo.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MemberLoginResponseDTO (

        @JsonProperty("member_id")
        Long memberId

){
}
