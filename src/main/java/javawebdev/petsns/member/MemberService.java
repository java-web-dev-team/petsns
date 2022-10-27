package javawebdev.petsns.member;

import javawebdev.petsns.member.dto.Member;

public interface MemberService {

    void joinMember(Member member) throws Exception;

    Member updateMember(Member member) throws Exception;

    Member findByNickname(String nickname) throws Exception;

    void deleteMember(String nickname) throws Exception;
}
