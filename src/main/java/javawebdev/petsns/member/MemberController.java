package javawebdev.petsns.member;

import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/")
    public String main(){
        return "maintest";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admintest";
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
    public String updateForm(Model model, HttpSession session) {
        String nickname = (String) session.getAttribute("nickname");
        try {
            Member member = memberService.findByNickname(nickname);
            model.addAttribute("member", member);
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
        System.out.println("member = " + member);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}
