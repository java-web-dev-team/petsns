package javawebdev.petsns.comment;

import javawebdev.petsns.comment.dto.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    // save
    void save(Comment comment);

    void plusCount(Integer postId);
    // save-end

    void update(Integer id, String content);

    // delete
    void delete(Integer id);

    void minusCount(Integer postId);
    // delete-end

    List<Comment> findByPostId(Integer postId);
}
