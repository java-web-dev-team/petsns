package javawebdev.petsns.heart;

import javawebdev.petsns.heart.dto.Heart;
import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.post.PostMapper;
import javawebdev.petsns.post.dto.Post;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class HeartServiceTest {

    @Autowired
    HeartService heartService;

    @Autowired
    HeartMapper heartMapper;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostMapper postMapper;

    @Test
    void 좋아요() {
        // given
        Member member = memberRepository.findAll().get(0);
        Post post = postMapper.findAll().get(0);

        // when
        heartService.save(new Heart(member.getNickname(), post.getId()));

        // then
        Heart heart = heartService.findByPostId(post.getId()).get(0);
        assertThat(heart.getNickName()).isEqualTo(member.getNickname());
        assertThat(heart.getPostId()).isEqualTo(post.getId());
    }

    @Test
    void 좋아요취소() {
        // given
        Member member = memberRepository.findAll().get(0);
        Post post = postMapper.findAll().get(0);
        heartService.save(new Heart(member.getNickname(), post.getId()));
        Heart heart = heartService.findByPostId(post.getId()).get(0);

        int beforeSize = heartMapper.findAll().size();


        // when
        heartService.delete(heart);

        // then
        int afterSize = heartMapper.findAll().size();
        assertThat(afterSize).isEqualTo(beforeSize - 1);
    }
}
