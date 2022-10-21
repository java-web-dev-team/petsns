package javawebdev.petsns.report;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReportController {

    @GetMapping("/report/write")
    public String reportwriteForm() {

        return "reportwrite";
    }
    @PostMapping("/report/writepro")
    public String reportwritePro(String title,String content){

        System.out.println("제목 : " + title);
        System.out.println("내용 :" + content);

        return "";
    }
}
