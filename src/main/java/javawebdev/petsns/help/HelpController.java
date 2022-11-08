package javawebdev.petsns.help;

import javawebdev.petsns.member.MemberService;
import javawebdev.petsns.member.dto.CustomUser;
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
    public String getForm(@AuthenticationPrincipal CustomUser customUser, Model model) {
        Member member = memberService.customUserToMember(customUser);
        model.addAttribute("member", member);
        return "/helps/help-form";
    }

    @PostMapping("/")
    public String create(@AuthenticationPrincipal CustomUser customUser, String content) throws Exception {
        Member member = memberService.customUserToMember(customUser);
        helpService.create(member.getId(), content);
        return "redirect:/helps";
    }

    @GetMapping("/")
    public String getMyList(@AuthenticationPrincipal CustomUser customUser, Model model) {
        Member member = memberService.customUserToMember(customUser);
        model.addAttribute("helps", helpService.getMyHelps(member.getId()));
        return "/helps/list";
    }

    @GetMapping("/{helpId}")
    public String getOne(@AuthenticationPrincipal CustomUser customUser, @PathVariable Integer helpId, Model model) {
        Member member = memberService.customUserToMember(customUser);
        model.addAttribute("help", helpService.getHelp(member.getId(), helpId));
        return "/helps/detail";
    }

    @PutMapping("/{helpId}")
    public String update(@AuthenticationPrincipal CustomUser customUser, @PathVariable Integer helpId, String updatedContent) throws Exception {
        Member member = memberService.customUserToMember(customUser);
        helpService.update(member.getId(), helpId, updatedContent);
        return "redirect:/helps/{helpId}";
    }

    @DeleteMapping("/{helpId}")
    public String delete(@AuthenticationPrincipal CustomUser customUser, @PathVariable Integer helpId) throws Exception {
        Member member = memberService.customUserToMember(customUser);
        helpService.delete(member.getId(), helpId);
        return "redirect:/helps";
    }
}
