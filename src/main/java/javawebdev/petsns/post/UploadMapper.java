package javawebdev.petsns.post;

import javawebdev.petsns.post.dto.UpdateDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UploadMapper {
    public void insert(UpdateDTO dto);
    public int delete (String uuid);
    List<UpdateDTO> findBypid(Integer id);
    public void deleteAll(Integer id);
    public List<UpdateDTO>getOldFiles();
}
