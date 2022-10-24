package javawebdev.petsns;

import javawebdev.petsns.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        {
            http.authorizeRequests()
                .antMatchers("/register", "/login", "/signUp").permitAll()
                .antMatchers("/*").hasRole("MEMBER")
                .antMatchers("/admin/*").hasRole("ADMIN")
                .anyRequest().authenticated();       // 나머지 모든 요청은 권한종류 상관없이 권한이 있어야 접근가능

            http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .usernameParameter("nickname");

            http.logout()
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true);
        }
    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(memberService)
//                .passwordEncoder(new BCryptPasswordEncoder());
//    }

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
