package javawebdev.petsns.post;

import javawebdev.petsns.post.dto.Post;
import javawebdev.petsns.post.dto.PostAttach;

import java.util.List;

public interface PostService {
    public List<Post> getList();
    public void register(Post post);
    public Post read(Integer id);
    public boolean modify(Post post);
    public boolean remove(Integer id);
    public List<PostAttach> getAttachList(Integer id);

    //post 테이블이 사진테이블을 조인해서 가져와야한다.
}
