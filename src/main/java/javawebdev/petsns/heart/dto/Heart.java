package javawebdev.petsns.heart.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
public class Heart {

    private Integer id;
    @Setter private String nickName;
    @Setter private Integer postId;
    @Setter private LocalDateTime heartDate;

    public Heart(String nickName, Integer postId) {
        this.nickName = nickName;
        this.postId = postId;
        this.heartDate = LocalDateTime.now();
    }

}
