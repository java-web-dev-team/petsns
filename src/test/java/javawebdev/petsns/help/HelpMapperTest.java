package javawebdev.petsns.help;

import javawebdev.petsns.help.dto.Help;
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

    @Test
    void save() {
        //  given
        Help help = new Help("nickname", "content");

        //  when
        helpMapper.save(help);

        //  then
        Help help1 = helpMapper.findAll().get(0);
        assertThat(helpMapper.findAll().size()).isEqualTo(1);
    }

    @Test
    void findAllByMember() {
        //  given
        String nickname = "nickname";
        Member member = new Member();
        member.setNickname(nickname);

        String content = "content";
        Help help = new Help(nickname, content);
        helpMapper.save(help);

        //  when
        List<Help> helps = helpMapper.findAllByMember(member);

        //  then
        assertThat(helps.size()).isEqualTo(1);
        assertThat(helps.get(0).getNickname()).isEqualTo(nickname);
        assertThat(helps.get(0).getContent()).isEqualTo(content);
    }

    @Test
    void findById() {
        //  given
        String nickname = "nickname";
        Member member = new Member();
        member.setNickname(nickname);

        String content = "content";
        Help help = new Help(nickname, content);
        helpMapper.save(help);

        //  when
        List<Help> helps = helpMapper.findAllByMember(member);
        Help findHelp = helpMapper.findById(helps.get(0).getId()).get();

        // then
        assertThat(findHelp.getNickname()).isEqualTo(nickname);
        assertThat(findHelp.getContent()).isEqualTo(content);
    }

    @Test
    void update() {
        //  given
        String nickname = "nickname";
        Member member = new Member();
        member.setNickname(nickname);

        String content = "content";
        Help help = new Help(nickname, content);
        helpMapper.save(help);

        //  when
        List<Help> helps = helpMapper.findAllByMember(member);
        Help findHelp = helpMapper.findById(helps.get(0).getId()).get();
        String updatedContent = "updatedContent";
        findHelp.setContent(updatedContent);
        helpMapper.update(findHelp);

        // then
        assertThat(helpMapper.findById(findHelp.getId()).get().getContent()).isEqualTo(updatedContent);
    }

    @Test
    void delete() {
        //  given
        String nickname = "nickname";
        Member member = new Member();
        member.setNickname(nickname);

        String content = "content";
        Help help = new Help(nickname, content);
        helpMapper.save(help);

        // when
        Help findHelp = helpMapper.findAll().get(0);
        helpMapper.delete(findHelp.getId());

        // then
        assertThat(helpMapper.findAll().size()).isEqualTo(0);

    }

    @Test
    void checkHelp() {
        //  given
        String nickname = "nickname";
        Member member = new Member();
        member.setNickname(nickname);

        String content = "content";
        Help help = new Help(nickname, content);
        helpMapper.save(help);

        // when
        Help findHelp = helpMapper.findAll().get(0);
        helpMapper.checkHelp(findHelp.getId());

        // then
        assertThat(helpMapper.findById(findHelp.getId()).get().getCheck()).isEqualTo(true);

    }
}