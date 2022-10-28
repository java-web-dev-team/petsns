package javawebdev.petsns.heart;

import javawebdev.petsns.Validation;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.heart.dto.Heart;
import javawebdev.petsns.post.dto.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class HeartService {

    private final HeartMapper heartMapper;

    private final Validation validation;

    public Heart save(Integer postId, Integer memberId) throws Exception {
        Post post = validation.getPostOrException(postId);
        Member member = validation.getMemberOrException(memberId);
        Heart heart = new Heart(member.getNickname(), post.getId());
        heartMapper.save(heart);
        return heart;
    }

    public void delete(Integer memberId, Integer postId) throws Exception {
        Member member = validation.getMemberOrException(memberId);
        Post post = validation.getPostOrException(postId);
        if (validation.isNotExistentHeart(member.getNickname(), post.getId())) {
            throw new IllegalArgumentException();
        } else {
            delete(memberId, postId);
        }

    }

    public List<Heart> findByNickName(String nickName) {

        return heartMapper.findByNickName(nickName);
    }

    public List<Heart> findByPostId(Integer postId) {

        return heartMapper.findByPostId(postId);
    }

    public List<Heart> findOne(Integer postId) {

        return heartMapper.findByPostId(postId);
    }

}

