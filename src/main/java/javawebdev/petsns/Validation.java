package javawebdev.petsns;

import javawebdev.petsns.comment.CommentMapper;
import javawebdev.petsns.comment.dto.Comment;
import javawebdev.petsns.heart.HeartMapper;
import javawebdev.petsns.help.HelpMapper;
import javawebdev.petsns.help.dto.Help;
import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.post.PostMapper;
import javawebdev.petsns.post.dto.Post;
import javawebdev.petsns.report.dto.Report;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class Validation {

    private final MemberRepository memberRepository;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final HeartMapper heartMapper;
    private final HelpMapper helpMapper;

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

    // 좋아요 확인
    public boolean isNotExistentHeart(String nickname, Integer postId) {
        return heartMapper.findByNicknameAndPostId(nickname, postId).equals(Optional.empty());
    }

    // 문의 확인
    public Help getHelpOrException(Integer helpId) {
        return helpMapper.findById(helpId).orElseThrow(() -> {
            log.info("Help not found. helpId = {}", helpId);
            throw new IllegalArgumentException();
        });
    }

    // admin 확인
    public Member getAdminMemberOrException(Integer memberId) throws Exception {
        Member member = getMemberOrException(memberId);
        if (Objects.equals(member.getAuth(), "ADMIN")) {
            return member;
        } else {
            log.info("Member is not admin. member.auth = {}", member.getAuth());
            throw new IllegalArgumentException();
        }
    }

    // 유효한 접근 확인
    public boolean isValidAccess(Comment comment, Member member, Post post) {
        if (Objects.equals(comment.getPostId(), post.getId()) && Objects.equals(comment.getMemberId(), member.getId())) {
            return true;
        } else {
            log.info("Not valid access. " +
                            "comment.memberId = {}" +
                            "current memberId = {}" +
                            "comment.postId = {}" +
                            "current postId = {}",
                    comment.getMemberId(), member.getId(), comment.getPostId(), post.getId());
            throw new IllegalArgumentException();
        }
    }

    public boolean isValidAccess(Post post, Member member) {
        if (Objects.equals(member.getNickname(), post.getNickname())) {
            return true;
        } else {
            log.info("Not valid access. post.memberNickname = {}, current nickname = {}", post.getNickname(), member.getNickname());
            throw new IllegalArgumentException();
        }
    }

//    public boolean isValidAccess(Help help, Member member) {
//        if (Objects.equals(member.getId(), help.getMemberId())) {
//            return true;
//        } else {
//            log.info("Not valid access. help.memberId = {}, current memberId = {}", help.getMemberId(), member.getId());
//            throw new IllegalArgumentException();
//        }
//    }

    public boolean isValidAccess(Report report, Member member) {
        if (Objects.equals(member.getNickname(), report.getReporter())) {
            return true;
        } else {
            log.info("Not valid access. report.reporter = {}, current member = {}", report.getReporter(), member.getNickname());
            throw new IllegalArgumentException();
        }
    }

}
