package javawebdev.petsns.follow;

import javawebdev.petsns.member.MemberService;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.member.dto.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class FollowController {

    private final FollowService followService;
    private final MemberService memberService;

    @PostMapping("/follows/{memberId}")
    public String click(@PathVariable Integer memberId, @AuthenticationPrincipal PrincipalDetails customUser) {
        Member member = memberService.customUserToMember(customUser);
        Member target = memberService.findById(memberId);
        followService.click(member.getNickname(), target.getNickname());
        return "redirect:/follows/{followId}";
    }
}
