package javawebdev.petsns.member;

import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member")
    public String main(@AuthenticationPrincipal Member member) {
        return "maintest";
    }

    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal Member member) {
        return "maintest";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "registertest";
    }

    @GetMapping("/member/{nickname}")
    public String GetMember(@PathVariable("nickname") String nickname, Model model) throws Exception {
        Member member = memberService.findByNickname(nickname);
        model.addAttribute("member", member);
        return "findByNickname";
    }

    /**
     * 단순 화면 출력이 아닌 데이터를 리턴하고자할 때 사용하는 리턴방식
     *
     * @ResponseEntity : 데이터, 상태코드(200, 400, 404, 405, 500 등)를 함께 리턴할 수 있음.
     * @ResponseBody : 데이터를 리턴할 수 있음.
     */
    @DeleteMapping("/member/{nickname}")
    public ResponseEntity deleteByNickname(@PathVariable String nickname) {
        try {
            memberService.deleteMember(nickname);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/member/update")
    public String updateForm(Model model, @AuthenticationPrincipal Member member) {
        String nickname = member.getNickname();
        try {
            Member member1 = memberService.findByNickname(nickname);
            model.addAttribute("member", member1);
            return "redirect:/member/update";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PutMapping("/member/{nickname}")
    public ResponseEntity update(@RequestBody Member member) throws Exception {
        memberService.updateMember(member);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/signUp")
    public String register(Member member) throws Exception {
        memberService.joinMember(member);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

    @GetMapping("/oauth/loginInfo")
    @ResponseBody
    public String oauthLoginInfo(@AuthenticationPrincipal OAuth2User oAuth2User){
        Map<String, Object> attributes = oAuth2User.getAttributes();
        System.out.println("attributes = " + attributes);

        // attributes == attributes1

        return attributes.toString();
    }
}
