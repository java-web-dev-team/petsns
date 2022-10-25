package javawebdev.petsns.post.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadForm {
    String desc;
    MultipartFile[] uploadfile;
}
