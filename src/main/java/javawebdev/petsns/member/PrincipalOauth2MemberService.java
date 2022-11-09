package javawebdev.petsns.member;

import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.member.dto.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PrincipalOauth2MemberService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {
        OAuth2User oAuth2User = super.loadUser(request);
        System.out.println("oAuth2User = " + oAuth2User);

//        String provider = request.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        System.out.println("providerId = " + providerId);
        String username = providerId;

        String uuid = UUID.randomUUID().toString().substring(0, 6);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = bCryptPasswordEncoder.encode("패스워드" + uuid);

        String email = oAuth2User.getAttribute("email");


        Member member = memberRepository.findMemberByEmail(email);
        if (member == null) {
            member = Member.builder()
                    .nickname(username)
                    .email(email)
                    .password(password)
                    .auth("ROLE_MEMBER")
                    .introduce("Google Login")
                    .build();
            memberService.joinMember(member);
        }
        return new PrincipalDetails(member, oAuth2User.getAttributes());

    }
}