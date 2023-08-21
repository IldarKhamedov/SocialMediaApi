package ru.khamedov.ildar.socialMedia.controller;

import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.khamedov.ildar.socialMedia.dto.ImageFileDTO;
import ru.khamedov.ildar.socialMedia.dto.PostDTO;
import ru.khamedov.ildar.socialMedia.dto.UpdatePostDTO;
import ru.khamedov.ildar.socialMedia.model.post.Post;
import ru.khamedov.ildar.socialMedia.repository.PostRepository;
import ru.khamedov.ildar.socialMedia.service.ModelMapperService;
import ru.khamedov.ildar.socialMedia.service.PostService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/social/post")
public class PostRestController {

    @Resource
    private PostService postService;

    @Resource
    private PostRepository postRepository;

    @Resource
    private ModelMapperService modelMapperService;



    @PostMapping("/create")
    public ResponseEntity createPost(@RequestBody PostDTO postDTO){
        Post post=postService.createPost(postDTO);
        return new ResponseEntity(post.getId(), HttpStatus.OK);
    }

    @PutMapping("/addImage/{postId}")
    public ResponseEntity addImage(@RequestBody List<ImageFileDTO> imageFileDTOList, @PathVariable(required = true)Long postId){
        postService.addImage(postId,imageFileDTOList);
        return new ResponseEntity<>(postId,HttpStatus.OK);
    }

    @GetMapping("/view/{userName}")
    public ResponseEntity getPostByUserList(@PathVariable(required = true)String userName){
        List<PostDTO> postDTOList=postRepository.findPostByUserName(userName).stream().map(p->modelMapperService.converterToPostDTO(p)).collect(Collectors.toList());
        return new ResponseEntity<>(postDTOList,HttpStatus.OK);
    }
    @DeleteMapping("delete/{postId}")
    public ResponseEntity deletePostById(@PathVariable(required = true)Long postId){
        boolean isDeleted=postService.deletePost(postId);
        return new ResponseEntity<>(isDeleted,HttpStatus.OK);
    }

    @PutMapping("/update/{postId}")
    public ResponseEntity updatePost(@RequestBody UpdatePostDTO updatePostDTO, @PathVariable(required = true)Long postId){
        boolean isUpdated=postService.updatePostById(postId,updatePostDTO.getText());
        return new ResponseEntity<>(isUpdated,HttpStatus.OK);
    }
}
