package javawebdev.petsns.comment;

import javawebdev.petsns.Validation;
import javawebdev.petsns.comment.dto.Comment;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.post.dto.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentMapper commentMapper;
    private final Validation validation;

    @Transactional
    public Comment findById(Integer id) throws Exception {
        return validation.getCommentOrException(id);
    }

    @Transactional
    public List<Comment> getCommentsByPostId(Integer postId) {
        Post post = validation.getPostOrException(postId);
        return commentMapper.findByPostId(post.getId());
    }

    @Transactional
    public Comment create(Integer postId, Integer memberId, String content) throws Exception {
        Post post = validation.getPostOrException(postId);
        Member member = validation.getMemberOrException(memberId);
        Comment comment = new Comment(member.getId(), post.getId(), content);
        commentMapper.save(comment);
        commentMapper.plusCount(postId);
        return comment;
    }

    @Transactional
    public void update(Integer memberId, Integer postId, Integer commentId, String updatedContent) throws Exception {
        Member member = validation.getMemberOrException(memberId);
        Post post = validation.getPostOrException(postId);
        Comment comment = validation.getCommentOrException(commentId);

        if (validation.isValidAccess(comment, member, post)) {
            comment.setContent(updatedContent);
            commentMapper.update(comment);
        }
    }

    @Transactional
    public void delete(Integer memberId, Integer postId, Integer commentId) throws Exception {
        Member member = validation.getMemberOrException(memberId);
        Comment comment = validation.getCommentOrException(commentId);
        Post post = validation.getPostOrException(postId);

        if (validation.isValidAccess(comment, member, post)) {
            commentMapper.delete(comment.getId());
            commentMapper.minusCount(postId);
        }
    }
}
