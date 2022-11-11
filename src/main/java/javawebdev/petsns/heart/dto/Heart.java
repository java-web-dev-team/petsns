package javawebdev.petsns.heart.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class Heart {

    private Integer id;
    private String nickname;
    private Integer postId;
    private LocalDateTime heartDate;

    public Heart(String nickName, Integer postId) {
        this.nickname = nickName;
        this.postId = postId;
        this.heartDate = LocalDateTime.now();
    }

}
