package javawebdev.petsns.heart;

import javawebdev.petsns.Validation;
import javawebdev.petsns.member.MemberRepository;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.heart.dto.Heart;
import javawebdev.petsns.post.dto.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class HeartService {

    private final HeartMapper heartMapper;

    private final MemberRepository memberRepository;
    private final Validation validation;

    public Heart save(Integer postId, String nickName) throws Exception {
        Post post = validation.getPostOrException(postId);
        Member member = validation.getMemberOrException(nickName);
        Heart heart = new Heart(member.getNickname(), post.getId());
        heartMapper.save(heart);
        return heart;
    }

    public void delete(Integer postId, String nickName) throws Exception {
        Member member = validation.getMemberOrException(nickName);
        Post post = validation.getPostOrException(postId);
        if (validation.isNotExistentHeart(member.getNickname(), post.getId())) {
            throw new IllegalArgumentException();
        } else {
            delete(postId, nickName);
        }

    }

    public List<Heart> findByNickName(String nickName) {

        return heartMapper.findByNickName(nickName);
    }

    public List<Member> getMembersByPostId(Integer postId) {
        List<Heart> hearts = heartMapper.findByPostId(postId);
        return heartsToMembers(hearts);
    }

    public List<Heart> findOne(Integer postId) {

        return heartMapper.findByPostId(postId);
    }

    private List<Member> heartsToMembers(List<Heart> hearts) {
        List<Member> members = new ArrayList<>();
        for (Heart heart : hearts) {
            members.add(validation.getMemberOrException(heart.getNickName()));
        }
        return members;
    }

}

