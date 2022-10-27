package javawebdev.petsns.post;

import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.post.dto.Post;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.websocket.server.PathParam;
import java.util.List;

@Controller
@RequestMapping("/posts")
@AllArgsConstructor
@Slf4j
public class PostController {

    private PostService service;

    @GetMapping
    public String main(Model model) {
        List<Post> posts = service.getList();
        model.addAttribute("posts", service.getList());
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
    public String register(@ModelAttribute Post post, RedirectAttributes rttr, @AuthenticationPrincipal Member member){
        log.info("register post");
        String name = member.getNickname();
        post.setNickname(name);
        service.register(post);
        rttr.addFlashAttribute("result");
        return "redirect:/posts";
        }

    @GetMapping("/read/{id}")
    public String read(Model model, @PathVariable Integer id){
        log.info("read" + id);
        List<Post> posts = service.read(id);
        log.info(posts.toString());
        model.addAttribute("postDetail", posts);
        return "post/view";
    }

    @PostMapping("/modify") //수정
    public String modify(Post post, RedirectAttributes rttr, @AuthenticationPrincipal Member member, Model model) {
        log.info("modify" + post);
        if(service.modify(post)) {
            model.addAttribute("nickname", member.getNickname());
            rttr.addFlashAttribute("result", "success");

        }
        return "redirect:/posts";
    }


    @PostMapping("/remove") //삭제
    public String remove(@RequestParam("id") Integer id) {
        log.info("remove" + id);
        return "redirect:/posts";
    }
}