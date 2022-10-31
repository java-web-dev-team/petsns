package javawebdev.petsns.follow;

import javawebdev.petsns.follow.dto.Follow;

import java.util.List;
import java.util.Optional;

public interface FollowMapper {

    void save(Follow follow);

    void delete(Follow follow);

    List<Follow> findByFollowing(String following);

    List<Follow> findByFollower(String follower);

    Optional<Follow> findByFollowingAndFollower(String following, String follower);

    List<Follow> findAll();


}
