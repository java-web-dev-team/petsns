package javawebdev.petsns.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentMapper commentMapper;

    @Transactional
    public List<Comment> getComments(Integer postId) {
        return commentMapper.findByPostId(postId);
    }

    @Transactional
    public Comment create(Integer postId, Integer memberId, String content) {
        Comment comment = new Comment(postId, memberId, content);
        commentMapper.save(comment);
        return comment;
    }

    @Transactional
    public void update(Integer commentId, String content) {
        commentMapper.update(commentId, content);
    }

    @Transactional
    public void delete(Integer commentId) {
        commentMapper.delete(commentId);
    }
}
