package javawebdev.petsns.heart;

import javawebdev.petsns.heart.dto.Heart;
import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/hearts")
@Controller
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/")
    public String save(@PathVariable Integer postId, @AuthenticationPrincipal Member member) throws Exception {
        heartService.save(postId, member.getNickname());
        return "redirect:/posts/{postId}";
    }

    @DeleteMapping("/")
    public String delete(@PathVariable Integer postId, @AuthenticationPrincipal Member member) throws Exception {
         heartService.delete(postId, member.getNickname());
        return "redirect:/posts/{postId}";
    }
}