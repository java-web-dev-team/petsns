package javawebdev.petsns.report;

import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.report.dto.Report;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ReportMapper {

    //    회원용
    //    신고하기
    void save(Report report);

    //    내 신고목록
    List<Report> findAllByMember(Member member);

    //    개별 신고 조회(관리자 겸용)
    Optional<Report> findById(Integer id);

    //    신고 수정
    void update(Report updatedReport);

    //    신고 삭제
    void delete(Integer id);

    //    회원용 --end

    //    관리자용
    //    전체 신고 조회
    List<Report> findAll();

    //    신고 확인
    void checkReport(Integer id);
    //    관리자용 --end

    //    test
    void deleteAll();
}
