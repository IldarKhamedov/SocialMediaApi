package ru.khamedov.ildar.socialMedia.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SecurityRequirement(name = "JWT")
@SecurityRequirement(name = "BASIC")
@Tag(name = "Лента активности")
public class FeedRestController {

    @Resource
    private PostService postService;

    @Resource
    private AuthService authService;

    @Operation(summary = "Получить ленту постов от пользователей, на которых подписан")
    @GetMapping(params = {"page", "size", "sort"})
    public List<PostDTO> getFeed(@RequestParam("page") @Parameter(description = "Для пагинации: номер страницы",required = true) int page,
                                 @RequestParam("size") @Parameter(description = "Для пагинации: количество постов на странице",required = true) int size,
                                 @RequestParam("sort") @Parameter(description = "Для сортировки по времени создания поста",required = true) Instant sort) {

        return postService.getPostByFilterList(authService.getUserProfile(), sort, page, size);
    }
}
