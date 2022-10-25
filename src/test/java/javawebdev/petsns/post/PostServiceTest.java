package javawebdev.petsns.post;

import javawebdev.petsns.post.dto.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    void getList() {
        List<Post> posts = postService.getList();
        int size = posts.size();

        Assertions.assertThat(size).isEqualTo(1);
    }

    @Test
    void register() {
    }

    @Test
    void read() {
    }

    @Test
    void modify() {
    }

    @Test
    void remove() {
    }

    @Test
    void getAttachList() {
    }
}