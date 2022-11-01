package javawebdev.petsns.member.handler;

import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.member.dto.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MemberRepository memberRepository;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        log.info("-------------------------------");
        log.info("onAuthenticationSuccess");

        PrincipalDetails member = (PrincipalDetails) authentication.getPrincipal();

        boolean nicknameCheck = member.getName().length() == 21;

        Member m = memberRepository.selectMember(member.getName());
        String id = String.valueOf(m.getId());

        if(nicknameCheck){
            redirectStrategy.sendRedirect(request, response, "/member/"+ id);
        } else{

            redirectStrategy.sendRedirect(request, response, "/member/profile/"+ id);
        }
    }
}
