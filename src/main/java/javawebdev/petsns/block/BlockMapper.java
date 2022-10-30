package javawebdev.petsns.block;

import javawebdev.petsns.block.dto.Block;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BlockMapper {

    //    차단하기
    void block(Block block);

    //    차단취소
    void cancel(Block block);

    //    차단 조회
    Optional<Block> findByBlock(Block block);

    //    내 차단 목록
    List<Block> findByBlocker(String nickname);

    //    모든 차단 조회
    List<Block> findAll();
}
