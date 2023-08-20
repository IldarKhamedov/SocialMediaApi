package ru.khamedov.ildar.socialMedia.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.khamedov.ildar.socialMedia.dto.PostDTO;

@RestController
@RequestMapping("api/social/post")
public class PostRestController {

    @PostMapping("/create")
    public ResponseEntity createPost(@RequestBody PostDTO postDTO){
        return new ResponseEntity("test", HttpStatus.OK);
    }
}
