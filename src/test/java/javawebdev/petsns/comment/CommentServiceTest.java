package javawebdev.petsns.comment;

import javawebdev.petsns.comment.dto.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class CommentServiceTest {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    CommentService commentService;

    @Test
    void getComments() {
        String content = "content";
        Integer postId = 1;
        Integer memberId = 1;

        commentService.create(postId, memberId, content);
        commentService.create(postId, memberId, content);

        List<Comment> comments = commentService.getComments(postId);

        assertThat(comments.size()).isEqualTo(2);
    }

    @Test
    void create() {
        String content = "content";
        Integer postId = 1;
        Integer memberId = 1;

        Comment comment = commentService.create(postId, memberId, content);

        assertThat(comment.getContent()).isEqualTo(content);
        assertThat(comment.getMemberId()).isEqualTo(memberId);
        assertThat(comment.getPostId()).isEqualTo(postId);
    }

    @Test
    void update() {
        String content = "content";
        Integer postId = 1;
        Integer memberId = 1;

        Comment comment = commentService.create(postId, memberId, content);
        Integer id = comment.getId();

        String modifiedContent = "modifiedContent";
        commentService.update(id, modifiedContent);
        assertThat(commentService.findById(id).getContent()).isEqualTo(modifiedContent);
    }

    @Test
    void delete() {
        String content = "content";
        Integer postId = 1;
        Integer memberId = 1;

        Comment comment = commentService.create(postId, memberId, content);
        Integer id = comment.getId();
        commentService.delete(postId, id);

        List<Comment> comments = commentService.getComments(postId);
        for (Comment c : comments) {
            assertThat(c.getId()).isNotEqualTo(id);
        }
    }
}