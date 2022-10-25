package javawebdev.petsns.post;

import javawebdev.petsns.post.dto.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    List<Post> getPostList();
    void insert(Post post);
    Post read(Integer id);
    int update(Post post);
    int delete(Integer id);
}
