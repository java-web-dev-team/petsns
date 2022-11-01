package javawebdev.petsns.post;

import javawebdev.petsns.post.dto.Post;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j
public class PostServiceImpl implements PostService{

    private PostMapper postMapper;

    @Override
    public List<Post> getList() {
        return postMapper.getPostList();
    }

    @Override
    public void insert(Post post) {
        log.info("register service " + post);
        postMapper.insert(post);
    }

    @Override
    public Post read(Integer id) {
        return postMapper.findById(id).get();
    }

    @Override
    public void register(Post post) {
        log.info("register" + post);
        postMapper.insert(post);
    }

    @Override
    public void update(Post post) {
        log.info("update" + post);
        postMapper.update(post);
    }

    @Override
    public void remove(Post post) {
        postMapper.remove(post);
    }

}
