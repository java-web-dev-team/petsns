package javawebdev.petsns.block;

import groovy.util.logging.Slf4j;
import javawebdev.petsns.block.dto.Block;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
class BlockMapperTest {

    @Autowired
    BlockMapper blockMapper;

    @Test
    void block() {
        String blocker = "blocker";
        String blocked = "blocked";
        Block block = new Block(blocker, blocked);
        blockMapper.block(block);
        assertThat(blockMapper.findAll().size()).isEqualTo(1);
        assertThat(blockMapper.findAll().get(0).getBlocked()).isEqualTo(blocked);
        assertThat(blockMapper.findAll().get(0).getBlocker()).isEqualTo(blocker);
    }

    @Test
    void cancel() {
        String blocker = "blocker";
        String blocked = "blocked";
        Block block = new Block(blocker, blocked);
        blockMapper.block(block);

        blockMapper.cancel(block);

        Block findBlock = blockMapper.findAll().get(0);
        System.out.println(findBlock.getCanceledAt());
        assertThat(findBlock.getCanceledAt()).isNotNull();
    }
}