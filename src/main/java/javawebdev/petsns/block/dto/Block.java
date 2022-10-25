package javawebdev.petsns.block.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class Block {

    private Integer id;
    private String blocker;
    private String blocked;
    private LocalDateTime registeredAt;
    private LocalDateTime canceledAt;

    public Block(String blocker, String blocked) {
        this.blocker = blocker;
        this.blocked = blocked;
        this.registeredAt = LocalDateTime.now();
    }
}
