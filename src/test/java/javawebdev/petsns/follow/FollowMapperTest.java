package javawebdev.petsns.follow;

import javawebdev.petsns.follow.dto.Follow;
import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.dto.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class FollowMapperTest {


    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private MemberRepository memberRepository;


    @Test
    void findByFollowing() {

        followMapper.findByFollowing("abcdefg");
        List<Follow> follow1 = followMapper.findByFollowing("abcdefg");
        if(follow1.size() !=0){
            Follow findFollow = follow1.get(0);
            assertThat(findFollow.getFollowing()).isEqualTo("abcdefg");
        }
    }

    @Test
    void findByFollower() {
        followMapper.findByFollower("abcdefg");
        List<Follow> follow2 = followMapper.findByFollower("abcdefg");
        if(follow2.size() !=0){
            Follow findFollow = follow2.get(0);
            assertThat(findFollow.getFollower()).isEqualTo("abcdefg");
        }
    }

    @Test
    void save() {
        String following = "dhkd";
        String follower = "eofbs";
        Follow follow = new Follow(following, follower);

        followMapper.save(follow);
        List<Follow> follows = followMapper.findAll();
        Follow findFollow = follows.get(0);

        assertThat(findFollow.getFollowing()).isEqualTo(following);
        assertThat(findFollow.getFollower()).isEqualTo(follower);
    }

    @Test
    void findAll() {
        followMapper.findAll();
        List<Follow> followList = followMapper.findAll();
        if(followList.size() !=0){
            Follow findFollow = followList.get(0);
            System.out.println(findFollow);
        }
    }

    @Test
    void delete() {
        String following = "asdfg";
        String follower = "lkj";
        Follow follow = new Follow(following, follower);

        followMapper.delete(follow);
    }

    @Test
    void findByFollowingAndFollower() {
        Member following = memberRepository.findAll().get(0);
        Member follower = memberRepository.findAll().get(0);

        followMapper.save(new Follow(following.getNickname(), follower.getNickname()));
        Follow follow = followMapper.findByFollowingAndFollower(following.getNickname(), follower.getNickname()).get();
        assertThat(follow.getFollowing()).isEqualTo(following.getNickname());
        assertThat(follow.getFollower()).isEqualTo(follower.getNickname());
    }
}
