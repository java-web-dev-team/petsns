package javawebdev.petsns.follow;

import javawebdev.petsns.follow.dto.Follow;
import javawebdev.petsns.member.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class FollowMapperTest {

    @Autowired
    FollowMapper followMapper;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void findByFollowing() {

        followMapper.findByFollowing("abcdefg");
        List<Follow> follows = followMapper.findByFollowing("abcdefg");
        Follow findFollow = follows.get(0);

        assertThat(findFollow.getFollowing()).isEqualTo("abcdefg");
    }
}
