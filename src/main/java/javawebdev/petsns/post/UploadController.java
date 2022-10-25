package javawebdev.petsns.post;

import javawebdev.petsns.post.dto.UploadForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class UploadController {
    private static final String UPLOAD_PATH = "/Users/kimgeonwoo/Desktop/upload";



    @PostMapping("/uploadForm")
    public void uplpadFormAction(UploadForm form, Model model){

    }
}
