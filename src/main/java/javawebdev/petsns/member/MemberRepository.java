package javawebdev.petsns.member;


import javawebdev.petsns.member.dto.Member;

public interface MemberRepository {

    Member selectMember(String nickname) throws Exception;

    String selectMemberNickname(String nickname) throws Exception;

    int insert(Member member) throws Exception;

    int update(Member member) throws Exception;

    int delete(String nickname) throws Exception;

    int count() throws Exception;

    void deleteAll() throws Exception;

}
