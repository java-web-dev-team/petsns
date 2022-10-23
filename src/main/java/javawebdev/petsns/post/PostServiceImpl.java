package javawebdev.petsns.post;

import javawebdev.petsns.post.dto.Post;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService{

    private PostMapper postMapper;

    @Override
    public void register(Post post) {

    }

    @Override
    public Post get(Integer id) {
        return null;
    }

    @Override
    public boolean modify(Post post) {
        return false;
    }

    @Override
    public boolean remove(Integer id) {
        return false;
    }

    @Override
    public List<PostAttach> getAttachList(Integer id) {
        return null;
    }
}
