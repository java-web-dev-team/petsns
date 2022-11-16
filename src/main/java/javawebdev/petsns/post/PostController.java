package javawebdev.petsns.post;

import javawebdev.petsns.heart.HeartService;
import javawebdev.petsns.heart.dto.Heart;
import javawebdev.petsns.member.MemberService;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.member.dto.PrincipalDetails;
import javawebdev.petsns.post.dto.Post;
import javawebdev.petsns.post.dto.PostVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final MemberService memberService;
    private final HeartService heartService;

    //  내가 팔로우한 사람의 게시물 가져오기
    @GetMapping("/posts")
    public String getPosts(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model) {
        Member member = memberService.findByEmail(principalDetails.getUsername());
        model.addAttribute("member", member);
        List<PostVO> myFollowingPosts = postService.getMyFollowingPosts(member);
        List<PostVO> myPosts = postService.getMyPosts(member.getNickname());

        List<PostVO> getPost = new ArrayList<>();

        getPost.addAll(myPosts);
        getPost.addAll(myFollowingPosts);
        if(getPost.isEmpty()){
            return "/post/main";
        }
        model.addAttribute("posts", getPost);
        return "/post/main";
    }

    //  게시물 작성 폼 가져오기
    @GetMapping("/posts/post-form")
    public String registerForm(@AuthenticationPrincipal PrincipalDetails customUser, Model model){
        Member member = memberService.customUserToMember(customUser);
        model.addAttribute("member", member);
        return "/post/register";
    }

    //  게시물 등록
    @PostMapping("/posts")
    public String register(@ModelAttribute Post post, @AuthenticationPrincipal PrincipalDetails customUser){
        Member member = memberService.customUserToMember(customUser);
        postService.register(member, post);
        return "redirect:/posts";
    }

    //  개별 게시물 가져오기
    @GetMapping("/posts/{postId}")
    public String read(Model model, @PathVariable Integer postId, @AuthenticationPrincipal PrincipalDetails customUser){
        Member member = memberService.customUserToMember(customUser);
        model.addAttribute("member", member);
        PostVO postVO = postService.getPost(postId);
        model.addAttribute("post", postVO);
        return "post/view";
    }

    //  게시물 수정 폼 가져오기
    @GetMapping("/posts/{postId}/update")
    public String updateForm(Model model, @PathVariable Integer postId, @AuthenticationPrincipal PrincipalDetails customUser){
        Member member = memberService.customUserToMember(customUser);
        model.addAttribute("member", member);
        PostVO postVO = postService.getPost(postId);
        model.addAttribute("post", postVO);
        Post post = postService.getPostForUpdate(postId);
        model.addAttribute("postId", postId);
        return "post/update";
    }

    //  게시물 수정하기(content 만 수정 가능)
    @PostMapping("/posts/{postId}/update")
    public String update(@AuthenticationPrincipal PrincipalDetails customUser, @PathVariable Integer postId, String content, Model model) {
        Member member = memberService.customUserToMember(customUser);
        model.addAttribute("postId" , postId);
        postService.update(member, postId, content);
        return "redirect:/posts/{postId}";
    }

    //  게시글 삭제
    @PostMapping("/posts/{postId}/delete")
    public String delete(@AuthenticationPrincipal PrincipalDetails customUser, @PathVariable Integer postId, Model model) {
        Member member = memberService.customUserToMember(customUser);
        model.addAttribute("postId", postId);
        postService.remove(member, postId);
        return "redirect:/posts";
    }
}