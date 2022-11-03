package javawebdev.petsns.heart;

import javawebdev.petsns.heart.dto.Heart;
import javawebdev.petsns.member.MemberService;
import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/hearts")
@Controller
public class HeartController {

    private final HeartService heartService;
    private final MemberService memberService;

    @PostMapping("/")
    public String save(@PathVariable Integer postId, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        Member member = memberService.findByNickname(userDetails.getUsername());
        heartService.save(postId, member.getNickname());
        return "redirect:/posts/{postId}";
    }

    @DeleteMapping("/")
    public String delete(@PathVariable Integer postId, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        Member member = memberService.findByNickname(userDetails.getUsername());
        heartService.delete(postId, member.getNickname());
        return "redirect:/posts/{postId}";
    }
}