package javawebdev.petsns;

import javawebdev.petsns.member.CustomOAuth2MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2MemberService customOAuth2MemberService;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        {
            http.authorizeRequests()
                .antMatchers("/register", "/login", "/signUp").permitAll()
                .antMatchers("/feeds/").hasAnyRole("MEMBER","ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();       // 나머지 모든 요청은 권한종류 상관없이 권한이 있어야 접근가능

            http.oauth2Login()
                    .loginPage("/login")
                    .defaultSuccessUrl("/member")
                    .failureUrl("/login")
                    .userInfoEndpoint()
                    .userService(customOAuth2MemberService);

            http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/feed")
                .usernameParameter("nickname");

            http.logout()
                    .logoutSuccessUrl("/login")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true);

        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        {
            web.ignoring().antMatchers("/css/**", "/script/**", "/img/**", "fonts/**", "lib/**");
        }
    }

}
