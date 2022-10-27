package javawebdev.petsns.post;

import javawebdev.petsns.post.dto.Post;
import javawebdev.petsns.post.dto.PostAttach;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> getList();
    void insert(Post post);
    List<Post> read(Integer id);
    void register(Post post);
    boolean modify(Post post);
    boolean remove(Integer id);
    List<PostAttach> getAttachList(Integer id);


    //post 테이블이 사진테이블을 조인해서 가져와야한다.
}
