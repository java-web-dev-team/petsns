package javawebdev.petsns.post;

import javawebdev.petsns.comment.CommentService;
import javawebdev.petsns.post.dto.Post;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
@AllArgsConstructor
@Slf4j
public class PostController {

    private PostService postService;
    private CommentService commentService;

    @GetMapping
    public String main(Model model) {
        List<Post> posts = postService.getList();
        model.addAttribute("posts", postService.getList());

        log.info("main");
        return "post/main";
    }

    @GetMapping("/post-form")
    public String register(){
        log.info("register get");
        return "post/register";
    }

//    @GetMapping("/read")
//    public String view() {
//
//        return "post/view";
//    }

    @PostMapping() //등록
    public String register(@ModelAttribute Post post, @AuthenticationPrincipal UserDetails member){
        log.info("register post");
        String name = member.getUsername();
        post.setNickname(name);
        postService.register(post);
        return "redirect:/posts";
    }

    @GetMapping("/read/{id}")
    public String read(Model model, @PathVariable Integer id){
        log.info("read" + id);
        Post posts = postService.read(id);
        log.info(posts.toString());
        model.addAttribute("postDetail", posts);
        return "post/view";
    }

    @GetMapping("/update/{id}")
    public String edit(Model model, @PathVariable Integer id){
        log.info("edit: " + id);
        Post posts = postService.read(id);
        log.info(posts.toString());
        model.addAttribute("postDetail", posts);
        return "post/update";
    }

    @PostMapping("/update") //수정
    public String update(Post post, @AuthenticationPrincipal UserDetails member) {
        log.info("update");
        post.setNickname(member.getUsername());
        postService.update(post);
        return "redirect:/posts";
    }


    @PostMapping("/remove") //삭제
    public String remove(Post post, @AuthenticationPrincipal UserDetails member) {
        log.info("remove");
        post.setNickname(member.getUsername());
        postService.remove(post);
        return "redirect:/posts";
    }
}