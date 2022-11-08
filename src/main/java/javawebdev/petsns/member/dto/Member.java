package javawebdev.petsns.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;


@Getter
@Setter
@ToString
public class Member implements UserDetails {

    private Integer id;
    private String nickname;
    private String password;
    private String introduce;
    private LocalDateTime update_at;
    private String email;

    private String auth;

    private String profileImg;

    private Map<String, Object> attributes;

    public Member() {
    }

    public Member update(String nickname){
        this.nickname = nickname;
        return this;
    }


    @Builder
    public Member(String nickname, String password, String introduce, String email, String auth) {
        this.nickname = nickname;
        this.password = password;
        this.introduce = introduce;
        this.update_at = LocalDateTime.now();
        this.email = email;
        this.auth = auth;
    }

    public Member(Integer id, String nickname, String password, String introduce, LocalDateTime update_at, String email, String auth, String profileImg) {
        this.id = id;
        this.nickname = nickname;
        this.password = password;
        this.introduce = introduce;
        this.update_at = update_at;
        this.email = email;
        this.auth = auth;
        this.profileImg = profileImg;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    @Override
    public String getUsername() {
        return nickname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
