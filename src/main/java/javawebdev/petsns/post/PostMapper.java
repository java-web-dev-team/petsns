package javawebdev.petsns.post;

import javawebdev.petsns.post.dto.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    public List<Post> getPostList();
    public void insert(Post post);
    public Post post(Integer id);
    public int update(Post post);
    public int delete(Integer id);
}
