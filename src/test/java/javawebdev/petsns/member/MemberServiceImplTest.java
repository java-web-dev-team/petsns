package javawebdev.petsns.member;

import javawebdev.petsns.member.dto.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceImplTest {

    @Autowired
    MemberServiceImpl memberService;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void beforeEach() throws Exception{
        memberRepository.deleteAll();
    }

    @Test
    void joinTest() throws Exception {
        System.out.println("memberService = " + memberService);
        Member member = new Member(1, "asdf", "1234", "hi", "aaa@aaa.com");
        memberService.joinMember(member);
        Assertions.assertTrue(memberRepository.count() == 1);
    }

    @Test
    void updateTest() throws Exception{
        Member member = new Member(1, "asdf", "1234", "hi", "aaa@aaa.com");
        memberService.joinMember(member);

        Member member2 = new Member(1, "asdf", "3456", "hello", "bbb@bbb.com");
        memberService.updateMember(member2);

        Assertions.assertTrue(memberRepository.count() == 1);
    }

    @Test
    void deleteTest() throws Exception{
        Member member = new Member(1, "asdf", "1234", "hi", "aaa@aaa.com");
        memberService.joinMember(member);
        Assertions.assertTrue(memberRepository.count() == 1);
        memberService.deleteMember("asdf");
        Assertions.assertTrue(memberRepository.count() == 0);
    }


}