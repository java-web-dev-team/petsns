package javawebdev.petsns.post;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/petsns/*")
@AllArgsConstructor
@Slf4j
public class PostController {

    private PostService service;

    @GetMapping("/main")
    public void main() {
        log.info("main");
    }

    @PostMapping("/register") //등록
    public String register(Post post, RedirectAttributes rttr){
        log.info("register");
        service.register(post);
        rttr.addFlashAttribute("result");
        return "redirect:/petsns/main";
        }

    @PostMapping("/modify") //수정
    public String modify(Post post, RedirectAttributes rttr) {
        log.info("modify" + post);
        if(service.modify(post)) {
            rttr.addFlashAttribute("result", "success");
        }
        return "redirect:/petsns/main";
    }


    @PostMapping("/remove") //삭제
    public String remove(@RequestParam("id") Integer id) {
        log.info("remove" + id);
        return "redirect:/petsns/main";
    }
}