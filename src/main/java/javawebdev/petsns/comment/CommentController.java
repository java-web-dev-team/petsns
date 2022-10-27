package javawebdev.petsns.comment;

import javawebdev.petsns.comment.dto.Comment;
import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
@Controller
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/")
    public String create(@PathVariable Integer postId, Member member, String content, Model model) throws Exception {
        Comment comment = commentService.create(postId, member.getId(), content);
        model.addAttribute("comment", comment);
        return "redirect:/posts/{postId}";
    }

    @PutMapping("/{commentId}")
    public String update(@PathVariable Integer postId, @PathVariable Integer commentId, Member member, String updatedContent) throws Exception {
        commentService.update(member.getId(), postId, commentId, updatedContent);
        return "redirect:/posts/{postId}";
    }

    @DeleteMapping("/{commentId}")
    public String delete(@PathVariable Integer commentId, @PathVariable Integer postId, Member member) throws Exception {
        commentService.delete(member.getId(), postId, commentId);
        return "redirect:/posts/{postId}";
    }
}
