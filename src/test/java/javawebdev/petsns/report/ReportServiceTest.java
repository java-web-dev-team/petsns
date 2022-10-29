package javawebdev.petsns.report;

import javawebdev.petsns.member.MemberServiceImpl;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.report.dto.Report;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private MemberServiceImpl memberService;

    @Test
    void create() throws Exception {
        //  given
        Member member = new Member();
        member.setNickname("admin");

        //  when
        String reported = "reported";
        String content = "content";
        reportService.create(member, reported, content);

        //  then
        assertThat(reportService.getAllReports().size()).isEqualTo(1);
    }

    @Disabled
    @Test
    void getReportsByMember() throws Exception {
        //  given
        Member member = memberService.findByNickname("admin");
        String reported = "reported";
        String content = "content";
        reportService.create(member, reported, content);

        //  when
        List<Report> reports = reportService.getReportsByMember(member);

        //  then

    }

    @Test
    void getReportById() {
    }

    @Test
    void update() throws Exception {
        //  given
        Member member = new Member("nickname", "password", "introduce", "email@email", "MEMBER");
        member.setId(10000);
        String reported = "reported";
        String content = "content";
        reportService.create(member, reported, content);
        List<Report> reports = reportService.getAllReports();

        //  when
        Report report = reports.get(0);
        String modifiedReported = "modified reported";
        String modifiedContent = "modified content";
        reportService.update(report.getId(), member, modifiedReported, modifiedContent);

        //  then
        Report updatedReport = reportService.getAllReports().get(0);
        assertThat(updatedReport.getReported()).isEqualTo(modifiedReported);
        assertThat(updatedReport.getContent()).isEqualTo(modifiedContent);
    }

    @Test
    void delete() throws Exception {
        //  given
        Member member = new Member("nickname", "password", "introduce", "email@email", "MEMBER");
        member.setId(10000);
        String reported = "reported";
        String content = "content";
        reportService.create(member, reported, content);
        List<Report> reports = reportService.getAllReports();

        //  when


        //  then
    }

    @Test
    void checkReport() {
    }
}