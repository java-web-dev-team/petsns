package javawebdev.petsns.post.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Post {
    private Integer id;
    private String content;
    private String nickname;
    private LocalDateTime registered_at;

    private List<UpdateDTO> ImageDTOList;

    public Post(String content, String nickname) {
        this.content = content;
        this.nickname = nickname;
        this.registered_at = LocalDateTime.now();
    }
}