package javawebdev.petsns.post;

import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.post.dto.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {

    //  validation 용
    Optional<Post> findById(Integer id);

    //  저장
    void saveAndSetId(Post post);
    void update(Post post);
    List<Post> findAll();
    void delete(Post post);
    List<Post> findAllOfFollowingMember(Member member);

    List<Post> findByNickname(String nickname);

    void updateNickname(String changeNickname, String changedNickname);
}
