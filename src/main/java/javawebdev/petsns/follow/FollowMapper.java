package javawebdev.petsns.follow;

import javawebdev.petsns.follow.dto.Follow;
import javawebdev.petsns.member.dto.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FollowMapper {

    List<Member> findByFollowing(Member member);

    void save(Follow follow);

    void delete(Follow follow);

    List<Follow> findByFollowing(String following);

    List<Follow> findByFollower(String follower);

    Optional<Follow> findByFollowingAndFollower(String following, String follower);

    List<Follow> findAll();


}
