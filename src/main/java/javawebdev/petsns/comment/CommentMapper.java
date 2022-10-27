package javawebdev.petsns.comment;

import javawebdev.petsns.comment.dto.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {

    Optional<Comment> findById(Integer id);

    // save
    void save(Comment comment);

    void plusCount(Integer postId);
    // save-end

    void update(Comment comment);

    // delete
    void delete(Integer id);

    void minusCount(Integer postId);
    // delete-end

    List<Comment> findByPostId(Integer postId);
}
