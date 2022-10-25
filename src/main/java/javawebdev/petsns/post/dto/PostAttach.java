package javawebdev.petsns.post.dto;

import lombok.Data;

@Data
public class PostAttach {
    private String uuid;
    private String uploadpath;
    private String filename;
    private Integer id;
}
