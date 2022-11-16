package javawebdev.petsns.heart;

import javawebdev.petsns.member.MemberService;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.member.dto.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/H/")
@Controller
public class HeartController {

    private final HeartService heartService;
    private final MemberService memberService;

    @PostMapping("/save")
    public String save(@PathVariable Integer postId, @AuthenticationPrincipal PrincipalDetails customUser, Model model) throws Exception {
        Member member = memberService.customUserToMember(customUser);
        heartService.save(postId, member.getNickname());
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(@PathVariable Integer postId, @AuthenticationPrincipal PrincipalDetails customUser) throws Exception {
        Member member = memberService.customUserToMember(customUser);
        heartService.delete(postId, member.getNickname());
        return "redirect:/";
    }
}