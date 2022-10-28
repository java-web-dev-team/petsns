package javawebdev.petsns.member;

import javawebdev.petsns.member.dto.Member;

public interface MemberService {

    void joinMember(Member member);

    Member updateMember(Member member);

    Member findByNickname(String nickname);

    void deleteMember(String nickname);
}
