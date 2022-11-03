package javawebdev.petsns;

import javawebdev.petsns.heart.HeartMapper;
import javawebdev.petsns.help.HelpMapper;
import javawebdev.petsns.help.dto.Help;
import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.post.PostMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

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