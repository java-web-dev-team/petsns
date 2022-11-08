package javawebdev.petsns.member;

import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final PostService postService;

    @Value("${upload.path2}")
    private String getUpLoadPath;


    @GetMapping("/")
    public String main() {
        return "redirect:/posts";
    }

    @GetMapping("/login-form")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "exception", required = false) String exception,
                            Model model
    ) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "login-form";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "signup";
    }

    @GetMapping("/pwdChange")
    public String pwdChange() {
        return "pwd-edit";
    }

    @GetMapping("/member/profile/{id}")
    public String MyProfile(Model model, @PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails) {
        Member me = memberService.findByNickname(userDetails.getUsername());
        Member member = memberService.findById(id);
        model.addAttribute("isMe", memberService.isMyProfile(me.getNickname(), member.getNickname()));
        model.addAttribute("member", member);
        model.addAttribute("posts", postService.getPosts(member.getId()));
        return "profile";
    }

    @GetMapping("/member/{id}")
    public String updateForm(@PathVariable Integer id, Model model) {
        model.addAttribute("member", memberService.findById(id));
        return "profile-edit";
    }

    @PostMapping("/member/modify")
    public String updateMember(@AuthenticationPrincipal UserDetails userDetails, Member updatedMember) {
        Integer id = memberService.findByNickname(userDetails.getUsername()).getId();
        updatedMember.setId(id);
        memberService.updateMember(updatedMember);
        return "redirect:/member/profile/" + id;
    }

    @PostMapping("/member/modify/pwd")
    public String updatePwd(@AuthenticationPrincipal UserDetails userDetails, String pwd, String password, String passwordCheck, Model model) {
        Member member = memberService.findByNickname(userDetails.getUsername());
        System.out.println("pwd = " + pwd);
        System.out.println("password = " + password);
        System.out.println("passwordCheck = " + passwordCheck);
        if (pwd.equals(password)) {
            model.addAttribute("pwdCheckMsg", "현재 비밀번호와 같습니다. 다른 비밀번호로 변경해주세요.");
            return "redirect:/pwdChange";
        }

        if ((!pwd.equals(password)) && (password.equals(passwordCheck))) {
            memberService.updateMember(password, member.getId());
            return "redirect:/member/profile/" + member.getId();
        } else {
            model.addAttribute("pwdCheckMsg", "바꿀 비밀번호와 바꿀 비밀번호 체크가 다릅니다. 다시 확인해 주세요.");
            return "redirect:/pwdChange";
        }
    }

    @GetMapping("/member/delete")
    public String deleteByNickname(@AuthenticationPrincipal UserDetails userDetails) {
        memberService.deleteMember(userDetails.getUsername());
        SecurityContextHolder.clearContext();   // 탈퇴 시 로그아웃 처리됌
        return "redirect:/login-form";
    }


    @PostMapping("/signUp")
    public String register(Member member, Model model) {
        if (memberService.isValidNickname(member.getNickname())
                && memberService.isValidPwd(member.getPassword())
                && memberService.isValidEmail(member.getEmail())
                && memberService.expression(member.getEmail())) {
            memberService.joinMember(member);
            String nickname = memberService.findByNickname(member.getNickname()).getNickname();
            return "redirect:/memberImg/" + nickname;
        }
        model.addAttribute("regiCheck", "닉네임, 이메일 중복확인을 진행해 주세요.");
        return "signup";
    }

    // 이미지 등록
    @GetMapping("/memberImg/{nickname}")
    public String memberImg(Model model, @PathVariable String nickname) {
        Member member = memberService.findByNickname(nickname);
        model.addAttribute("member", member);
        return "memberImg";
    }

    @RequestMapping("/logout")
    public void logoutPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        new SecurityContextLogoutHandler().logout(request, null, null);
        response.sendRedirect("/");
    }

    /**
     * 이메일 중복 체크
     */
    @ResponseBody
    @RequestMapping(value = "/emailCheck", method = RequestMethod.POST)
    public int emailCheck(@RequestParam("email") String email) {
        log.info("/emailCheck ----");
        log.info(email);
        if (memberService.expression(email)) {
            int count = memberService.emailCheck(email);
            return count;
        } else {
            return -1;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/pwdCheck", method = RequestMethod.POST)
    public int pwdCheck(@RequestParam("password") String password, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("/pwdCheck ----");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String nowPwd = memberService.findByNickname(userDetails.getUsername()).getPassword();

        if (encoder.matches(password, nowPwd)) {
            return 1;
        } else {
            return 0;
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


    /**
     * 이메일 인증 구현
     */
    @RequestMapping(value = "/email/Certification", method = RequestMethod.POST)
    @ResponseBody
    public StringBuffer emailCertification(@RequestParam("email") String email) {
        log.info("email 인증 메일이 들어옴 email -> " + email);
        StringBuffer randomInt = memberService.mailSend(email);
        log.info("randomInt = " + randomInt);
        return randomInt;
    }

    /**
     * 회원 프로필 사진 생성, Ajax
     */
    @ResponseBody
    @RequestMapping(value = "/memberImg/upload", method = RequestMethod.POST)
    public Path upLoadImg(@Param(value = "profileImg") MultipartFile profileImg) {

        log.info("profileImg = " + profileImg);
        // 지원하지 않는 이미지 형식
        if (!profileImg.getContentType().startsWith("image")) {
            log.info("지원하지 않는 이미지 형식입니다.");
        }

        // 실제 파일 이름 IE, Edge 는 전체 경로
        String originalName = profileImg.getOriginalFilename();
        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
        log.info("fileName: " + fileName);


        //UUID
        String uuid = UUID.randomUUID().toString();

        //저장할 파일 이름 중간에 "_" 를 이용해서 구분
        String saveName = getUpLoadPath + File.separator + uuid + "_" + fileName;

        Path savePath = Paths.get(saveName);

        try {
            profileImg.transferTo(savePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("savePath = " + savePath);
        return savePath;
    }

    @PostMapping("/member/profile/modify/{nickname}")
    public String firstUpdateMember(@PathVariable String nickname, Member updatedMember, Model model) {
        Member member = memberService.findByNickname(nickname);
        updatedMember.setId(member.getId());
        model.addAttribute("member", member);
        System.out.println("updatedMember.getProfileImg() = " + updatedMember.getProfileImg());
        System.out.println("updatedMember.getId() = " + updatedMember.getId());
        memberService.updateProfileImg(updatedMember.getProfileImg(), updatedMember.getId());
        return "redirect:/login-form";
    }
}

