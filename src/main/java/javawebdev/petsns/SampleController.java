package javawebdev.petsns;

import javawebdev.petsns.aws.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class SampleController {

    private final AwsS3Service awsS3Service;

    @GetMapping("/api/sample")
    public String sample() {
        return "OK";
    }

    @PostMapping("/api/upload")
    @ResponseBody
    public String upload(@RequestParam("data")MultipartFile file) throws IOException{
        return awsS3Service.upload(file, "profile-image");
    }
}
