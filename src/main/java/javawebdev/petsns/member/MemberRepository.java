package javawebdev.petsns.member;


import javawebdev.petsns.member.dto.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberRepository {

    Member selectMember(String nickname);

    Member findMemberByNickname(String nickname);

    Member findMemberByEmail(String email);

    int insertMember(Member member);

    int updateMember(Member member);

    int deleteMember(String nickname);

    int count();

    void deleteAll();

    Optional<Member> selectById(Integer id);

    Member selectByIdNotOptional(Integer id);

    Optional<Member> selectMemberByNickname(String nickname);

    List<Member> findAll();

    int emailCheck(String email);
    int idCheck(String nickname);

}
