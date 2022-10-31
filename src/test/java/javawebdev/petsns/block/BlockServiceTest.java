package javawebdev.petsns.block;

import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.dto.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class BlockServiceTest {

    @Autowired
    BlockService blockService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void block() throws Exception {
        Member blocker = memberRepository.findAll().get(0);
        Member blocked = memberRepository.findAll().get(1);

        blockService.block(blocker.getNickname(), blocked.getNickname());

        assertThat(blockService.getMyBlocks(blocker.getNickname()).size()).isEqualTo(1);
    }

    @Test
    void cancel() throws Exception {
        Member blocker = memberRepository.findAll().get(0);
        Member blocked = memberRepository.findAll().get(1);

        blockService.block(blocker.getNickname(), blocked.getNickname());
        int beforeSize = blockService.getMyBlocks(blocker.getNickname()).size();

        blockService.cancel(blocker.getNickname(), blocked.getNickname());
        int afterSize = blockService.getMyBlocks(blocker.getNickname()).size();

        assertThat(afterSize).isEqualTo(beforeSize - 1);
    }
}