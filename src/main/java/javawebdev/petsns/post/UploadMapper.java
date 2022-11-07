package javawebdev.petsns.post;

import javawebdev.petsns.post.dto.UpdateDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UploadMapper {
    void insert(UpdateDTO dto);
    int delete (String uuid);
    List<UpdateDTO> findByPostId(Integer postId);

    void deleteAll(Integer postId);
}
