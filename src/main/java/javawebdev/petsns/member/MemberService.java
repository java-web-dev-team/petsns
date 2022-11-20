package javawebdev.petsns.member;

import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.member.dto.PrincipalDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Set;

public interface MemberService {

    void joinMember(Member member);

    Member updateMember(Member member);

    Member findByNickname(String nickname);

    boolean isMyProfile(String myNickname, String nickname);

    Member findById(Integer id);

    void deleteMember(String email);

    int emailCheck(String email);

    int idCheck(String nickname);

    boolean isValidNickname(String nickname);

    boolean isValidEmail(String email);

    boolean isValidPwd(String password);

    boolean expression(String email);

    void updateMember(String password, String email);

    void updateProfileImg(String profileImg, String email);

    StringBuffer mailSend(String email);

    Member findByNicknameValid(String nickname);

    Member findByNickIdValid(Integer id);

    Member findByEmail(String email);

    Member customUserToMember(PrincipalDetails customUser);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    List<Member> searchMember(String nickname);

}


