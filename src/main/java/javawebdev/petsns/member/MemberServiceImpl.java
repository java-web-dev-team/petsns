package javawebdev.petsns.member;

import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService, UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public void joinMember(Member member) throws Exception {
        if(memberRepository.findMemberByNickname(member.getNickname()) != null){
            throw new RuntimeException("이미 존재하는 유저입니다. ");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.insert(member);
    }

    @Override
    public void updateMember(Member member) throws Exception {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            memberRepository.update(member);
    }

    @Override
    public Member findByNickname(String nickname) throws Exception {
        if (memberRepository.selectMember(nickname) == null) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }
        return memberRepository.selectMember(nickname);
    }

    @Override
    public void deleteMember(String nickname) throws Exception {
        memberRepository.delete(nickname);
    }

    @Override
    public Member loadUserByUsername(String nickname){
        try {
            return memberRepository.findMemberByNickname(nickname);
        } catch (Exception e) {
            e.printStackTrace();
            throw new UsernameNotFoundException(nickname);
        }
    }


}
