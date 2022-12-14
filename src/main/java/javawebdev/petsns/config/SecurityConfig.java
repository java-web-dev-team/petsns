package javawebdev.petsns.config;

import javawebdev.petsns.member.handler.ClubLoginSuccessHandler;
import javawebdev.petsns.member.PrincipalOauth2MemberService;
import javawebdev.petsns.member.handler.customFailureHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalOauth2MemberService principalOauth2MemberService;

    @Override
    public void configure(WebSecurity web) {
        {
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
            web.ignoring().antMatchers("/favicon.ico", "/resources/**", "/error");
        }
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        {
            http.authorizeRequests()
                    .antMatchers("/api/**","/register", "/login-form", "/signUp", "/img/**", "/login", "/emailCheck", "/idCheck", "/memberImg/**", "/memberImg/upload", "/memberImg/img/**", "/member/profile/modify/**", "/email/Certification/**").permitAll()
                    .anyRequest()
                    .authenticated();       // ????????? ?????? ????????? ???????????? ???????????? ????????? ????????? ????????????

            http.oauth2Login()
                    .loginPage("/login-form")
                    .defaultSuccessUrl("/posts")
                    .userInfoEndpoint()
                    .userService(principalOauth2MemberService)
                    .and()
                    .successHandler(successHandler());

            http.formLogin()
                    .loginPage("/login-form")
                    .defaultSuccessUrl("/posts")
                    .loginProcessingUrl("/login")
                    .usernameParameter("nickname")
                    .passwordParameter("password")
                    .failureHandler(customFailureHandler())
                    .permitAll();


            http.logout()
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("http://localhost:8080/logout").permitAll()
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .clearAuthentication(true);

        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ClubLoginSuccessHandler successHandler(){
        return new ClubLoginSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler customFailureHandler(){
        return new customFailureHandler();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

}
