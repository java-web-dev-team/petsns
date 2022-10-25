package javawebdev.petsns.report;

import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.report.dto.Report;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ReportMapperTest {

    @Autowired
    ReportMapper reportMapper;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void findById() {
        //  given
        Member member = new Member();
        member.setNickname("nickname");

        String reported = "reported";
        String content = "content";
        Report report = new Report(member.getNickname(), reported, content);
        reportMapper.save(report);

        //  when
        List<Report> reports = reportMapper.findAllByMember(member);
        Report findReport = reportMapper.findById(reports.get(0).getId()).get();

        //  then
        assertThat(findReport.getReporter()).isEqualTo(report.getReporter());
        assertThat(findReport.getReported()).isEqualTo(report.getReported());
        assertThat(findReport.getContent()).isEqualTo(report.getContent());
    }

    @Test
    void update() {
        //  given
        Member member = new Member();
        member.setNickname("nickname");

        String reported = "reported";
        String content = "content";
        Report report = new Report(member.getNickname(), reported, content);
        reportMapper.save(report);

        //  when
        List<Report> reports = reportMapper.findAllByMember(member);
        Report findReport = reportMapper.findById(reports.get(0).getId()).get();
        String updatedReported = "updatedReported";
        String updatedContent = "updatedContent";
        findReport.setReported(updatedReported);
        findReport.setContent(updatedContent);
        reportMapper.update(findReport);

        // then
        assertThat(reportMapper.findById(findReport.getId()).get().getReported()).isEqualTo(updatedReported);
        assertThat(reportMapper.findById(findReport.getId()).get().getContent()).isEqualTo(updatedContent);
    }

    @Test
    void delete() {
        //  given
        String reporter = "reporter";
        String reported = "reported";
        String content = "content";
        Report report = new Report(reporter, reported, content);
        reportMapper.save(report);
        assertThat(reportMapper.findAll().size()).isEqualTo(1);

        Integer id = reportMapper.findAll().get(0).getId();

        //  when
        reportMapper.delete(id);

        //  then
        assertThat(reportMapper.findAll().size()).isEqualTo(0);
    }

    @Test
    void checkReport() {
        //  given
        String reporter = "reporter";
        String reported = "reported";
        String content = "content";
        Report report = new Report(reporter, reported, content);
        reportMapper.save(report);
        Integer id = reportMapper.findAll().get(0).getId();

        //  when
        reportMapper.checkReport(id);

        //  then
        assertThat(reportMapper.findAll().get(0).isCheck()).isEqualTo(true);

    }
}