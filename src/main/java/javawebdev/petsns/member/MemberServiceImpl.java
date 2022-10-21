package javawebdev.petsns.member;

import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public void joinMember(Member member) throws Exception {
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
        if (memberRepository.selectMember(nickname) != null) {
            return memberRepository.selectMember(nickname);
        }
        return null;
    }

    @Override
    public void deleteMember(String nickname) throws Exception {
        memberRepository.delete(nickname);
    }

}
