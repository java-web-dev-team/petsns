package javawebdev.petsns.member;

import javawebdev.petsns.member.dto.Member;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository{

    private final SqlSession sqlSession;

    private static String namespace = "javawebdev.petsns.member.dto.Member.";

    @Override
    public Member selectMember(String nickname) throws Exception {
        return sqlSession.selectOne(namespace + "selectMember", nickname);
    }

    @Override
    public String findMemberByNickname(String nickname) throws Exception {
        return sqlSession.selectOne(namespace + "findMemberByNickname", nickname);
    }

    @Override
    public Member findMemberByEmail(String email) throws Exception {
        return sqlSession.selectOne(namespace + "findMemberByEmail", email);
    }

    @Override
    public int insert(Member member) throws Exception {
        return sqlSession.insert(namespace + "insertMember", member);
    }

    @Override
    public int update(Member member) throws Exception {
        return sqlSession.update(namespace + "updateMember", member);
    }

    @Override
    public int delete(String nickname) throws Exception {
        return sqlSession.delete(namespace + "deleteMember", nickname);
    }

    @Override
    public int count() throws Exception {
        return sqlSession.selectOne(namespace + "count");
    }

    @Override
    public void deleteAll() throws Exception {
        sqlSession.delete(namespace + "deleteAll");
    }

    @Override
    public Optional<Member> selectById(Integer id) throws Exception {
        return sqlSession.selectOne(namespace + "selectMember", id);
    }


}
