package javawebdev.petsns.block;

import javawebdev.petsns.block.dto.Block;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlockMapper {

    //    차단하기
    void block(Block block);

    //    차단취소
    void cancel(Block block);

    //    모든 차단 조회
    List<Block> findAll();
}
