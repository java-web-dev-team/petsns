package javawebdev.petsns.member;


import javawebdev.petsns.member.dto.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberRepository {

    Member selectMember(String nickname) throws Exception;

    Member findMemberByNickname(String nickname) throws Exception;

    Member findMemberByEmail(String email) throws Exception;

    int insertMember(Member member) throws Exception;

    int updateMember(Member member) throws Exception;

    int deleteMember(String nickname) throws Exception;

    int count() throws Exception;

    void deleteAll() throws Exception;

    Optional<Member> selectById(Integer id) throws Exception;

    Optional<Member> selectMemberByNickname(String nickname) throws Exception;

    List<Member> findAll();

}
