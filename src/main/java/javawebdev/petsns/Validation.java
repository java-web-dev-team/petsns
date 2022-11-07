package javawebdev.petsns;

import javawebdev.petsns.block.BlockMapper;
import javawebdev.petsns.block.dto.Block;
import javawebdev.petsns.comment.CommentMapper;
import javawebdev.petsns.comment.dto.Comment;
import javawebdev.petsns.follow.FollowMapper;
import javawebdev.petsns.follow.dto.Follow;
import javawebdev.petsns.heart.HeartMapper;
import javawebdev.petsns.heart.dto.Heart;
import javawebdev.petsns.help.HelpMapper;
import javawebdev.petsns.help.dto.Help;
import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.post.PostMapper;
import javawebdev.petsns.post.dto.Post;
import javawebdev.petsns.report.ReportMapper;
import javawebdev.petsns.report.dto.Report;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
public class Validation {

    private final MemberRepository memberRepository;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final HeartMapper heartMapper;
    private final HelpMapper helpMapper;
    private final ReportMapper reportMapper;
    private final BlockMapper blockMapper;
    private final FollowMapper followMapper;

    //  패스워드 유효성 확인
    public boolean isValidPassword(String password) {
        if (password.contains(" ") || password.equals("")) {
            throw new IllegalArgumentException("Not valid password.");
        } else {
            return true;
        }
    }

    // 유저 확인(id)
    public Member getMemberOrException(Integer memberId) {
        return memberRepository.selectById(memberId).orElseThrow(() -> {
            log.info("Member not found. memberId = {}", memberId);
            throw new IllegalArgumentException("Member not found. memberId = " + memberId);
        });
    }

    // 유저 확인(nickname)
    public Member getMemberOrException(String nickname) {
        return memberRepository.selectMemberByNickname(nickname).orElseThrow(() -> {
            log.info("Member not found. nickname = {}", nickname);
            throw new IllegalArgumentException("Member not found. nickname = " + nickname);
        });
    }

    // 포스트 확인
    public Post getPostOrException(Integer postId) {
        return postMapper.findById(postId).orElseThrow(() -> {
            log.info("Post not found. postId = {}", postId);
            throw new IllegalArgumentException("Post not found. postId = " + postId);
        });
    }

    // 댓글 확인
    public Comment getCommentOrException(Integer commentId) {
        return commentMapper.findById(commentId).orElseThrow(() -> {
            log.info("Comment not found. commentId = {}", commentId);
            throw new IllegalArgumentException("Comment not found. commentId = " + commentId);
        });
    }

    // 문의 확인
    public Report getReportOrException(Integer reportId) {
        return reportMapper.findById(reportId).orElseThrow(() -> {
            log.info("Report not found. reportId = {}", reportId);
            throw new IllegalArgumentException("Report not found. reportId = " + reportId);
        });
    }

    // 이미 좋아요했는지 확인
    public void heartOrException(String nickname, Integer postId) {
        if (!heartMapper.findByNicknameAndPostId(nickname, postId).equals(Optional.empty())) {
            throw new IllegalArgumentException("Already hearted. nickname = " + nickname + " postId = " + postId);
        }
    }

    // 문의 확인
    public Help getHelpOrException(Integer helpId) {
        return helpMapper.findById(helpId).orElseThrow(() -> {
            log.info("Help not found. helpId = {}", helpId);
            throw new IllegalArgumentException("sample Exception message");
        });
    }

    // 이미 차단 했는지 확인
    public boolean isNotExistentBlock(Block block) {
        if (blockMapper.findByBlock(block).equals(Optional.empty())) {
            return true;
        } else {
            log.info("Already blocked.");
            throw new IllegalArgumentException("sample Exception message");
        }
    }

    // 차단 확인
    public Block getBlockOrException(Block block) {
        return blockMapper.findByBlock(block).orElseThrow(() -> {
            log.info("Block not found. " +
                    "blocker = {}" +
                    "blocked = {}", block.getBlocker(), block.getBlocked());
            throw new IllegalArgumentException("sample Exception message");
        });
    }

    // 팔로우 확인
    public Follow getFollowOrException(Follow follow) {
        return followMapper.findByFollowingAndFollower(follow.getFollowing(), follow.getFollower()).orElseThrow(() -> {
            log.info("Follow not found. " +
                    "following = {}" +
                    "follower = {}", follow.getFollowing(), follow.getFollower());
            throw new IllegalArgumentException("sample Exception message");
        });
    }

    // 하트 확인
    public Heart getHeartOrException(String nickname, Integer postId) {
        return heartMapper.findByNicknameAndPostId(nickname, postId).orElseThrow(() -> {
            log.info("Heart not Found. nickname = {}, postId = {}", nickname, postId);
            throw new IllegalArgumentException("Heart not Found. nickname = " + nickname + ", postId = " + postId);
        });
    }

    // 이미 팔로우 했는지 확인
    public boolean isNotExistentFollow(Follow follow) {
        return followMapper.findByFollowingAndFollower(follow.getFollowing(), follow.getFollower()).equals(Optional.empty());
    }

    // admin 확인
    public Member getAdminOrException(Integer memberId) {
        Member member = getMemberOrException(memberId);
        if (Objects.equals(member.getAuth(), "ROLE_ADMIN")) {
            return member;
        } else {
            log.info("Member is not admin. member.auth = {}", member.getAuth());
            throw new IllegalArgumentException("sample Exception message");
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
            throw new IllegalArgumentException("sample Exception message");
        }
    }

    public Post isValidAccess(Post post, Member member) {
        if (Objects.equals(member.getNickname(), post.getNickname())) {
            return post;
        } else {
            log.info("Not valid access. post.memberNickname = {}, current nickname = {}", post.getNickname(), member.getNickname());
            throw new IllegalArgumentException("sample Exception message");
        }
    }

    public Help isValidAccess(Help help, Member member) {
        if (Objects.equals(member.getId(), help.getMemberId())) {
            return help;
        } else {
            log.info("Not valid access. help.memberId = {}, current memberId = {}", help.getMemberId(), member.getId());
            throw new IllegalArgumentException("sample Exception message");
        }
    }

    public Report isValidAccess(Report report, Member member) {
        if (Objects.equals(member.getNickname(), report.getReporter())) {
            return report;
        } else {
            log.info("Not valid access. report.reporter = {}, current member = {}", report.getReporter(), member.getNickname());
            throw new IllegalArgumentException("sample Exception message");
        }
    }

    public Heart isValidAccess(Heart heart, Member member, Post post) {
        if (Objects.equals(member.getNickname(), heart.getNickName()) && Objects.equals(heart.getPostId(), post.getId())) {
            return heart;
        } else {
            log.info("Not valid access. heart.nickname = {}, current member = {}", heart.getNickName(), member.getNickname());
            throw new IllegalArgumentException(
                    "Not valid access. heart.nickname = " +
                    heart.getNickName() +
                    ", current member = " +
                    member.getNickname() +
                    "heart.postId = " +
                    post.getId());
        }
    }

    /**
     * 이메일 인지 검증.
     */
    public boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()) {
            err = true;
        }
        return err;
    }
}
