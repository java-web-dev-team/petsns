package javawebdev.petsns.post;

import javawebdev.petsns.post.dto.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {
    List<Post> getPostList();
    Optional<Post> findById(Integer id);
    void insert(Post post);
    List<Post> read(Integer id);
    int update(Post post);
    int delete(Integer id);

    List<Post> findAll();
}
