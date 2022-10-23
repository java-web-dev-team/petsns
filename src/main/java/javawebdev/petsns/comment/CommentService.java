package javawebdev.petsns.comment;

import javawebdev.petsns.comment.dto.Comment;
import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.post.Post;
import javawebdev.petsns.post.PostMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentMapper commentMapper;
    private final MemberRepository memberRepository;
    private final PostMapper postMapper;

    @Transactional
    public Comment findById(Integer id) {
        return getCommentOrException(id);
    }

    @Transactional
    public List<Comment> getComments(Integer postId) {
        return commentMapper.findByPostId(postId);
    }

    @Transactional
    public Comment create(Integer postId, Integer memberId, String content) {
        Comment comment = new Comment(postId, memberId, content);
        commentMapper.save(comment);
        commentMapper.plusCount(postId);
        return comment;
    }

    @Transactional
    public void update(Integer commentId, String content) {
        commentMapper.update(commentId, content);
    }

    @Transactional
    public void delete(Integer postId, Integer commentId) {
        Comment comment = getCommentOrException(commentId);

        commentMapper.delete(comment.getId());
        commentMapper.minusCount(postId);
    }

    //  ------------ validation --------------

    // TODO: 유저 확인
    private Member getMemberOrException(Integer memberId) throws Exception {
        return memberRepository.selectById(memberId).orElseThrow(() -> {
            log.info("Member not found. memberId = {}", memberId);
            throw new IllegalArgumentException();
        });
    }

    // TODO: 포스트 확인
    private Post getPostOrException(Integer postId) {
        return postMapper.post(postId);
    }

    // TODO: 댓글 확인
    private Comment getCommentOrException(Integer commentId) {
            return commentMapper.findById(commentId).orElseThrow(() -> {
                log.info("Comment not found. commentId = {}", commentId);
                throw new IllegalArgumentException();
            });
    }

    // TODO: validation
    private void validation(Comment comment, Integer postId, Integer memberId) {

        if (!Objects.equals(comment.getMemberId(), memberId) || !Objects.equals(comment.getPostId(), postId)) {
            log.info("올바르지 않은 접근입니다." +
                            "comment.memberId = {}" +
                            "memberId = {}" +
                            "comment.postId = {}" +
                            "postId = {}",
                    comment.getMemberId(),
                    memberId,
                    comment.getPostId(),
                    postId
            );
            throw new IllegalArgumentException();
        }
    }
}
