package javawebdev.petsns.report;

import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.report.dto.Report;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/reports")
@RequiredArgsConstructor
@Controller
public class ReportController {

    private final ReportService reportService;

    // 신고하기
    @PostMapping("/")
    public String create(@AuthenticationPrincipal Member member, String reported, String content) throws Exception {
        reportService.create(member.getNickname(), reported, content);
        return "redirect:/reports";
    }

    // 신고 작성 폼 가져오기
    @GetMapping("/form")
    public String form(@AuthenticationPrincipal Member member, Model model) {
        model.addAttribute("reporter", member.getNickname());
        return "report-form";
    }

    // 내 신고목록
    @GetMapping("/")
    public String getMy(@AuthenticationPrincipal Member member, Model model) throws Exception {
        List<Report> reports = reportService.getReportsByMember(member.getId());
        model.addAttribute("reports", reports);
        return "reports";
    }

    // 개별 신고조회
    @GetMapping("/{reportId}")
    public String getReport(@PathVariable Integer reportId, @AuthenticationPrincipal Member member, Model model) throws Exception {
        Report report = reportService.getReportById(member.getId(), reportId);
        model.addAttribute("report", report);
        return "report-detail";
    }

    // report 수정
    @PutMapping("/{reportId}")
    public String update(@PathVariable Integer reportId, @AuthenticationPrincipal Member member, String updatedContent, String updatedReported) throws Exception {
        reportService.update(member.getNickname(), updatedReported, reportId, updatedContent);
        return "redirect:/reports/{reportId}";
    }

    // report 삭제
    @DeleteMapping("/{reportId}")
    public String delete(@PathVariable Integer reportId, @AuthenticationPrincipal Member member) throws Exception {
        reportService.delete(reportId, member.getNickname());
        return "redirect:/reports/{memberId}";
    }
}
