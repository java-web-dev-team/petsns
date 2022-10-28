package javawebdev.petsns;

import javawebdev.petsns.heart.HeartMapper;
import javawebdev.petsns.heart.dto.Heart;
import javawebdev.petsns.help.HelpMapper;
import javawebdev.petsns.help.dto.Help;
import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.post.PostMapper;
import javawebdev.petsns.post.dto.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ValidationTest {

    @Autowired
    Validation validation;

    @Autowired
    HeartMapper heartMapper;

    @Autowired
    PostMapper postMapper;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    HelpMapper helpMapper;

    @Test
    void getMemberOrException() {
        assertThrows(IllegalArgumentException.class, () -> {
            validation.getMemberOrException(400);
        });
    }

    @Test
    void getPostOrException() {
        assertThrows(IllegalArgumentException.class, () -> {
            validation.getPostOrException(400);
        });
    }

    @Test
    void getCommentOrException() {
        assertThrows(IllegalArgumentException.class, () -> {
            validation.getCommentOrException(400);
        });
    }

    @Test
    void isAlreadyHearted() {
        assertThat(validation.isNotExistentHeart("nonexistent nickname", 400))
                .isEqualTo(true);

        Member member = memberRepository.findAll().get(0);
        Post post = postMapper.findAll().get(0);

        heartMapper.save(new Heart(member.getNickname(), post.getId()));

        assertThat(validation.isNotExistentHeart(member.getNickname(), post.getId()))
                .isEqualTo(false);
    }

    @Test
    void isValidAccess() {
        Member validMember = memberRepository.findAll().get(0);
        Member notValidMember = memberRepository.findAll().get(1);

        Help help = new Help(validMember.getId(), "content");
        helpMapper.save(help);

        assertThrows(IllegalArgumentException.class, () -> {
            validation.isValidAccess(help, notValidMember);
        });

    }
}