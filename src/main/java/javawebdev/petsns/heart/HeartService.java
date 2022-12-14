package javawebdev.petsns.heart;

import javawebdev.petsns.Validation;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.heart.dto.Heart;
import javawebdev.petsns.post.PostService;
import javawebdev.petsns.post.dto.Post;
import javawebdev.petsns.post.dto.PostVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Transactional
public class HeartService {

    private final HeartMapper heartMapper;
    private final Validation validation;
    private final PostService postService;

    public void delete(Integer postId, String nickName) throws Exception {
        Post post = validation.getPostOrException(postId);
        Member member = validation.getMemberOrException(nickName);
        Heart heart = validation.getHeartOrException(member.getNickname(), post.getId());
        heartMapper.delete(heart);
    }

    public Heart save(Integer postId, String nickName) throws Exception {
        Member member = validation.getMemberOrException(nickName);
        Post post = validation.getPostOrException(postId);
        validation.heartOrException(nickName, postId);

        Heart heart = new Heart(member.getNickname(), post.getId());
        heartMapper.save(heart);
        return heart;
    }

    public List<Heart> findByNickName(String nickName) {
        return heartMapper.findByNickName(nickName);
    }

    public Set<Member> findByNickNameM(String nickName) {
        return heartMapper.findByNickNameM(nickName);
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
            members.add(validation.getMemberOrException(heart.getNickname()));
        }
        return members;
    }

//    public boolean isInHeart(String principalName, Integer postId){
//        PostVO postVO = postService.getPost(postId);
//        for(int i=0; i<postVO.getHeartMembers().size(); i++){
//            if(postVO.getHeartMembers() == principalName){
//                return true;
//            }
//        }

//        return false;
//    }

}

