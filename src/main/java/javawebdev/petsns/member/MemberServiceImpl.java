package javawebdev.petsns.member;

import javawebdev.petsns.Validation;
import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService, UserDetailsService {

    private final MemberRepository memberRepository;
    private final Validation validation;

    @Override
    public void joinMember(Member member){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setPassword(passwordEncoder.encode(member.getPassword()) );
        memberRepository.insertMember(member);
    }

    @Override
    public Member updateMember(Member member){
        memberRepository.updateMember(member);
        return member;
    }

    public void updateMember(String password, Integer id){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String changedPwd = passwordEncoder.encode(password);
        memberRepository.updatePwd(changedPwd, id);
    }

    @Override
    public Member findByNickname(String nickname) {
        return memberRepository.selectMember(nickname);
    }

    @Override
    public Member findById(Integer id) {
        return memberRepository.selectByIdNotOptional(id);
    }

    @Override
    public void deleteMember(String nickname){
        validation.getMemberOrException(nickname);
        memberRepository.deleteMember(nickname);
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
    public UserDetails loadUserByUsername(String nickname){
        log.info("loadUserByName: " + nickname);
        Member member = memberRepository.selectMember(nickname);
        return new User(member.getNickname(), member.getPassword(), Arrays.asList(new SimpleGrantedAuthority(member.getAuth())));
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


}


