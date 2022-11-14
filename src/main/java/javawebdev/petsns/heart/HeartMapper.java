package javawebdev.petsns.heart;

import javawebdev.petsns.heart.dto.Heart;
import javawebdev.petsns.member.dto.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface HeartMapper {

    List<Heart> findByNickName(String nickName);

    void delete(Heart heart);

    void save(Heart heart);

    List<Heart> findByPostId(Integer postId);

    Optional<Heart> findByNicknameAndPostId(String nickname, Integer postId);

    List<Heart> findAll();

    List<Member> findMembersByPostId(Integer postId);

    List<Heart> isInHeart(Integer postId);
}
