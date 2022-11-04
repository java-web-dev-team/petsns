package javawebdev.petsns.member;

import javawebdev.petsns.member.dto.Member;

public interface MemberService {

    void joinMember(Member member);

    Member updateMember(Member member);

    Member findByNickname(String nickname);

    Member findById(Integer id);

    void deleteMember(String nickname);

    int emailCheck(String email);

    int idCheck(String nickname);

    boolean isValidNickname(String nickname);

    boolean isValidEmail(String email);

    boolean isValidPwd(String password);

    boolean expression(String email);

    void updateMember(String password, Integer id);
}


