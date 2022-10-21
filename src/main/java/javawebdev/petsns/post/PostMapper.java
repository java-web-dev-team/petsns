package javawebdev.petsns.post;

import java.util.List;

public interface PostMapper {
    public List<Post> getPostList();
    public void insert(Post post);
    public Post post(Integer id);
    public int update(Post post);
    public int delete(Integer id);
}
