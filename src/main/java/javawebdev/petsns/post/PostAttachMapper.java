package javawebdev.petsns.post;

import javawebdev.petsns.post.dto.PostAttach;

public interface PostAttachMapper {
    void insert(PostAttach attach);
    void deleteAll(Integer id);
}
