package javawebdev.petsns;

import javawebdev.petsns.comment.CommentMapper;
import javawebdev.petsns.comment.dto.Comment;
import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.post.PostMapper;
import javawebdev.petsns.post.dto.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class Validation {

    private final MemberRepository memberRepository;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;

    // 유저 확인
    public Member getMemberOrException(Integer memberId) throws Exception {
        return memberRepository.selectById(memberId).orElseThrow(() -> {
            log.info("Member not found. memberId = {}", memberId);
            throw new IllegalArgumentException();
        });
    }

    // 포스트 확인
    public Post getPostOrException(Integer postId) {
        return postMapper.findById(postId).orElseThrow(() -> {
            log.info("Post not found. postId = {}", postId);
            throw new IllegalArgumentException();
        });
    }

    // 댓글 확인
    public Comment getCommentOrException(Integer commentId) {
        return commentMapper.findById(commentId).orElseThrow(() -> {
            log.info("Comment not found. commentId = {}", commentId);
            throw new IllegalArgumentException();
        });
    }

    // TODO: validation
    public void validation(Comment comment, Integer postId, Integer memberId) {

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