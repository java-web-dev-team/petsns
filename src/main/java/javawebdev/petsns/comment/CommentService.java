package javawebdev.petsns.comment;

import javawebdev.petsns.Validation;
import javawebdev.petsns.comment.dto.Comment;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.post.dto.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
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

        if (
                Objects.equals(member.getId(), comment.getMemberId())
                && Objects.equals(post.getId(), comment.getPostId())
        ) {
            comment.setContent(updatedContent);
            commentMapper.update(comment);
        } else {
            log.info("Unauthorized. " +
                    "comment.memberId = {}" +
                    "current memberId = {}" +
                    "comment.postId = {}" +
                    "current postId = {}",
                    comment.getMemberId(), memberId, comment.getPostId(), postId);
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public void delete(Integer memberId, Integer postId, Integer commentId) throws Exception {
        Member member = validation.getMemberOrException(memberId);
        Comment comment = validation.getCommentOrException(commentId);

        if (Objects.equals(member.getId(), comment.getMemberId())) {
            commentMapper.delete(comment.getId());
            commentMapper.minusCount(postId);
        } else {
            log.info("Member is Unauthorized. memberId = {}", memberId);
            throw new IllegalArgumentException();
        }

    }

    //  ------------ validation --------------
//
//    // 유저 확인
//    private Member getMemberOrException(Integer memberId) throws Exception {
//        return memberRepository.selectById(memberId).orElseThrow(() -> {
//            log.info("Member not found. memberId = {}", memberId);
//            throw new IllegalArgumentException();
//        });
//    }
//
//    // 포스트 확인
//    private Post getPostOrException(Integer postId) {
//        return postMapper.findById(postId).orElseThrow(() -> {
//            log.info("Post not found. postId = {}", postId);
//            throw new IllegalArgumentException();
//        });
//    }
//
//    // 댓글 확인
//    private Comment getCommentOrException(Integer commentId) {
//            return commentMapper.findById(commentId).orElseThrow(() -> {
//                log.info("Comment not found. commentId = {}", commentId);
//                throw new IllegalArgumentException();
//            });
//    }
//
//    // TODO: validation
//    private void validation(Comment comment, Integer postId, Integer memberId) {
//
//        if (!Objects.equals(comment.getMemberId(), memberId) || !Objects.equals(comment.getPostId(), postId)) {
//            log.info("올바르지 않은 접근입니다." +
//                            "comment.memberId = {}" +
//                            "memberId = {}" +
//                            "comment.postId = {}" +
//                            "postId = {}",
//                    comment.getMemberId(),
//                    memberId,
//                    comment.getPostId(),
//                    postId
//            );
//            throw new IllegalArgumentException();
//        }
//    }
}
