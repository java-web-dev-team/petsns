package javawebdev.petsns.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Setter
@Getter
public class CustomUser extends User  {


    private Member member;

    public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public CustomUser(Member member){
        super(member.getEmail(), member.getPassword(), member.getAuthorities()
                .stream()
                .map(auth -> new SimpleGrantedAuthority(member.getAuth())).collect(Collectors.toList()));
    }
}
