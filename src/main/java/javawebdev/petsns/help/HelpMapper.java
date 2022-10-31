package javawebdev.petsns.help;

import javawebdev.petsns.help.dto.Help;
import javawebdev.petsns.member.dto.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface HelpMapper {

    //    회원용
    //    문의하기
    void save(Help help);

    //    내 문의 목록
    List<Help> findAllByMemberId(Integer memberId);

    //    개별 문의 조회(관리자 겸용)
    Optional<Help> findById(Integer id);

    //    문의 수정
    void update(Help updatedHelp);

    //    문의 삭제
    void delete(Integer id);

    //    회원용 --end

    //    관리자용
    //    전체 문의 조회
    List<Help> findAll();

    //    문의 확인
    void checkHelp(Integer id);
    //    관리자용 --end

    //    test
    void deleteAll();
}
