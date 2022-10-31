package javawebdev.petsns.member;

import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService, UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public void joinMember(Member member){
        try {
            if (memberRepository.findMemberByNickname(member.getNickname()) != null) {
                throw new RuntimeException("이미 존재하는 유저입니다. ");
            }
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            memberRepository.insertMember(member);
        } catch(Exception e){
            log.error("insertMember - error : ", e);
        }
    }

    @Override
    public Member updateMember(Member member){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            try {
                memberRepository.updateMember(member);
            } catch(Exception e){
                log.error("updateMember - error : ", e);
            }
        return member;
    }

    @Override
    public Member findByNickname(String nickname) {
        try {
            if (memberRepository.selectMember(nickname) == null) {
                throw new UsernameNotFoundException("UsernameNotFoundException");
            }

            return memberRepository.selectMember(nickname);
        } catch(Exception e){
            log.error("selectMember - error : ", e);
        }
        return null;
    }

    @Override
    public void deleteMember(String nickname){
        try {
            memberRepository.deleteMember(nickname);
        } catch(Exception e){
            log.error("deleteMember - error ", e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String nickname){
        log.info("loadUserByName: " + nickname);
        Member member = memberRepository.selectMemberByNickname(nickname).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다"));
        return new User(member.getNickname(), member.getPassword(), Arrays.asList(new SimpleGrantedAuthority(member.getAuth())));
    }


}
