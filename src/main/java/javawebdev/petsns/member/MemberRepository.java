package javawebdev.petsns.member;


import javawebdev.petsns.member.dto.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mapper
public interface MemberRepository {

    Member selectMember(String nickname);

    Member findMemberByNickname(String nickname);

    Member findMemberByEmail(String email);

    int insertMember(Member member);

    int updateMember(Member member);

    int deleteMember(String email);

    int count();

    void deleteAll();

    Optional<Member> selectById(Integer id);

    Member selectByIdNotOptional(Integer id);

    Optional<Member> selectMemberByNickname(String nickname);

    List<Member> findAll();

    List<Member> findLikeMember(String nickname);

    int emailCheck(String email);
    int idCheck(String nickname);

    void updateProfileImg(@Param("profileImg") String profileImg, @Param("email") String email);

    void updatePwd(String password, String email);

}
