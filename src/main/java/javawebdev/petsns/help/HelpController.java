package javawebdev.petsns.help;

import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/helps")
@RequiredArgsConstructor
@Controller
public class HelpController {

    private final HelpService helpService;

    @GetMapping("/form")
    public String getForm(@AuthenticationPrincipal Member member, Model model) {
        model.addAttribute("member", member);
        return "/helps/help-form";
    }

    @PostMapping("/")
    public String create(@AuthenticationPrincipal Member member, String content) throws Exception {
        helpService.create(member.getId(), content);
        return "redirect:/helps";
    }

    @GetMapping("/")
    public String getMyList(@AuthenticationPrincipal Member member, Model model) throws Exception {
        model.addAttribute("helps", helpService.getMyHelps(member.getId()));
        return "/helps/list";
    }

    @GetMapping("/{helpId}")
    public String getOne(@AuthenticationPrincipal Member member, @PathVariable Integer helpId, Model model) throws Exception {
        model.addAttribute("help", helpService.getHelp(member.getId(), helpId));
        return "/helps/detail";
    }

    @PutMapping("/{helpId}")
    public String update(@AuthenticationPrincipal Member member, @PathVariable Integer helpId, String updatedContent) throws Exception {
        helpService.update(member.getId(), helpId, updatedContent);
        return "redirect:/helps/{helpId}";
    }

    @DeleteMapping("/{helpId}")
    public String delete(@AuthenticationPrincipal Member member, @PathVariable Integer helpId) throws Exception {
        helpService.delete(member.getId(), helpId);
        return "redirect:/helps";
    }
}
