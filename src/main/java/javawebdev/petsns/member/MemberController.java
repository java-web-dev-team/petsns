package javawebdev.petsns.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javawebdev.petsns.follow.FollowService;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.member.dto.MemberDto;
import javawebdev.petsns.member.dto.PrincipalDetails;
import javawebdev.petsns.post.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final PostService postService;
    private final FollowService followService;
    private final AuthenticationManager authenticationManager;

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
    public String pwdChange(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        model.addAttribute("member", memberService.findByEmail(principalDetails.getMember().getEmail()));
        return "pwd-edit";
    }

    @GetMapping("/member/profile/{email}")
    public String MyProfile(@PathVariable String email, @AuthenticationPrincipal PrincipalDetails principalDetails, Model model, HttpServletRequest request) {

        Member me = memberService.findByEmail(principalDetails.getUsername());
        Member member = memberService.findByEmail(email);
        List<Member> followers = followService.getFollowersByFollowing(member.getNickname());
        List<Member> followings = followService.getFollowingsByFollower(member.getNickname());
        model.addAttribute("isMe", memberService.isMyProfile(me.getNickname(), member.getNickname()));
        model.addAttribute("member", member);
        model.addAttribute("posts", postService.getPosts(member.getId()));
        model.addAttribute("followers", followers);
        model.addAttribute("followings", followings);
        return "profile";
    }

    @GetMapping("/member/{email}")
    public String updateForm(@PathVariable String email, Model model) {
        Member member = memberService.findByEmail(email);
        model.addAttribute("member", member);
        return "profile-edit";
    }


    @PostMapping("/member/modify/{email}")
    public void updateMember(@PathVariable("email") String email, @AuthenticationPrincipal PrincipalDetails principalDetails, Member updatedMember) {
        updatedMember.setEmail(principalDetails.getUsername());

        postService.updatePostNickname(principalDetails.getName(), updatedMember.getNickname());
        memberService.updateMember(updatedMember);

        // ?????? ?????????
        if(principalDetails != null) {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(principalDetails.getName(), principalDetails.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        log.info("updateMember = -> ");
    }

    @PostMapping("/member/modify/pwd")
    public String updatePwd(@AuthenticationPrincipal PrincipalDetails principalDetails, String pwd, String password, String passwordCheck, Model model) {
        Member member = memberService.findByEmail(principalDetails.getUsername());
        model.addAttribute("model", model);
        if (pwd.equals(password)) {
            model.addAttribute("pwdCheckMsg", "?????? ??????????????? ????????????. ?????? ??????????????? ??????????????????.");
            return "redirect:/pwdChange";
        }

        if ((!pwd.equals(password)) && (password.equals(passwordCheck))) {
            memberService.updateMember(password, principalDetails.getUsername());
            return "redirect:/member/profile/" + principalDetails.getUsername();
        } else {
            model.addAttribute("pwdCheckMsg", "?????? ??????????????? ?????? ???????????? ????????? ????????????. ?????? ????????? ?????????.");
            return "redirect:/pwdChange";
        }
    }

    @GetMapping("/member/delete")
    public String deleteByNickname(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        memberService.deleteMember(principalDetails.getUsername());
        SecurityContextHolder.clearContext();   // ?????? ??? ???????????? ?????????
        return "redirect:/login-form";
    }


    @PostMapping("/signUp")
    public String register(Member member, Model model) {
        if (memberService.isValidNickname(member.getNickname())
                && memberService.isValidPwd(member.getPassword())
                && memberService.isValidEmail(member.getEmail())
                && memberService.expression(member.getEmail())) {
            memberService.joinMember(member);
            String email = memberService.findByEmail(member.getEmail()).getEmail();
            return "redirect:/memberImg/" + email;
        }
        model.addAttribute("regiCheck", "?????????, ????????? ??????????????? ????????? ?????????.");
        return "signup";
    }

    // ????????? ??????
    @GetMapping("/memberImg/{email}")
    public String memberImg(Model model, @PathVariable String email) {
        Member member = memberService.findByEmail(email);
        model.addAttribute("member", member);
        return "memberImg";
    }

    @RequestMapping("/logout")
    public void logoutPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        new SecurityContextLogoutHandler().logout(request, null, null);
        response.sendRedirect("/");
    }

    /**
     * ????????? ?????? ??????
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
    public int pwdCheck(@RequestParam("password") String password, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("/pwdCheck ----");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String nowPwd = memberService.findByEmail(principalDetails.getUsername()).getPassword();

        if (encoder.matches(password, nowPwd)) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * ????????? ?????? ??????
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
     * ????????? ?????? ??????
     */
    @RequestMapping(value = "/email/Certification", method = RequestMethod.POST)
    @ResponseBody
    public StringBuffer emailCertification(@RequestParam("email") String email) {
        log.info("email ?????? ????????? ????????? email -> " + email);
        StringBuffer randomInt = memberService.mailSend(email);
        log.info("randomInt = " + randomInt);
        return randomInt;
    }

    /**
     * ?????? ????????? ?????? ??????, Ajax
     */
//    @ResponseBody
//    @RequestMapping(value = "/memberImg/upload", method = RequestMethod.POST)
//    public MemberDto upLoadImg(@Param(value = "profileImg") MultipartFile profileImg) {
//
//        log.info("profileImg = " + profileImg);
//        // ???????????? ?????? ????????? ??????
//        if (!profileImg.getContentType().startsWith("image")) {
//            log.info("???????????? ?????? ????????? ???????????????.");
//        }
//
//        // ?????? ?????? ?????? IE, Edge ??? ?????? ??????
//        String originalName = profileImg.getOriginalFilename();
//        String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
//        log.info("fileName: " + fileName);
//
//
//        //UUID
//        String uuid = UUID.randomUUID().toString();
//
//        //????????? ?????? ?????? ????????? "_" ??? ???????????? ??????
//        String saveName = getUpLoadPath + File.separator + uuid + "_" + fileName;
//
//        Path savePath = Paths.get(saveName);
//
//        try {
//            profileImg.transferTo(savePath);
//            return new MemberDto(uuid, fileName);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    @PostMapping("/member/profile/modify/{email}")
    public String firstUpdateMember(@PathVariable String email, Member updatedMember, Model model) {
        Member member = memberService.findByEmail(email);
        updatedMember.setEmail(member.getEmail());
        model.addAttribute("member", member);
        memberService.updateProfileImg(updatedMember.getProfileImg(), member.getEmail());

        return "redirect:/login-form";
    }

    @PostMapping("/member/profileImg/modify/{email}")
    public String imgUpdateMember(@PathVariable String email, Member updatedMember, Model model) {
        Member member = memberService.findByEmail(email);
        updatedMember.setEmail(member.getEmail());
        model.addAttribute("member", member);
        memberService.updateProfileImg(updatedMember.getProfileImg(), member.getEmail());

        return "redirect:/member/profile/" + email;
    }

//    @GetMapping("/user")
//    public String testLogin(@AuthenticationPrincipal PrincipalDetails principalDetails) {
//        System.out.println("principalDetails.getMember() = " + principalDetails.getMember());
//        System.out.println("principalDetails.getMember().getEmail() = " + principalDetails.getMember().getEmail());
//        return "user";
//    }

    @ResponseBody
    @RequestMapping(value = "/searchMember", method = RequestMethod.POST)
    public List<Member> searchedMember(@RequestParam("memberName") String memberName, Model model) {
        List<Member> list = memberService.searchMember(memberName);
        return list;
    }
}

