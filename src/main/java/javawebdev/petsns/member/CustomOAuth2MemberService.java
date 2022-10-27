package javawebdev.petsns.member;

import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.member.dto.OAuthAttributes;
import javawebdev.petsns.member.dto.SessionMember;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final HttpSession session;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        try {
            OAuth2UserService delegate = new DefaultOAuth2UserService();
            OAuth2User oAuth2User = delegate.loadUser(userRequest);

            /**
             * registrationId
             * 현재 로그인 진행 중인 서비스 구분하는 코드
             * 이후에 여러가지 추가할 때 네이버인지 구글인지 구분
             */
            String registrationId = userRequest.getClientRegistration().getRegistrationId();

            /**
             * userNameAttributeName
             * OAuth2 로그인 진행 시 키가 되는 필드값 (=Primary Key)
             * 구글 기본 코드: sub, 네이버 카카오 등은 기본 지원 ㅌ
             * 이후 네이버, 구글 로그인 동시 지원시 사용
             */
            String userNameAttributeName =
                    userRequest
                            .getClientRegistration()
                            .getProviderDetails()
                            .getUserInfoEndpoint()
                            .getUserNameAttributeName();

            OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
            Member member = saveOrUpdate(attributes);


            /**
             * SessionMember
             * 세션에 사용자 정보를 저장하기 위한 dto 클래스
             * (Member 클래스를 사용x, 새로 만듬)
             */
            session.setAttribute("Member", new SessionMember(member));

            return new DefaultOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority(
                            member.getAuth())
                    ), attributes.getAttributes(), attributes.getNameAttributeKey()
            );

        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

        private Member saveOrUpdate(OAuthAttributes oAuthAttributes) throws Exception {
        Member member = memberRepository.findMemberByNickname(oAuthAttributes.getNickname());
            if(member != null){
                member.update(oAuthAttributes.getNickname());
                return memberService.updateMember(member);
            } else{
                member = oAuthAttributes.toEntity();
                memberService.joinMember(member);
            }

            return null;
        }
    }

