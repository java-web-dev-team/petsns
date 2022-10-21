package javawebdev.petsns.comment;

import javawebdev.petsns.comment.dto.Comment;
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
    public String create(@PathVariable Integer postId, Integer memberId, String content, Model model) {
        Comment comment = commentService.create(postId, memberId, content);
        model.addAttribute("comment", comment);
        return "redirect:/posts/{postId}";
    }

    @PutMapping("/{commentId}")
    public String update(@PathVariable Integer postId, @PathVariable Integer commentId, String content) {
        commentService.update(commentId, content);
        return "redirect:/posts/{postId}";
    }

    @DeleteMapping("/{commentId}")
    public String delete(@PathVariable Integer commentId, @PathVariable Integer postId) {
        commentService.delete(commentId, postId);
        return "redirect:/posts/{postId}";
    }
}
