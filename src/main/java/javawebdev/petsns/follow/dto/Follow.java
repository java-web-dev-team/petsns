package javawebdev.petsns.follow.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class Follow {

    private Integer id;

    @Setter
    private String following;

    @Setter
    private String follower;

    @Setter
    private LocalDateTime registeredAt;


    public Follow(String following, String follower) {
        this.following = following;
        this.follower = follower;
        this.registeredAt = LocalDateTime.now();
    }

}
