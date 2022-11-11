package javawebdev.petsns.post;

import javawebdev.petsns.Validation;
import javawebdev.petsns.comment.CommentMapper;
import javawebdev.petsns.heart.HeartMapper;
import javawebdev.petsns.member.dto.Member;
import javawebdev.petsns.post.dto.Post;
import javawebdev.petsns.post.dto.PostVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostMapper postMapper;
    private final UploadMapper uploadMapper;
    private final CommentMapper commentMapper;
    private final HeartMapper heartMapper;
    private final Validation validation;

    // 내가 팔로우한 사람의 게시물 목록
    public List<PostVO> getMyFollowingPosts(Member member) {
        List<PostVO> myFollowingPosts = new ArrayList<>();
        List<Post> posts = postMapper.findAllOfFollowingMember(member);
        for (Post post : posts) {
            myFollowingPosts.add(postToVO(post));
        }
        return myFollowingPosts;
    }

    // 내 게시글
    public List<PostVO> getMyPosts(String nickname){
        List<PostVO> myPosts = new ArrayList<>();
        List<Post> posts = postMapper.findByNickname(nickname);
        for(Post post : posts){
            myPosts.add(postToVO(post));
        }
        return myPosts;
    }

    // 게시물 작성
    public void register(Member member, Post post) {
        post.setNickname(member.getNickname());
        postMapper.saveAndSetId(post);

        if(post.getImageDTOList() != null) {
            post.getImageDTOList().forEach(image -> {
                image.setPostId(post.getId());
                uploadMapper.insert(image);
            });
        }
    }

    //  개별 게시글 조회
    public PostVO getPost(Integer id) {
        Post postWithUpdateDTOs = postWithUpdateDTOs(id);
        return postToVO(postWithUpdateDTOs);
    }

    //  프로필에서 member 가 작성한 post 불러오기
    public List<PostVO> getPosts(Integer memberId) {
        List<PostVO> postVOS = new ArrayList<>();
        Member member = validation.getMemberOrException(memberId);
        List<Post> posts = postMapper.findByNickname(member.getNickname());
        for (Post post : posts) {
            postVOS.add(postToVO(postWithUpdateDTOs(post.getId())));
        }
        return postVOS;
    }

    //  update 용 post 추출
    public Post getPostForUpdate(Integer id) {
        return postWithUpdateDTOs(id);
    }

    //  게시글 수정
    public void update(Member member, Integer postId, String updatedContent) {
        Post post = validation.getPostOrException(postId);
        validation.isValidAccess(post, member);
        post.setContent(updatedContent);
        postMapper.update(post);
    }

    //  게시글 삭제
    public void remove(Member member, Integer postId) {
        Post post = validation.getPostOrException(postId);
        validation.isValidAccess(post, member);
        postMapper.delete(post);
        uploadMapper.deleteAll(post.getId());
    }


    //  util method
    private Post postWithUpdateDTOs(Integer id) {
        Post post = validation.getPostOrException(id);
        post.setImageDTOList(uploadMapper.findByPostId(id));
        return post;
    }

    private PostVO postToVO(Post post) {
        validation.getPostOrException(post.getId());
        Post postWithUpdateDTOs = postWithUpdateDTOs(post.getId());
        int id = postWithUpdateDTOs.getId();

        return PostVO.fromDTO(
                postWithUpdateDTOs,
                commentMapper.findByPostId(id),
                heartMapper.findByPostId(id),
                heartMapper.findMembersByPostId(id)
        );
    }

}
