package javawebdev.petsns.help;

import javawebdev.petsns.member.MemberService;
import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/helps")
@RequiredArgsConstructor
@Controller
public class HelpController {

    private final HelpService helpService;
    private final MemberService memberService;

    @GetMapping("/form")
    public String getForm(@AuthenticationPrincipal Member member, Model model) {
        model.addAttribute("member", member);
        return "/helps/help-form";
    }

    @PostMapping("/")
    public String create(@AuthenticationPrincipal UserDetails userDetails, String content) throws Exception {
        Member member = memberService.findByNickname(userDetails.getUsername());
        helpService.create(member.getId(), content);
        return "redirect:/helps";
    }

    @GetMapping("/")
    public String getMyList(@AuthenticationPrincipal UserDetails userDetails, Model model) throws Exception {
        Member member = memberService.findByNickname(userDetails.getUsername());
        model.addAttribute("helps", helpService.getMyHelps(member.getId()));
        return "/helps/list";
    }

    @GetMapping("/{helpId}")
    public String getOne(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer helpId, Model model) throws Exception {
        Member member = memberService.findByNickname(userDetails.getUsername());
        model.addAttribute("help", helpService.getHelp(member.getId(), helpId));
        return "/helps/detail";
    }

    @PutMapping("/{helpId}")
    public String update(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer helpId, String updatedContent) throws Exception {
        Member member = memberService.findByNickname(userDetails.getUsername());
        helpService.update(member.getId(), helpId, updatedContent);
        return "redirect:/helps/{helpId}";
    }

    @DeleteMapping("/{helpId}")
    public String delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer helpId) throws Exception {
        Member member = memberService.findByNickname(userDetails.getUsername());
        helpService.delete(member.getId(), helpId);
        return "redirect:/helps";
    }
}
