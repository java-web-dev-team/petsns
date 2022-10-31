package javawebdev.petsns.member;

import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/")
    public String main() {
        return "redirect:/posts";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "login-form";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "registertest";
    }

//    @GetMapping("/members/{nickname}")
//    public String GetMember(@PathVariable("nickname") String nickname, Model model) throws Exception {
//        Member member = memberService.findByNickname(nickname);
//        model.addAttribute("member", member);
//        return "findByNickname";
//    }

    /**
     * 단순 화면 출력이 아닌 데이터를 리턴하고자할 때 사용하는 리턴방식
     *
     * @ResponseEntity : 데이터, 상태코드(200, 400, 404, 405, 500 등)를 함께 리턴할 수 있음.
     * @ResponseBody : 데이터를 리턴할 수 있음.
     */
    @GetMapping("/member/delete")
    public String deleteByNickname(@AuthenticationPrincipal UserDetails member) {
        try {
            System.out.println("member = " + member);
            System.out.println("member.getUsername() = " + member.getUsername());
            memberService.deleteMember(member.getUsername());
            SecurityContextHolder.clearContext();   // 탈퇴 시 로그아웃 처리됌
            new ResponseEntity(HttpStatus.OK);
            return "redirect:/login-form";
        } catch (Exception e) {
            e.printStackTrace();
            new ResponseEntity(HttpStatus.BAD_REQUEST);
            return null;
        }
    }

    @GetMapping("/member/modify")
    public String updateForm() {
        return "profile-edit";
    }

    @PostMapping("/member/modify")
    public ResponseEntity update(@AuthenticationPrincipal UserDetails member) throws Exception {
        memberService.updateMember((Member)member);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/signUp")
    public String register(Member member) throws Exception {
        memberService.joinMember(member);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        new SecurityContextLogoutHandler().logout(request, response, authentication);
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }

}

