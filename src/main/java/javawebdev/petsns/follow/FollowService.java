package javawebdev.petsns.follow;

import javawebdev.petsns.Validation;
import javawebdev.petsns.follow.dto.Follow;
import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class FollowService {

    private final FollowMapper followMapper;

    private final MemberRepository memberRepository;

    private final Validation validation;
    private Follow follow;

    public void click (String following, String follower){
        Member followerMember = validation.getMemberOrException(follower);
        Member followingMember = validation.getMemberOrException(following);
        Follow follow = new Follow(followingMember.getNickname(), followerMember.getNickname());
        if (validation.isNotExistentFollow(follow)) {
            followMapper.save(follow);
        } else {
            followMapper.delete(follow);
        }

    }

    public List<Member> findByFollowing(String following) {
        List<Follow> follows = followMapper.findByFollowing(following);
        return followsToFollowings(follows);
    }

    public List<Member> findByFollower(String follower) {
        List<Follow> follows = followMapper.findByFollower(follower);
        return followsToFollower(follows);
    }

    public Follow save(String following, String follower) throws Exception {
        Member followingMember = validation.getMemberOrException(following);
        Member followerMember = validation.getMemberOrException(follower);
        Follow follow = new Follow(followingMember.getNickname(), followerMember.getNickname());
        followMapper.save(follow);
        return follow;
    }

    public void delete(String following, String follower) throws Exception {
        Member followingMember = validation.getMemberOrException(following);
        Member followerMember = validation.getMemberOrException(follower);
        if (validation.isNotExistentFollow(follow)) {
            throw new IllegalArgumentException();
        } else {
            delete(following, follower);
        }
    }

    private List<Member> followsToFollowings(List<Follow> follows) {
        List<Member> members = new ArrayList<>();
        for(Follow follow : follows) {
            members.add(validation.getMemberOrException(follow.getFollowing()));
        }
        return members;
    }
    private List<Member> followsToFollower(List<Follow> follows) {
        List<Member> members = new ArrayList<>();
        for(Follow follow : follows) {
            members.add(validation.getMemberOrException(follow.getFollower()));
        }
        return members;
    }

}
