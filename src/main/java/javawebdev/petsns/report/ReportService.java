package javawebdev.petsns.report;

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
    private final MemberRepository memberRepository;

    //    회원용
    //    신고하기
    public void create(Member member, String reported, String content) throws Exception {
        getMemberOrException(member.getNickname());
        Report report = new Report(member.getNickname(), reported, content);
        reportMapper.save(report);
    }

    //    내 신고목록
    public List<Report> getReportsByMember(Member member) throws Exception {
        getMemberOrException(member.getNickname());
        return reportMapper.findAllByMember(member);
    }

    /*    개별 신고 조회(관리자 겸용)*/
    public Report getReportById(Integer reportId, Member member) throws Exception {
        getMemberOrException(member.getNickname());
        return getReportOrException(reportId);
    }

    //    신고 수정
    public Report update(Integer id, Member member, String reported, String content) throws Exception {
        Report report = getReportOrException(id);
        getMemberOrException(member.getNickname());
        if (Objects.equals(member.getNickname(), report.getReporter())) {
            report.setReported(reported);
            report.setContent(content);
            reportMapper.update(report);
            return report;
        } else {
            log.info("Member not authorized. nickname = {}", member.getNickname());
            throw new IllegalArgumentException();
        }
    }

    //    신고 삭제
    public void delete(Integer id, Member member) {
        Report report = getReportOrException(id);
        if (Objects.equals(member.getNickname(), report.getReporter())) {
            reportMapper.delete(id);
        } else {
            log.info("Member not authorized. nickname = {}", member.getNickname());
            throw new IllegalArgumentException();
        }
    }
    //    회원용 --end

    //    관리자용
    //    전체 신고 조회
    public List<Report> getAllReports() {
        return reportMapper.findAll();
    }

    //    신고 확인
    public void checkReport(Integer id) {
        getReportOrException(id);
        reportMapper.checkReport(id);
    }

    // TODO: validation

    private Report getReportOrException(Integer id) {
        return reportMapper.findById(id).orElseThrow(() ->{
            log.info("Report not found. reportId = {}", id);
            throw new IllegalArgumentException();
        });
    }

    private Member getMemberOrException(String nickname) throws Exception {
        return memberRepository.selectMemberByNickname(nickname).orElseThrow(() -> {
            log.info("Member not found. nickname = {}", nickname);
            throw new IllegalArgumentException();
        });
    }

    //  test
    public void deleteAll() {
        reportMapper.deleteAll();
    }
}
