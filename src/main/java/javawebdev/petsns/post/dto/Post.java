package javawebdev.petsns.post.dto;

import lombok.Data;
import java.util.Date;

@Data
public class Post {
    private Integer id;
    private String content;
    private String nickname;
    private Date registered_at;
}