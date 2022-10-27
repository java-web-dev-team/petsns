package javawebdev.petsns.heart;

import javawebdev.petsns.heart.dto.Heart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class HeartService {

    private final HeartMapper heartMapper;

    public Integer save(Heart heart) {
        valiadateDuplicateHeart(heart);
        heartMapper.save(heart);
        return heart.getId();
    }

    private void valiadateDuplicateHeart(Heart heart) {
//        heartMapper.findByNickName(heart.getNickName())
//                .ifPresent(m -> {
//                    throw new IllegalStateException("이미 좋아요를 눌렀습니다.");
//                });
    }

    public void delete(Heart heart) {
        heartMapper.delete(heart);
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

