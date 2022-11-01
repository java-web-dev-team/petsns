package javawebdev.petsns.comment;

import javawebdev.petsns.comment.dto.Comment;
import javawebdev.petsns.member.MemberService;
import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public String create(@PathVariable Integer postId, @AuthenticationPrincipal UserDetails userDetails, String content, Model model) throws Exception {
        Member member = memberService.findByNickname(userDetails.getUsername());
        Comment comment = commentService.create(postId, member.getId(), content);
        model.addAttribute("comment", comment);
        return "redirect:/posts/{postId}";
    }

    @PutMapping("/{commentId}")
    public String update(@PathVariable Integer postId, @PathVariable Integer commentId, @AuthenticationPrincipal UserDetails userDetails, String updatedContent) throws Exception {
        Member member = memberService.findByNickname(userDetails.getUsername());
        commentService.update(member.getId(), postId, commentId, updatedContent);
        return "redirect:/posts/{postId}";
    }

    @DeleteMapping("/{commentId}")
    public String delete(@PathVariable Integer commentId, @PathVariable Integer postId, UserDetails userDetails) throws Exception {
        Member member = memberService.findByNickname(userDetails.getUsername());
        commentService.delete(member.getId(), postId, commentId);
        return "redirect:/posts/{postId}";
    }
}
