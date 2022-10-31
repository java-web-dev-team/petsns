package javawebdev.petsns.follow;

import javawebdev.petsns.follow.dto.Follow;

import java.util.List;
import java.util.Optional;

public interface FollowMapper {

    void save(Follow follow);

    void delete(Follow follow);

    List<Follow> findByFollowingId(Integer followingId);

    List<Follow> findByFollowerId(Integer followerId);

    Optional<Follow> findByFollowingIdAndFollowerId(Integer followingId, Integer followerId);

    List<Follow> findAll();


}
