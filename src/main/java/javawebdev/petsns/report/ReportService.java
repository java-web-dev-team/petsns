package javawebdev.petsns.report;

import javawebdev.petsns.Validation;
import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.report.dto.Report;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportMapper reportMapper;
    private final Validation validation;

    //    회원용
    //    신고하기
    public void create(String reporter, String reported, String content) throws Exception {
        Member reporterMember = validation.getMemberOrException(reporter);
        Member reportedMember = validation.getMemberOrException(reported);
        Report report = new Report(reporterMember.getNickname(), reportedMember.getNickname(), content);
        reportMapper.save(report);
    }

    //    내 신고목록
    public List<Report> getReportsByMember(Integer memberId) throws Exception {
        Member member = validation.getMemberOrException(memberId);
        return reportMapper.findAllByMember(member);
    }

    /*    개별 신고 조회(관리자 겸용)*/
    public Report getReportById(Integer memberId, Integer reportId) throws Exception {
        validation.getMemberOrException(memberId);
        return validation.getReportOrException(reportId);
    }

    //    신고 수정
    public Report update(String reporter, String reported, Integer id, String updatedContent) throws Exception {
        Report report = validation.getReportOrException(id);
        Member reporterMember = validation.getMemberOrException(reporter);
        Member reportedMember = validation.getMemberOrException(reported);

        report.setReported(reportedMember.getNickname());
        report.setReporter(reporterMember.getNickname());
        report.setContent(updatedContent);

        reportMapper.update(report);

        return report;
    }

    //    신고 삭제
    public void delete(Integer id, String reporter) throws Exception {
        Member member = validation.getMemberOrException(reporter);
        Report report = validation.getReportOrException(id);

        reportMapper.delete(report.getId());
    }
    //    회원용 --end

    //    관리자용
    //    전체 신고 조회
    public List<Report> getAllReports(Integer memberId) throws Exception {
        validation.getAdminOrException(memberId);
        return reportMapper.findAll();
    }

    //    신고 확인
    public void checkReport(Integer memberId, Integer id) throws Exception {
        validation.getAdminOrException(memberId);
        validation.getReportOrException(id);
        reportMapper.checkReport(id);
    }
}
