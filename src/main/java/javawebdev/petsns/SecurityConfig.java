package javawebdev.petsns;

import javawebdev.petsns.member.PrincipalOauth2MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalOauth2MemberService principalOauth2MemberService;

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
//                    .defaultSuccessUrl("/oauth/loginInfo")
//                    .failureUrl("/login")
                    .userInfoEndpoint()
                    .userService(principalOauth2MemberService);

            http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/feed")
                .usernameParameter("nickname");

            http.logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
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
