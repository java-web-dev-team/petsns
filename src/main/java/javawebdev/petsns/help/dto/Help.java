package javawebdev.petsns.help.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class Help {

    private Integer id;
    @Setter private String nickname;
    @Setter private String content;
    @Setter private Boolean check;

    @Setter private LocalDateTime registeredAt;

    public Help(String nickname, String content) {
        this.nickname = nickname;
        this.content = content;
        this.check = false;
        this.registeredAt = LocalDateTime.now();
    }
}
