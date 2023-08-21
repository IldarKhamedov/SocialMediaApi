package ru.khamedov.ildar.socialMedia.controller;

import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.khamedov.ildar.socialMedia.dto.PostDTO;
import ru.khamedov.ildar.socialMedia.service.AuthService;
import ru.khamedov.ildar.socialMedia.service.PostService;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/social/feed")
public class FeedRestController {

    @Resource
    private PostService postService;

    @Resource
    private AuthService authService;

    @GetMapping(params = {"page", "size", "sort"})
    public List<PostDTO> getFeed(@RequestParam("page") int page,
                                 @RequestParam("size") int size,
                                 @RequestParam("sort") Instant sort) {

        return postService.getPostByFilterList(authService.getUserProfile(), sort, page, size);
    }
}
