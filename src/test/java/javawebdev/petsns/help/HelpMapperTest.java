package javawebdev.petsns.help;

import javawebdev.petsns.help.dto.Help;
import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.dto.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class HelpMapperTest {

    @Autowired
    HelpMapper helpMapper;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void save() {
        //  given
        Member member = memberRepository.findAll().get(0);

        //  when
        String content = "help content";
        helpMapper.save(new Help(member.getId(), content));

        //  then
        Help help = helpMapper.findAllByMemberId(member.getId()).get(0);
        assertThat(help.getMemberId()).isEqualTo(member.getId());
        assertThat(help.getCheck()).isEqualTo(false);
        assertThat(help.getContent()).isEqualTo(content);
    }

    @Test
    void update() {
        //  given
        Member member = memberRepository.findAll().get(0);
        String content = "help content";
        helpMapper.save(new Help(member.getId(), content));
        Help help = helpMapper.findAll().get(0);

        //  when
        String updatedContent = "updatedContent";
        help.setContent(updatedContent);
        helpMapper.update(help);

        // then
        assertThat(helpMapper.findById(help.getId()).get().getContent()).isEqualTo(updatedContent);
    }

    @Test
    void delete() {
        //  given
        Member member = memberRepository.findAll().get(0);
        String content = "help content";
        helpMapper.save(new Help(member.getId(), content));
        int beforeSize = helpMapper.findAll().size();

        // when
        Help help = helpMapper.findAll().get(0);
        helpMapper.delete(help.getId());

        // then
        int afterSize = helpMapper.findAll().size();
        assertThat(afterSize).isEqualTo(beforeSize - 1);

    }

    @Test
    void checkHelp() {
        //  given
        Member member = memberRepository.findAll().get(0);
        String content = "help content";
        helpMapper.save(new Help(member.getId(), content));

        // when
        Help help = helpMapper.findAll().get(0);
        helpMapper.checkHelp(help.getId());

        // then
        assertThat(helpMapper.findById(help.getId()).get().getCheck()).isEqualTo(true);

    }
}