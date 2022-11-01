package javawebdev.petsns.post.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class Post {
    Integer id;
    private String content;
    private String nickname;
    private LocalDateTime registered_at;

    public Post(String content, String nickname) {
        this.content = content;
        this.nickname = nickname;
        this.registered_at = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", nickname='" + nickname + '\'' +
                ", registered_at=" + registered_at +
                '}';
    }


}