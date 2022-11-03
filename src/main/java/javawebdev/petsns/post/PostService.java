package javawebdev.petsns.post;

import javawebdev.petsns.post.dto.Post;
import javawebdev.petsns.post.dto.UpdateDTO;

import java.util.List;

public interface PostService {
    List<Post> getList();
    void insert(Post post);
    Post read(Integer id);
    void register(Post post);
    void update(Post post);
    void remove(Post post);

    List<UpdateDTO> ImageDTOList(Integer id);

    //post 테이블이 사진테이블을 조인해서 가져와야한다.
}
