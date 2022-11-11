package javawebdev.petsns.heart;

import javawebdev.petsns.member.MemberService;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.member.dto.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/hearts")
@Controller
public class HeartController {

    private final HeartService heartService;
    private final MemberService memberService;

    @PostMapping("/")
    public String save(@PathVariable Integer postId, @AuthenticationPrincipal PrincipalDetails customUser) throws Exception {
        Member member = memberService.customUserToMember(customUser);
        heartService.save(postId, member.getNickname());
        return "redirect:/posts/{postId}";
    }

    @DeleteMapping("/")
    public String delete(@PathVariable Integer postId, @AuthenticationPrincipal PrincipalDetails customUser) throws Exception {
        Member member = memberService.customUserToMember(customUser);
        heartService.delete(postId, member.getNickname());
        return "redirect:/posts/{postId}";
    }
}