package ru.khamedov.ildar.socialMedia.service;

import jakarta.annotation.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.khamedov.ildar.socialMedia.dto.ImageFileDTO;
import ru.khamedov.ildar.socialMedia.dto.PostDTO;
import ru.khamedov.ildar.socialMedia.model.UserProfile;
import ru.khamedov.ildar.socialMedia.model.post.ImageFile;
import ru.khamedov.ildar.socialMedia.model.post.Post;
import ru.khamedov.ildar.socialMedia.repository.PostRepository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class PostService {

    @Resource
    private ModelMapperService modelMapperService;

    @Resource
    private PostRepository postRepository;

    @Resource
    private AuthService authService;


    public Post createPost(PostDTO postDTO){
        Post post=modelMapperService.converterToPost(postDTO);
        post.setAuthor(authService.getUserProfile());
        post.getImageFileList().stream().forEach(i -> i.setPost(post));
        postRepository.save(post);
        return post;
    }

    public boolean addImage(Long postId, List<ImageFileDTO> imageFileDTOList){
        Post post=postRepository.findById(postId).get();
        if(post == null || imageFileDTOList.isEmpty()){
            return false;
        }
        List<ImageFile> imageFileList=imageFileDTOList.stream().map(i -> modelMapperService.converterToImageFile(i)).collect(Collectors.toList());
        imageFileList.stream().forEach(i->i.setPost(post));
        post.getImageFileList().addAll(imageFileList);
        postRepository.save(post);
        return true;
    }

    public boolean deletePost(Long postId){
        if(postRepository.findById(postId).isEmpty()){
            return false;
        }
        postRepository.deleteById(postId);
        return true;
    }

    public boolean updatePostById(Long postId,String text){
        if(postRepository.findById(postId).isEmpty()){
            return false;
        }
        Post post=postRepository.findById(postId).get();
        post.setText(text);
        postRepository.save(post);
        return true;
    }

    public List<PostDTO> getPostByFilterList(UserProfile user,Instant instant, int page, int limit){
        List<Post> postList = postRepository.findPostByInstant(user,instant,PageRequest.of(page, limit, Sort.by(Sort.Direction.DESC, "created")));
        return postList.stream().map(p->modelMapperService.converterToPostDTO(p)).collect(Collectors.toList());
    }
}
