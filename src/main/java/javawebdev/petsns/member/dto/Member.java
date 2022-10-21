package javawebdev.petsns.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@ToString
public class Member {

    private Integer id;
    private String nickname;
    private String password;
    private String introduce;
    private LocalDateTime update_at;
    private String email;

    public Member(){}

    public Member(Integer id, String nickname, String password, String introduce, String email) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.introduce = introduce;
        this.update_at = LocalDateTime.now();
        this.email = email;
    }

}
