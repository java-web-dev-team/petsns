//package javawebdev.petsns.follow;
//
//import javawebdev.petsns.Validation;
//import javawebdev.petsns.follow.dto.Follow;
//import javawebdev.petsns.member.dto.Member;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@Service
//@Transactional
//public class FollowService {
//
//    private final FollowMapper followMapper;
//
//    private final Validation validation;
//
//    public void click (String following, String follower){
//        Member followerMember = validation.getMemberOrException(follower);
//        Member followingMember = validation.getMemberOrException(following);
//        Follow follow = new Follow(followingMember.getNickname(), followerMember.getNickname());
//        if (validation.isNotExistentFollow(follow)) {
//            followMapper.save(follow);
//        } else {
//            followMapper.delete(follow);
//        }
//
//    }
//
//    public List<Follow> findByFollowing(String following) {
//
//        return followMapper.findByFollowing(following);
//    }
//
//    public Follow save(String following, String follower) throws Exception {
//        Member followingMember = validation.getMemberOrException(following);
//        Member followerMember = validation.getMemberOrException(follower);
//        Follow follow = new Follow(followingMember.getNickname(), followerMember.getNickname());
//        followMapper.save(follow);
//        return follow;
//    }
//
//    public void delete(String following, String follower) throws Exception {
//        Member followingMember = validation.getMemberOrException(following);
//        Member followerMember = validation.getMemberOrException(follower);
//        if (validation.isNotExistentFollow(follow)) {
//            throw new IllegalArgumentException();
//        }
//    }
//
//
////    public void delete(Integer memberId, Integer followingId) throws Exception {
////        Member member = validation.getMemberOrException(memberId);
////        Member following = validation.getMemberOrException(followingId);
////        if (validation.is)
////    }
//}
