package javawebdev.petsns.comment.dto;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class Comment {

    private Integer id;
    @Setter private String content;
    @Setter private Integer memberId;
    @Setter private Integer postId;
    @Setter private LocalDateTime registeredAt;

    public Comment(Integer memberId, Integer postId, String content) {
        this.content = content;
        this.memberId = memberId;
        this.postId = postId;
        this.registeredAt = LocalDateTime.now();
    }
}
