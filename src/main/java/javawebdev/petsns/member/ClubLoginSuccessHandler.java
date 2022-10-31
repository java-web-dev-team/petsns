package javawebdev.petsns.member;

import javawebdev.petsns.member.dto.PrincipalDetails;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class ClubLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("-------------------------------");
        log.info("onAuthenticationSuccess");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        boolean nicknameCheck = principalDetails.getUsername().length() == 21;

        if(nicknameCheck){
            redirectStrategy.sendRedirect(request, response, "/member/modify");
        }
    }
}
