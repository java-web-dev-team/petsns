package javawebdev.petsns.post.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@Getter
public class Post {
    private Integer id;
    @Setter private String content;
    @Setter private String nickname;
    @Setter private LocalDateTime registered_at;

    public Post(String content, String nickname) {
        this.content = content;
        this.nickname = nickname;
        this.registered_at = LocalDateTime.now();
    }
}