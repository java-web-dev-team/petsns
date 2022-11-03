package javawebdev.petsns.member;

import javawebdev.petsns.Validation;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.member.dto.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    private final Validation validation;


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
        return "signup";
    }

    @GetMapping("/member/profile/{id}")
    public String profile(Model model, @PathVariable Integer id) {
        model.addAttribute("member", memberRepository.selectByIdNotOptional(id));
        return "profile";
    }

    @GetMapping("/member/{id}")
    public String updateForm(@PathVariable Integer id, Model model) {
        model.addAttribute("member", memberRepository.selectByIdNotOptional(id));
        return "profile-edit";
    }

    @PostMapping("/member/modify")
    public String updateMember(@AuthenticationPrincipal PrincipalDetails userDetails, Member updatedMember) {
        Member member = memberRepository.findMemberByNickname(userDetails.getName());
        updatedMember.setId(member.getId());
        memberService.updateMember(updatedMember);
        return "redirect:/member/profile/" + member.getId();
    }

    /**
     * 단순 화면 출력이 아닌 데이터를 리턴하고자할 때 사용하는 리턴방식
     *
     * @ResponseEntity : 데이터, 상태코드(200, 400, 404, 405, 500 등)를 함께 리턴할 수 있음.
     * @ResponseBody : 데이터를 리턴할 수 있음.
     */
    @GetMapping("/member/delete")
    public String deleteByNickname(@AuthenticationPrincipal UserDetails member) {
        try {
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


    @PostMapping("/signUp")
    public String register(Member member, Model model) {
        if(validation.isValidEmail(member.getEmail()) && (memberRepository.findMemberByNickname(member.getNickname()) == null)){
            memberService.joinMember(member);
            return "redirect:/login-form";
        }
        model.addAttribute("regiCheck", "닉네임, 이메일 중복확인을 진행해 주세요.");
        return "signup";
    }

    @RequestMapping("/logout")
    public void logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 이메일 중복 체크
     */
    @ResponseBody
    @RequestMapping(value = "/emailCheck", method = RequestMethod.POST)
    public int emailCheck(@RequestParam("email") String email) {
        log.info("/emailCheck ----");
        log.info(email);
        if(validation.isValidEmail(email)){
            int count = memberService.emailCheck(email);
            return count;
        } else {
            return -1;
        }
    }

    /**
     * 닉네임 중복 체크
     */
    @ResponseBody
    @RequestMapping(value = "/idCheck", method = RequestMethod.POST)
    public int idCheck(@RequestParam("nickname") String nickname) {
        log.info("/idCheck ----");
        log.info(nickname);
        int count = memberService.idCheck(nickname);
        return count;
    }
}

