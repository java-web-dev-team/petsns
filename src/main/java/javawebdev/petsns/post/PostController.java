package javawebdev.petsns.post;

import javawebdev.petsns.member.MemberService;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.post.dto.Post;
import javawebdev.petsns.post.dto.PostVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberService memberService;

    //  내가 팔로우한 사람의 게시물 가져오기
    @GetMapping("/posts")
    public String getPosts(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Member member = memberService.findByNickname(userDetails.getUsername());
        List<PostVO> myFollowingPosts = postService.getMyFollowingPosts(member);
        model.addAttribute("member", member);
        model.addAttribute("posts", myFollowingPosts);
        return "/post/main";
    }

    //  게시물 작성 폼 가져오기
    @GetMapping("/posts/post-form")
    public String registerForm(@AuthenticationPrincipal UserDetails userDetails, Model model){
        Member member = memberService.findByNickname(userDetails.getUsername());
        model.addAttribute("member", member);
        return "/post/register";
    }

    //  게시물 등록
    @PostMapping("/posts")
    public String register(@ModelAttribute Post post, @AuthenticationPrincipal UserDetails userDetails){
        Member member = memberService.findByNickname(userDetails.getUsername());
        postService.register(member, post);
        return "redirect:/posts";
    }

    //  개별 게시물 가져오기
    @GetMapping("/posts/{postId}")
    public String read(Model model, @PathVariable Integer postId){
        PostVO postVO = postService.getPost(postId);
        model.addAttribute("post", postVO);
        return "/post/view";
    }

    //  게시물 수정 폼 가져오기
    @GetMapping("/posts/{postId}/update-form")
    public String updateForm(Model model, @PathVariable Integer postId){
        Post post = postService.getPostForUpdate(postId);
        model.addAttribute("post", post);
        return "/post/update";
    }

    //  게시물 수정하기(content 만 수정 가능)
    @PostMapping("/posts/{postId}/update")
    public String update(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer postId, String content) {
        Member member = memberService.findByNickname(userDetails.getUsername());
        postService.update(member, postId, content);
        return "redirect:/posts/{postId}";
    }

    //  게시글 삭제
    @PostMapping("/posts/{postId}/delete")
    public String delete(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Integer postId) {
        Member member = memberService.findByNickname(userDetails.getUsername());
        postService.remove(member, postId);
        return "redirect:/posts";
    }
}