package javawebdev.petsns.follow;

import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@RequestMapping("/follows/{memberId}")
@Controller
public class FollowController {

    private final FollowService followService;
    private final MemberRepository memberRepository;

    @PostMapping("/{id}")
    public String click(@PathVariable Integer id, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        Member following = memberRepository.findMemberByNickname(userDetails.getUsername());
        Member follower = memberRepository.selectByIdNotOptional(id);
        followService.click(follower.getNickname(), following.getNickname());
        return "redirect:/follows/{followId}";
    }
}
