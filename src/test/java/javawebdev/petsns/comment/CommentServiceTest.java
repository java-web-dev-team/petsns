package javawebdev.petsns.comment;

import javawebdev.petsns.comment.dto.Comment;
import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.MemberServiceImpl;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.post.PostMapper;
import javawebdev.petsns.post.dto.Post;
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
    CommentService commentService;

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    PostMapper postMapper;

    @Autowired
    MemberServiceImpl memberService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void getCommentsByPostId() throws Exception {
        Member member = memberRepository.findAll().get(0);
        Post post = postMapper.findAll().get(0);

        String content = "content";
        commentService.create(post.getId(), member.getId(), content);
        commentService.create(post.getId(), member.getId(), content);

        List<Comment> comments = commentService.getCommentsByPostId(post.getId());

        assertThat(comments.size()).isEqualTo(2);
    }

    @Test
    void create() throws Exception {
        Member member = memberRepository.findAll().get(0);
        Post post = postMapper.findAll().get(0);

        String content = "content";
        commentService.create(post.getId(), member.getId(), content);

        Comment comment = commentService.getCommentsByPostId(post.getId()).get(0);

        assertThat(comment.getContent()).isEqualTo(content);
        assertThat(comment.getMemberId()).isEqualTo(member.getId());
        assertThat(comment.getPostId()).isEqualTo(post.getId());
    }

    @Test
    void update() throws Exception {
        // given
        Member member = memberRepository.findAll().get(0);
        Post post = postMapper.findAll().get(0);

        String content = "content";
        commentService.create(post.getId(), member.getId(), content);

        Comment comment = commentService.getCommentsByPostId(post.getId()).get(0);

        // when
        String updatedContent = "updatedContent";
        commentService.update(comment.getId(), member.getId(), updatedContent);

        // then
        Comment updatedComment = commentService.getCommentsByPostId(post.getId()).get(0);
        assertThat(updatedComment.getContent()).isEqualTo(updatedContent);
        assertThat(updatedComment.getMemberId()).isEqualTo(member.getId());
        assertThat(updatedComment.getPostId()).isEqualTo(post.getId());
    }

    @Test
    void delete() throws Exception {
        // given
        Member member = memberRepository.findAll().get(0);
        Post post = postMapper.findAll().get(0);

        String content = "content";
        commentService.create(post.getId(), member.getId(), content);

        Comment comment = commentService.getCommentsByPostId(post.getId()).get(0);

        // when
        commentService.delete(post.getId(), comment.getId(), member.getId());

        //then
        List<Comment> comments = commentService.getCommentsByPostId(post.getId());
        assertThat(comments.size()).isEqualTo(0);
    }
}