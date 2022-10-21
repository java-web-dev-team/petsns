package javawebdev.petsns.member;

import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.NotFoundException;
import org.apache.ibatis.javassist.tools.web.BadHttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("register")
    public String registerForm() {
        return "register";
    }

    @GetMapping("{nickname}")
    public String GetMember(@PathVariable("nickname") String nickname, Model model) throws Exception {
        Member member = memberService.findByNickname(nickname);
        model.addAttribute("member", member);
        return "member/findByNickname";
    }

    /**
     * 단순 화면 출력이 아닌 데이터를 리턴하고자할 때 사용하는 리턴방식
     *
     * @ResponseEntity : 데이터, 상태코드(200, 400, 404, 405, 500 등)를 함께 리턴할 수 있음.
     * @ResponseBody : 데이터를 리턴할 수 있음.
     */
    @DeleteMapping("{nickname}")
    public ResponseEntity deleteByNickname(@PathVariable String nickname) {
        try {
            memberService.deleteMember(nickname);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("update")
    public String updateForm(Model model, HttpSession session) {
        String nickname = (String) session.getAttribute("nickname");
        try {
            Member member = memberService.findByNickname(nickname);
            model.addAttribute("member", member);
            return "member/update";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PutMapping("{nickname}")
    public ResponseEntity update(@RequestBody Member member) throws Exception{
        memberService.updateMember(member);
        return new ResponseEntity(HttpStatus.OK);
    }
}
