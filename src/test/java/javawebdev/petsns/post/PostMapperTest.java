package javawebdev.petsns.post;

import javawebdev.petsns.post.dto.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class PostMapperTest {

    @Autowired
    PostMapper postMapper;

    @Test
    void getPostList() {
    }

    @Test
    void insert() {
        String nickname = "nickname";
        String content = "content";
        Post post = new Post(content, nickname);

        postMapper.insert(post);

        List<Post> posts = postMapper.findAll();
        Post findPost = posts.get(0);

        assertThat(findPost.getContent()).isEqualTo(content);
        assertThat(findPost.getNickname()).isEqualTo(nickname);
    }

    @Test
    void read() {
    }

    @Test
    void findById() {

        String nickname = "nickname";
        String content = "content";
        Post post = new Post(content, nickname);

        postMapper.insert(post);

        Post findPost = postMapper.findById(postMapper.findAll().get(0).getId()).get();

        assertThat(findPost.getNickname()).isEqualTo(nickname);
        assertThat(findPost.getContent()).isEqualTo(content);

    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}