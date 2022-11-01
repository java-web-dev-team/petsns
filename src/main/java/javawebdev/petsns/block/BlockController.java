package javawebdev.petsns.block;

import javawebdev.petsns.member.MemberService;
import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/blocks")
@RequiredArgsConstructor
@Controller
public class BlockController {

    private final BlockService blockService;
    private final MemberService memberService;

    @PostMapping("/{blocked}")
    public String block(@PathVariable String blocked, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
        Member member = memberService.findByNickname(userDetails.getUsername());
        blockService.block(member.getNickname(), blocked);
        return "redirect:..";
    }

    @DeleteMapping("/{blocked}")
    public String cancel(@PathVariable String blocked, @AuthenticationPrincipal UserDetails userDetails) {
        Member member = memberService.findByNickname(userDetails.getUsername());
        blockService.cancel(member.getNickname(), blocked);
        return "redirect:..";
    }
}
