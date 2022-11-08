package javawebdev.petsns.member;

import javawebdev.petsns.Validation;
import javawebdev.petsns.member.dto.CustomUser;
import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService, UserDetailsService {

    private final MemberRepository memberRepository;
    private final Validation validation;

    private final JavaMailSender javaMailSender;
    @Override
    public void joinMember(Member member){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.insertMember(member);
    }

    @Override
    public Member updateMember(Member member){
        memberRepository.updateMember(member);
        return member;
    }

    @Override
    public void updateMember(String password, String email){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String changedPwd = passwordEncoder.encode(password);
        memberRepository.updatePwd(changedPwd, email);
    }

    @Override
    public void updateProfileImg(String profileImg, String email) {
        memberRepository.updateProfileImg(profileImg, email);
    }

    @Override
    public Member findByNickname(String nickname) {
        return memberRepository.selectMember(nickname);
    }

    @Override
    public Member findByNicknameValid(String nickname){
        return validation.getMemberOrException(nickname);
    }

    @Override
    public Member findByNickIdValid(Integer id){
        return validation.getMemberOrException(id);
    }

    @Override
    public Member findByEmail(String email) {
        return memberRepository.findMemberByEmail(email);
    }

    @Override
    public Member customUserToMember(CustomUser customUser) {
        return findByEmail(customUser.getUsername());
    }

    @Override
    public boolean isMyProfile(String myNickname, String nickname) {
        return Objects.equals(myNickname, nickname);
    }

    @Override
    public Member findById(Integer id) {
        return memberRepository.selectByIdNotOptional(id);
    }

    @Override
    public void deleteMember(String email){
        memberRepository.deleteMember(email);
    }

    @Override
    public int emailCheck(String email) {
        return memberRepository.emailCheck(email);
    }

    @Override
    public int idCheck(String nickname) {
        return memberRepository.idCheck(nickname);
    }

    @Override
    public UserDetails loadUserByUsername(String name){
        log.info("loadUserByName: " + name);
        Member member;
        if(name.contains("@")) {
            member = memberRepository.findMemberByEmail(name);
        } else {
            member = memberRepository.findMemberByNickname(name);
        }

        return member == null ? null : new CustomUser(member);
    }


    @Override
    public boolean isValidNickname(String nickname){
        if(memberRepository.findMemberByNickname(nickname) == null){
            return true;
        }
        return false;
    }

    @Override
    public boolean isValidEmail(String email){
        if(memberRepository.findMemberByEmail(email) == null){
            return true;
        }
        return false;
    }

    @Override
    public boolean expression(String email){
        if(validation.isValidEmail(email)){
            return true;
        }
        return false;
    }

    @Override
    public boolean isValidPwd(String password){
        return validation.isValidPassword(password);
    }

    /**
     * 메일 발송
     */
    @Override
    public StringBuffer mailSend(String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        StringBuffer random = randomString();

        simpleMailMessage.setTo(email);         // 이메일 보낼 대상
        simpleMailMessage.setSubject("인증번호 제목");
        simpleMailMessage.setText(String.valueOf(random));

        javaMailSender.send(simpleMailMessage);
        return random;
    }

    /** 난수 생성 */
    public static StringBuffer randomString(){

        Random rnd = new Random();
        StringBuffer buffer = new StringBuffer();

        for(int i=0; i<7; i++){
            if(rnd.nextBoolean()){                  // rnd.nextBoolean() 는 랜덤으로 true, false 를 리턴
                buffer.append((char)((Math.random() * 26) + 65));     // 랜덤한 대문자를 추가
            } else{
                buffer.append(rnd.nextInt(10));               // 랜덤한 숫자를 추가
            }
        }

        return buffer;
    }

}


