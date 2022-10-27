package javawebdev.petsns.member.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {

    private String nickname;
    private String email;

    public SessionMember(Member member) {
        this.nickname = member.getNickname();
        this.email = member.getEmail();
    }
}
