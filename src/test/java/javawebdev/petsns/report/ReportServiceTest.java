package javawebdev.petsns.report;

import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.report.dto.Report;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class ReportServiceTest {

    @Autowired
    ReportService reportService;

    @Autowired
    MemberRepository memberRepository;


    // Mock data


    void createMockReport() throws Exception {
        Member admin = memberRepository.selectMember("admin");
        Member reporter = memberRepository.findAll().get(0);
        Member reported = memberRepository.findAll().get(1);
        String content = "content";
        reportService.create(reporter.getNickname(), reported.getNickname(), content);
    }

    @Test
    void create() throws Exception {
        //  given
        Member admin = memberRepository.selectMember("admin");

        //  when
        createMockReport();

        //  then
        assertThat(reportService.getAllReports(admin.getId()).size()).isEqualTo(1);
    }

    @Test
    void getReportsByMember() throws Exception {
        //  given
        Member reporter = memberRepository.findAll().get(0);
        createMockReport();

        //  when
        List<Report> reports = reportService.getReportsByMember(reporter.getId());

        //  then
        assertThat(reports.size()).isEqualTo(1);
    }

    @Test
    void getReportById() throws Exception {
        //  given
        Member admin = memberRepository.selectMember("admin");
        Member reporter = memberRepository.findAll().get(0);
        Member reported = memberRepository.findAll().get(1);
        String content = "content";
        createMockReport();

        //  when
        Report report = reportService.getReportById(reporter.getId(), reportService.getAllReports(admin.getId()).get(0).getId());

        //  then
        assertThat(report.getReporter()).isEqualTo(reporter.getNickname());
        assertThat(report.getReported()).isEqualTo(reported.getNickname());
        assertThat(report.getContent()).isEqualTo(content);
    }

    @Test
    void update() throws Exception {
        //  given
        Member admin = memberRepository.selectMember("admin");
        Member reporter = memberRepository.findAll().get(0);
        Member reported = memberRepository.findAll().get(1);
        createMockReport();
        Report report = reportService.getAllReports(admin.getId()).get(0);

        //  when
        String updatedContent = "updatedContent";
        reportService.update(reporter.getNickname(), reported.getNickname(), report.getId(), updatedContent);

        //  then
        Report updatedReport = reportService.getReportById(reporter.getId(), report.getId());
        assertThat(updatedReport.getReporter()).isEqualTo(reporter.getNickname());
        assertThat(updatedReport.getReported()).isEqualTo(reported.getNickname());
        assertThat(updatedReport.getContent()).isEqualTo(updatedContent);
    }

    @Test
    void delete() throws Exception {
        //  given
        Member admin = memberRepository.selectMember("admin");
        Member reporter = memberRepository.findAll().get(0);
        createMockReport();

        List<Report> reports = reportService.getAllReports(admin.getId());
        Report report = reports.get(0);
        int beforeSize = reports.size();

        //  when
        reportService.delete(report.getId(), reporter.getNickname());
        int afterSize = reportService.getAllReports(admin.getId()).size();

        //  then
        assertThat(afterSize).isEqualTo(beforeSize - 1);
    }

    @Test
    void checkReport() throws Exception {
        //  given
        Member admin = memberRepository.selectMember("admin");
        Member reporter = memberRepository.findAll().get(0);
        createMockReport();

        Report report = reportService.getAllReports(admin.getId()).get(0);

        //  when
        reportService.checkReport(admin.getId(), report.getId());

        //  then
        Report checkedReport = reportService.getReportById(reporter.getId(), report.getId());
        assertThat(checkedReport.getCheck()).isEqualTo(true);

    }
}