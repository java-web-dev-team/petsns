package javawebdev.petsns.help;

import javawebdev.petsns.Validation;
import javawebdev.petsns.help.dto.Help;
import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class HelpService {

    private final HelpMapper helpMapper;
    private final Validation validation;

    //    회원용
    //    문의 작성
    public void create(Integer memberId, String content) throws Exception {
        Member member = validation.getMemberOrException(memberId);
        helpMapper.save(new Help(member.getId(), content));
    }

    //    내 문의 목록
    public List<Help> getMyHelps(Integer memberId) {
        Member member = validation.getMemberOrException(memberId);
        return helpMapper.findAllByMemberId(member.getId());
    }

    //    개별 문의 조회
    public Help getHelp(Integer memberId, Integer id) {
        Member member = validation.getMemberOrException(memberId);
        Help help = validation.getHelpOrException(id);

        return validation.isValidAccess(help, member);
    }

    public Help update(Integer memberId, Integer id, String updatedContent) throws Exception {
        Help help = validation.isValidAccess(validation.getHelpOrException(id), validation.getMemberOrException(memberId));

        help.setContent(updatedContent);
        helpMapper.update(help);

        return help;
    }

    public void delete(Integer memberId, Integer id) throws Exception {
        Help help = validation.isValidAccess(validation.getHelpOrException(id), validation.getMemberOrException(memberId));

        helpMapper.delete(help.getId());
    }

    //    회원용 --end

    //    관리자용
    //    모든 문의 보기
    public List<Help> getAll(Integer memberId) {
        validation.getAdminOrException(memberId);

        return helpMapper.findAll();
    }

    //    문의 확인
    public void check(Integer memberId, Integer id) {
        validation.getAdminOrException(memberId);
        helpMapper.checkHelp(id);
    }
}
