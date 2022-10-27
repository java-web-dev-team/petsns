package javawebdev.petsns.heart;

import javawebdev.petsns.heart.dto.Heart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class HeartMapperTest {

    @Autowired
    HeartMapper heartMapper;

    @Test
    void findByNickName() {

        heartMapper.findByNickName("cpiatek3");
        List<Heart> heart1 = heartMapper.findByNickName("cpiatek3");
        Heart findHeart = heart1.get(0);

        assertThat(findHeart.getNickName()).isEqualTo("cpiatek3");


    }

    @Test
    void delete() {
        String nickname = "kkk";
        Integer postId = 2;
        Heart heart = new Heart(nickname, postId);

        heartMapper.delete(heart);

    }

    @Test
    void save() {
        String nickname = "nickname";
        Integer postId = 1;
        Heart heart = new Heart(nickname, postId);

        heartMapper.save(heart);
        List<Heart> hearts = heartMapper.findAll();
        Heart findHeart = hearts.get(0);

        assertThat(findHeart.getNickName()).isEqualTo(nickname);
        assertThat(findHeart.getPostId()).isEqualTo(postId);
    }

    @Test
    void findByPostId() {

        heartMapper.findByPostId(50);
        List<Heart> heart3 = heartMapper.findByPostId(50);
        Heart findHearts = heart3.get(0);

        assertThat(findHearts.getPostId()).isEqualTo(50);
    }
}