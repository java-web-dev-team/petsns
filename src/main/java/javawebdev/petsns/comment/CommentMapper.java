package javawebdev.petsns.comment;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    void save(Comment comment);

    void update(Integer id, String content);

    void delete(Integer id);

    List<Comment> findByPostId(Integer postId);
}
