package javawebdev.petsns.block;

import javawebdev.petsns.Validation;
import javawebdev.petsns.block.dto.Block;
import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlockService {

    private final BlockMapper blockMapper;
    private final Validation validation;

    public void block(String blocker, String blocked) throws Exception {
        Member blockerMember = validation.getMemberOrException(blocker);
        Member blockedMember = validation.getMemberOrException(blocked);
        Block block = new Block(blockerMember.getNickname(), blockedMember.getNickname());

        if (validation.isNotExistentBlock(block)) {
            blockMapper.block(block);
        }
    }

    public List<Block> getMyBlocks(String nickname) {
        Member member = validation.getMemberOrException(nickname);

        return blockMapper.findByBlocker(member.getNickname());
    }

    public void cancel(String blocker, String blocked) {
        Block block = validation.getBlockOrException(new Block(blocker, blocked));
        blockMapper.cancel(block);
    }
}
