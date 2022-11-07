package javawebdev.petsns.post;

import javawebdev.petsns.follow.FollowMapper;
import javawebdev.petsns.follow.dto.Follow;
import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.post.dto.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class PostMapperTest {

    @Autowired
    PostMapper postMapper;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    FollowMapper followMapper;

    @DisplayName("게시글 저장 및 인스턴스에 setId")
    @Test
    void saveAndSetId() {
        //  given
        Member member = memberRepository.findAll().get(0);
        String content = "content";
        String nickname = member.getNickname();
        Post post = new Post(content, nickname);

        //  when
        postMapper.saveAndSetId(post);
        List<Post> posts = postMapper.findAll();
        Post lastPost = posts.get(posts.size() - 1);

        //  then
        assertThat(post.getId()).isEqualTo(lastPost.getId());
        assertThat(post.getContent()).isEqualTo(lastPost.getContent());
        assertThat(post.getNickname()).isEqualTo(lastPost.getNickname());
    }

    @DisplayName("내가 팔로우한 사람의 게시글 가져오기")
    @Test
    void findAllOfFollowingMember() {
        //  given(memberA가 memberB와 memberC를 팔로우하고 각각 게시물 2개씩 작성)
        List<Post> all = postMapper.findAll();
        for (Post post : all) {
            postMapper.delete(post);
        }
        Member memberA = memberRepository.findAll().get(0);
        Member memberB = memberRepository.findAll().get(1);
        Member memberC = memberRepository.findAll().get(2);

        followMapper.save(new Follow(memberB.getNickname(), memberA.getNickname()));
        followMapper.save(new Follow(memberC.getNickname(), memberA.getNickname()));

        String content = "content";
        postMapper.saveAndSetId(new Post(content, memberB.getNickname()));
        postMapper.saveAndSetId(new Post(content, memberB.getNickname()));
        postMapper.saveAndSetId(new Post(content, memberC.getNickname()));
        postMapper.saveAndSetId(new Post(content, memberC.getNickname()));

        //  when
        List<Post> posts = postMapper.findAllOfFollowingMember(memberA);

        //  then
        assertThat(posts.size()).isEqualTo(4);
    }

    @Test
    void update() {
        //  given
        Post post = postMapper.findAll().get(0);

        //  when
        String updatedContent = "updatedContent";
        post.setContent(updatedContent);
        postMapper.update(post);

        //  then
        assertThat(postMapper.findById(post.getId()).get().getContent()).isEqualTo(updatedContent);
    }
}