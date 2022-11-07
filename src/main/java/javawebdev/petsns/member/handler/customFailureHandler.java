package javawebdev.petsns.member.handler;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class customFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMsg;
        if (exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException){
            errorMsg="아이디 또는 비밀번호가 맞지 않습니다.";
        }else if (exception instanceof UsernameNotFoundException){
            errorMsg="존재하지 않는 아이디 입니다.";
        } else{
            errorMsg = "알 수 없는 이유로 로그인이 안되고 있습니다. 다시 시도해주세요.";
        }

        errorMsg = URLEncoder.encode(errorMsg, "UTF-8");
        setDefaultFailureUrl("/login-form?error&exception=" + errorMsg);
        super.onAuthenticationFailure(request, response, exception);
    }
}
