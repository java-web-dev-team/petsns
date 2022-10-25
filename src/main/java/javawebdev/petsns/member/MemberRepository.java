package javawebdev.petsns.member;


import javawebdev.petsns.member.dto.Member;

import java.util.Optional;

public interface MemberRepository {

    Member selectMember(String nickname) throws Exception;

    Member findMemberByNickname(String nickname) throws Exception;

    Member findMemberByEmail(String email) throws Exception;

    int insert(Member member) throws Exception;

    int update(Member member) throws Exception;

    int delete(String nickname) throws Exception;

    int count() throws Exception;

    void deleteAll() throws Exception;

    Optional<Member> selectById(Integer id) throws Exception;

    Optional<Member> selectMemberByNickname(String nickname) throws Exception;

}
