package javawebdev.petsns.member.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String nickname;
    private String email;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String nickname, String email) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.nickname = nickname;
        this.email = email;
    }

    /**
     * of()
     * OAuth2User 에서 반환하는 사용자 정보는 Map 이기 때문에 값 하나하나 반환
     */
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes){
        return
        OAuthAttributes.builder()
                .nickname((String) attributes.get("nickname"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    /**
     * toEntity()
     * Member 엔티티 생성
     * OAuthAttributes 에서 엔티티 생성 시점 = 처음 가입 시
     * OAuthAttributes 클래스 생성이 끝났으면 같은 패키지에 SessionMember 클래스 생성
     */

    public Member toEntity(){
        return Member.builder()
                .nickname(nickname)
                .email(email)
                .auth("ROLE_MEMBER")
                .build();
    }
}
