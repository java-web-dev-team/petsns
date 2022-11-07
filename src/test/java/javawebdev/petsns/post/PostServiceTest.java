package javawebdev.petsns.post;

import javawebdev.petsns.comment.CommentMapper;
import javawebdev.petsns.follow.FollowMapper;
import javawebdev.petsns.follow.dto.Follow;
import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.MemberService;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.post.dto.Post;
import javawebdev.petsns.post.dto.PostVO;
import javawebdev.petsns.post.dto.UpdateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.doesNotHave;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostMapper postMapper;

    @Autowired
    UploadMapper uploadMapper;

    @Autowired
    FollowMapper followMapper;

    @BeforeEach
    void beforeEach() {
        List<Post> all = postMapper.findAll();
        for (Post post : all) {
            postMapper.delete(post);
        }
    }

    @Test
    void register() {
        //  given
        Member member = memberRepository.findAll().get(0);
        UpdateDTO updateDTO1 = new UpdateDTO();
        updateDTO1.setPath("testPath");
        updateDTO1.setUuid(UUID.randomUUID().toString());
        updateDTO1.setImgName("test");

        UpdateDTO updateDTO2 = new UpdateDTO();
        updateDTO2.setPath("testPath");
        updateDTO2.setUuid(UUID.randomUUID().toString());
        updateDTO2.setImgName("test");

        //  when
        String content = "content";
        Post post = new Post(content, member.getNickname());

        post.setImageDTOList(new ArrayList<>());
        post.getImageDTOList().add(updateDTO1);
        post.getImageDTOList().add(updateDTO2);

        postService.register(member, post);
        List<Post> all = postMapper.findAll();
        Post findPost = all.get(all.size() - 1);
        //  then
        assertThat(uploadMapper.findByPostId(findPost.getId()).size()).isEqualTo(2);
        assertThat(findPost.getContent()).isEqualTo(content);
        assertThat(findPost.getNickname()).isEqualTo(member.getNickname());
    }

    @Test
    void getMyFollowingPosts() {
        //  given
        Member memberA = memberRepository.findAll().get(0);
        Member memberB = memberRepository.findAll().get(1);

        followMapper.save(new Follow(memberB.getNickname(), memberA.getNickname()));

        UpdateDTO updateDTO1 = new UpdateDTO();
        updateDTO1.setPath("testPath");
        updateDTO1.setUuid(UUID.randomUUID().toString());
        updateDTO1.setImgName("test");

        UpdateDTO updateDTO2 = new UpdateDTO();
        updateDTO2.setPath("testPath");
        updateDTO2.setUuid(UUID.randomUUID().toString());
        updateDTO2.setImgName("test");

        String content = "content";
        Post post = new Post(content, memberB.getNickname());

        post.setImageDTOList(new ArrayList<>());
        post.getImageDTOList().add(updateDTO1);
        post.getImageDTOList().add(updateDTO2);

        postService.register(memberB, post);
        //  when
        List<PostVO> myFollowingPosts = postService.getMyFollowingPosts(memberA);

        //  then
        assertThat(myFollowingPosts.get(0).getComments()).isEqualTo(new ArrayList<>());
        assertThat(myFollowingPosts.get(0).getHearts()).isEqualTo(new ArrayList<>());
        assertThat(myFollowingPosts.get(0).getUpdateDTOS()).isEqualTo(post.getImageDTOList());
        assertThat(myFollowingPosts.get(0).getContent()).isEqualTo(content);
    }

    @Test
    void getPost() {
        //  given
        Member member = memberRepository.findAll().get(0);
        UpdateDTO updateDTO1 = new UpdateDTO();
        updateDTO1.setPath("testPath");
        updateDTO1.setUuid(UUID.randomUUID().toString());
        updateDTO1.setImgName("test");

        UpdateDTO updateDTO2 = new UpdateDTO();
        updateDTO2.setPath("testPath");
        updateDTO2.setUuid(UUID.randomUUID().toString());
        updateDTO2.setImgName("test");

        String content = "content";
        Post post = new Post(content, member.getNickname());
        post.setImageDTOList(new ArrayList<>());
        post.getImageDTOList().add(updateDTO1);
        post.getImageDTOList().add(updateDTO2);
        postService.register(member, post);
        //  when
        PostVO postVO = postService.getPost(post.getId());

        //  then
        assertThat(postVO.getContent()).isEqualTo(content);
        assertThat(postVO.getNickname()).isEqualTo(member.getNickname());
        assertThat(postVO.getUpdateDTOS()).isEqualTo(post.getImageDTOList());
    }

    @Test
    void getPostForUpdate() {
        //  given
        Member member = memberRepository.findAll().get(0);
        UpdateDTO updateDTO1 = new UpdateDTO();
        updateDTO1.setPath("testPath");
        updateDTO1.setUuid(UUID.randomUUID().toString());
        updateDTO1.setImgName("test");

        UpdateDTO updateDTO2 = new UpdateDTO();
        updateDTO2.setPath("testPath");
        updateDTO2.setUuid(UUID.randomUUID().toString());
        updateDTO2.setImgName("test");

        String content = "content";
        Post post = new Post(content, member.getNickname());
        post.setImageDTOList(new ArrayList<>());
        post.getImageDTOList().add(updateDTO1);
        post.getImageDTOList().add(updateDTO2);
        postService.register(member, post);

        //  when
        postService.getPostForUpdate(post.getId());

        //  then
    }

    @Test
    void getPosts() {
        //  given
        Member member = memberRepository.findAll().get(0);
        UpdateDTO updateDTO1 = new UpdateDTO();
        updateDTO1.setPath("testPath");
        updateDTO1.setUuid(UUID.randomUUID().toString());
        updateDTO1.setImgName("test");

        UpdateDTO updateDTO2 = new UpdateDTO();
        updateDTO2.setPath("testPath");
        updateDTO2.setUuid(UUID.randomUUID().toString());
        updateDTO2.setImgName("test");

        String content = "content";
        Post post = new Post(content, member.getNickname());
        post.setImageDTOList(new ArrayList<>());
        post.getImageDTOList().add(updateDTO1);
        post.getImageDTOList().add(updateDTO2);
        postService.register(member, post);

        //  when
        List<PostVO> posts = postService.getPosts(member.getId());

        //  then
        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts.get(0).getUpdateDTOS()).isEqualTo(post.getImageDTOList());
    }

    @Test
    void update() {
    }

    @Test
    void remove() {
    }
}