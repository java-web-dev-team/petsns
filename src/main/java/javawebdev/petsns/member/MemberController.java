package javawebdev.petsns.member;

import javawebdev.petsns.Validation;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.member.dto.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;


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
    public String pwdChange(){
        return "pwd-edit";
    }

    @GetMapping("/member/profile/{id}")
    public String MyProfile(Model model, @PathVariable Integer id) {
        model.addAttribute("member", memberService.findById(id));
        return "profile";
    }

    @GetMapping("/member/{id}")
    public String updateForm(@PathVariable Integer id, Model model) {
        model.addAttribute("member", memberService.findById(id));
        return "profile-edit";
    }

    @PostMapping("/member/modify")
    public String updateMember(@AuthenticationPrincipal UserDetails userDetails, Member updatedMember) {
        Member member = memberService.findByNickname(userDetails.getUsername());
        updatedMember.setId(member.getId());
        memberService.updateMember(updatedMember);
        return "redirect:/member/profile/" + member.getId();
    }

    @PostMapping("/member/modify/pwd")
    public String updatePwd(@AuthenticationPrincipal UserDetails userDetails, String password){
        Member member = memberService.findByNickname(userDetails.getUsername());
        System.out.println("password = " + password);
        memberService.updateMember(password, member.getId());
        return "redirect:/member/profile/" + member.getId();
    }

    @PostMapping("/member/profile/modify/{nickname}")
    public String firstUpdateMember(@PathVariable String nickname, Member updatedMember, Model model) {
        Member member = memberService.findByNickname(nickname);
        model.addAttribute("member", member);
        updatedMember.setId(member.getId());
        memberService.updateMember(updatedMember);
        return "redirect:/login-form";
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
            return "redirect:/memberImg/"+nickname;
        }
        model.addAttribute("regiCheck", "닉네임, 이메일 중복확인을 진행해 주세요.");
        return "signup";
    }

    // 이미지 등록
    @GetMapping("/memberImg/{nickname}")
    public String memberImg(Model model, @PathVariable String nickname){
        Member member = memberService.findByNickname(nickname);
        model.addAttribute("member", member);
        return "memberImg";
    }

    @RequestMapping("/logout")
    public void logoutPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        new SecurityContextLogoutHandler().logout(request, null, null);
        response.sendRedirect(request.getHeader("referer"));
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
    public int pwdCheck(@RequestParam("password") String password, @AuthenticationPrincipal UserDetails userDetails){
        log.info("/pwdCheck ----");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String nowPwd = memberService.findByNickname(userDetails.getUsername()).getPassword();

        if (encoder.matches(password, nowPwd)) {
            return 1;
        } else{
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

    @Value("${upload.path2}")       //      /Users/profileImg
    private String upLoadPath;


    /** 회원 프로필 사진 등록 */
    @PostMapping("/memberImg/upload")
    public Path upLoadImg(MultipartFile uploadFiles){


            // 지원하지 않는 이미지 형식
            if(uploadFiles.getContentType().startsWith("image") == false){
                log.info("지원하지 않는 이미지 형식입니다.");
                return null;
            }

            // 실제 파일 이름 IE, Edge 는 전체 경로
            String originalName = uploadFiles.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
            log.info("fileName: " + fileName);


            //UUID
            String uuid = UUID.randomUUID().toString();

            //저장할 파일 이름 중간에 "_" 를 이용해서 구분
            String saveName = upLoadPath + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(saveName);

            try{
                uploadFiles.transferTo(savePath);
            } catch(Exception e){
                e.printStackTrace();
            }

            return savePath;
        }
    }

