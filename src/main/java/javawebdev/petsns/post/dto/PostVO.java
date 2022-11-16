package javawebdev.petsns.post.dto;

import javawebdev.petsns.comment.dto.Comment;
import javawebdev.petsns.heart.dto.Heart;
import javawebdev.petsns.member.dto.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostVO {

    private Integer id;
    private String content;
    private String nickname;
    private Member member;

    private List<UpdateDTO> updateDTOS;
    private List<Comment> comments;
    private List<Heart> hearts;
    private Set<Member> heartMembers;

    private LocalDateTime registeredAt;

    public static PostVO fromDTO(Post postWithUpdateDTOs, Member member, List<Comment> comments, List<Heart> hearts, Set<Member> heartMembers) {
        return new PostVO(
                postWithUpdateDTOs.getId(),
                postWithUpdateDTOs.getContent(),
                postWithUpdateDTOs.getNickname(),
                member,
                postWithUpdateDTOs.getImageDTOList(),
                comments,
                hearts,
                heartMembers,
                postWithUpdateDTOs.getRegistered_at()
        );
    }

}
