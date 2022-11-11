package javawebdev.petsns.heart;

import javawebdev.petsns.heart.dto.Heart;
import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.post.PostMapper;
import javawebdev.petsns.post.dto.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class HeartMapperTest {

    @Autowired
    HeartMapper heartMapper;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostMapper postMapper;

    @Test
    void findByNickName() {

        heartMapper.findByNickName("cpiatek3");
        List<Heart> heart1 = heartMapper.findByNickName("cpiatek3");
        Heart findHeart = heart1.get(0);

        assertThat(findHeart.getNickname()).isEqualTo("cpiatek3");


    }

    @Test
    void delete() {
        String nickname = "kkk";
        Integer postId = 2;
        Heart heart = new Heart(nickname, postId);

        heartMapper.delete(heart);

    }

    @Test
    void save() {
        String nickname = "nickname";
        Integer postId = 1;
        Heart heart = new Heart(nickname, postId);

        heartMapper.save(heart);
        List<Heart> hearts = heartMapper.findAll();
        Heart findHeart = hearts.get(0);

        assertThat(findHeart.getNickname()).isEqualTo(nickname);
        assertThat(findHeart.getPostId()).isEqualTo(postId);
    }

    @Test
    void findByPostId() {

        List<Heart> hearts = heartMapper.findByPostId(82);

        assertThat(hearts.get(0).getNickname()).isEqualTo("dhkd");

    }

    @Test
    void findByNicknameAndPostId() {
        Member member = memberRepository.findAll().get(0);
        Post post = postMapper.findAll().get(0);

        heartMapper.save(new Heart(member.getNickname(), post.getId()));
        Heart heart = heartMapper.findByNicknameAndPostId(member.getNickname(), post.getId()).get();
        assertThat(heart.getNickname()).isEqualTo(member.getNickname());
        assertThat(heart.getPostId()).isEqualTo(post.getId());

    }
}