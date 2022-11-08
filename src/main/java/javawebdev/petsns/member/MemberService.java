package javawebdev.petsns.member;

import javawebdev.petsns.member.dto.CustomUser;
import javawebdev.petsns.member.dto.Member;
import org.springframework.web.multipart.MultipartFile;

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

    Member customUserToMember(CustomUser customUser);

}


