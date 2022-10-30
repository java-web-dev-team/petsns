package javawebdev.petsns.admin;

import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.report.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final ReportService reportService;

    @GetMapping("/reports")
    public String getReports(@AuthenticationPrincipal Member member, Model model) throws Exception {
        model.addAttribute("reports", reportService.getAllReports(member.getId()));
        return "admin/reports";
    }

    @GetMapping("/admin/{reportId}")
    public String getReport(@PathVariable Integer reportId, @AuthenticationPrincipal Member member, Model model) throws Exception {
        model.addAttribute(reportService.getReportById(member.getId(), reportId));
        return "admin/reports/report-detail";
    }

    @PutMapping("/admin/reports/{reportId}")
    public String checkReport(@PathVariable Integer reportId, @AuthenticationPrincipal Member member) throws Exception {
        reportService.checkReport(member.getId(), reportId);
        return "redirect:/admin/reports/{reportId}";
    }
}
