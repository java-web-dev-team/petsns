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
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class HelpServiceTest {

    @Autowired
    HelpService helpService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void create() throws Exception {
        //  given
        Member member = memberRepository.findAll().get(0);
        String content = "content";

        //  when
        helpService.create(member.getId(), content);

        //  then
        assertThat(helpService.getMyHelps(member.getId()).size()).isEqualTo(1);
    }

    @Test
    void getMyHelps() throws Exception {
        //  given
        Member member = memberRepository.findAll().get(0);
        String content = "content";
        helpService.create(member.getId(), content);
        helpService.create(member.getId(), content);

        //  when
        List<Help> myHelps = helpService.getMyHelps(member.getId());

        //  then
        assertThat(myHelps.size()).isEqualTo(2);
    }

    @Test
    void getHelp() throws Exception {
        //  given
        Member member = memberRepository.findAll().get(0);
        String content = "content";
        helpService.create(member.getId(), content);

        //  when
        Help help = helpService.getMyHelps(member.getId()).get(0);

        //  then
        assertThat(help.getMemberId()).isEqualTo(member.getId());
        assertThat(help.getCheck()).isEqualTo(false);
        assertThat(help.getContent()).isEqualTo(content);
    }

    @Test
    void update() throws Exception {
        //  given
        Member member = memberRepository.findAll().get(0);
        String content = "content";
        helpService.create(member.getId(), content);
        Help help = helpService.getMyHelps(member.getId()).get(0);

        //  when
        String updatedContent = "updatedContent";
        Help updatedHelp = helpService.update(member.getId(), help.getId(), updatedContent);

        //  then
        assertThat(updatedHelp.getMemberId()).isEqualTo(member.getId());
        assertThat(updatedHelp.getCheck()).isEqualTo(false);
        assertThat(updatedHelp.getContent()).isEqualTo(updatedContent);
    }

    @Test
    void delete() throws Exception {
        //  given
        Member member = memberRepository.findAll().get(0);
        String content = "content";
        helpService.create(member.getId(), content);
        int beforeSize = helpService.getMyHelps(member.getId()).size();

        Help help = helpService.getMyHelps(member.getId()).get(0);

        //  when
        helpService.delete(member.getId(), help.getId());
        int afterSize = helpService.getMyHelps(member.getId()).size();

        //  then
        assertThat(afterSize).isEqualTo(beforeSize - 1);
    }

    @Test
    void getAll() throws Exception {
        //  given
        Member member = memberRepository.findAll().get(0);
        String content = "content";
        helpService.create(member.getId(), content);
        helpService.create(member.getId(), content);

        Member admin = memberRepository.findMemberByNickname("admin");

        //  when
        List<Help> allHelps = helpService.getAll(admin.getId());

        //  then
        assertThat(allHelps.size()).isEqualTo(2);
    }

    @Test
    void check() throws Exception {
        //  given
        Member member = memberRepository.findAll().get(0);
        String content = "content";
        helpService.create(member.getId(), content);

        Member admin = memberRepository.findMemberByNickname("admin");
        Help help = helpService.getAll(admin.getId()).get(0);

        //  when
        helpService.check(admin.getId(), help.getId());

        //  then
        assertThat(helpService.getHelp(member.getId(), help.getId()).getCheck()).isEqualTo(true);
    }
}