package javawebdev.petsns.post.dto;

import javawebdev.petsns.comment.dto.Comment;
import javawebdev.petsns.heart.dto.Heart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostVO {

    private Integer id;
    private String content;
    private String nickname;

    private List<UpdateDTO> updateDTOS;
    private List<Comment> comments;
    private List<Heart> hearts;

    private LocalDateTime registeredAt;

    public static PostVO fromDTO(Post postWithUpdateDTOs, List<Comment> comments, List<Heart> hearts) {
        return new PostVO(
                postWithUpdateDTOs.getId(),
                postWithUpdateDTOs.getContent(),
                postWithUpdateDTOs.getNickname(),
                postWithUpdateDTOs.getImageDTOList(),
                (comments != null) ? comments : new ArrayList<>(),
                (hearts != null) ? hearts : new ArrayList<>(),
                postWithUpdateDTOs.getRegistered_at()
        );
    }
}
