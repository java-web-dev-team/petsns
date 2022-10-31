package javawebdev.petsns.follow.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Follow {

    private Integer id;

    @Setter
    private Integer followingId;

    @Setter
    private Integer followerId;

    @Setter
    private LocalDateTime registeredAt;


    public Follow(Integer followingId, Integer followerId) {
        this.followingId = followingId;
        this.followerId = followerId;
        this.registeredAt = LocalDateTime.now();
    }

}
