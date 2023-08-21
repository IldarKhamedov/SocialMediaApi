package ru.khamedov.ildar.socialMedia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SecurityRequirement(name = "JWT")
@SecurityRequirement(name = "BASIC")
@Tag(name = "Управление постами")
public class PostRestController {

    @Resource
    private PostService postService;

    @Resource
    private PostRepository postRepository;

    @Resource
    private ModelMapperService modelMapperService;



    @Operation(summary = "Создать пост")
    @PostMapping("/create")
    public ResponseEntity createPost(@RequestBody PostDTO postDTO){
        Post post=postService.createPost(postDTO);
        return new ResponseEntity(post.getId(), HttpStatus.OK);
    }

    @Operation(summary = "Добавить картинки к посту")
    @PutMapping("/addImage/{postId}")
    public ResponseEntity addImage(@RequestBody List<ImageFileDTO> imageFileDTOList,
                                   @PathVariable @Parameter(description = "Id поста",required = true) Long postId){
        return new ResponseEntity<>(postService.addImage(postId,imageFileDTOList),HttpStatus.OK);
    }

    @Operation(summary = "Просмотр постов пользователя")
    @GetMapping("/view/{userName}")
    public ResponseEntity getPostByUserList(@PathVariable @Parameter(description = "Имя пользователя, чьи посты нужно получить",required = true)String userName){
        List<PostDTO> postDTOList=postRepository.findPostByUserName(userName).stream().map(p->modelMapperService.converterToPostDTO(p)).collect(Collectors.toList());
        return new ResponseEntity<>(postDTOList,HttpStatus.OK);
    }
    @Operation(summary = "Удалить пост")
    @DeleteMapping("delete/{postId}")
    public ResponseEntity deletePostById(@PathVariable @Parameter(description = "Id поста для удаления",required = true)Long postId){
        return new ResponseEntity<>(postService.deletePost(postId),HttpStatus.OK);
    }

    @Operation(summary = "Обновить пост")
    @PutMapping("/update/{postId}")
    public ResponseEntity updatePost(@RequestBody UpdatePostDTO updatePostDTO,
                                     @PathVariable@Parameter(description = "Id поста для обновления",required = true)Long postId){
        return new ResponseEntity<>(postService.updatePostById(postId,updatePostDTO.getText()),HttpStatus.OK);
    }
}
