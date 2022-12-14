package javawebdev.petsns.comment;

import javawebdev.petsns.comment.dto.Comment;
import javawebdev.petsns.member.MemberService;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.member.dto.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
@Controller
public class CommentController {

    private final CommentService commentService;
    private final MemberService memberService;

    @PostMapping("/")
    public String create(@PathVariable Integer postId, @AuthenticationPrincipal PrincipalDetails customUser, String content, Model model) throws Exception {
        Member member = memberService.customUserToMember(customUser);
        Comment comment = commentService.create(postId, member.getId(), content);
        model.addAttribute("comment", comment);
        return "redirect:/posts/{postId}";
    }

    @PutMapping("/{commentId}")
    public String update(@PathVariable Integer postId, @PathVariable Integer commentId, @AuthenticationPrincipal PrincipalDetails customUser, String updatedContent) throws Exception {
        Member member = memberService.customUserToMember(customUser);
        commentService.update(member.getId(), postId, commentId, updatedContent);
        return "redirect:/posts/{postId}";
    }

    @DeleteMapping("/{commentId}")
    public String delete(@PathVariable Integer commentId, @PathVariable Integer postId, @AuthenticationPrincipal PrincipalDetails customUser) throws Exception {
        Member member = memberService.customUserToMember(customUser);
        commentService.delete(member.getId(), postId, commentId);
        return "redirect:/posts/{postId}";
    }
}
