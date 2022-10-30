package javawebdev.petsns.block;

import javawebdev.petsns.block.dto.Block;
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
class BlockMapperTest {

    @Autowired
    BlockMapper blockMapper;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void block() {
        List<Member> members = memberRepository.findAll();
        Member blocker = members.get(0);
        Member blocked = members.get(1);

        Block block = new Block(blocker.getNickname(), blocked.getNickname());
        blockMapper.block(block);
        Block findBlock = blockMapper.findByBlock(block).get();

        assertThat(findBlock.getBlocker()).isEqualTo(blocker.getNickname());
        assertThat(findBlock.getBlocked()).isEqualTo(blocked.getNickname());
    }

    @Test
    void cancel() {
        List<Member> members = memberRepository.findAll();
        Member blocker = members.get(0);
        Member blocked = members.get(1);

        Block block = new Block(blocker.getNickname(), blocked.getNickname());
        blockMapper.block(block);
        int beforeSize = blockMapper.findAll().size();

        Block findBlock = blockMapper.findByBlock(block).get();
        blockMapper.cancel(findBlock);
        int afterSize = blockMapper.findAll().size();

        assertThat(afterSize).isEqualTo(beforeSize - 1);
    }
}