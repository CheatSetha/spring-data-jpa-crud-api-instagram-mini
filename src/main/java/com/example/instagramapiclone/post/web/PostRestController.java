package com.example.instagramapiclone.post.web;

import com.example.instagramapiclone.base.AppConstants;
import com.example.instagramapiclone.base.BaseApi;
import com.example.instagramapiclone.file.FileService;
import com.example.instagramapiclone.post.PostRepository;
import com.example.instagramapiclone.post.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@Slf4j
public class PostRestController {
    private final PostService postService;
    private final PostRepository postRepository;
    private final FileService fileService;

    @PostMapping
    public ResponseEntity<BaseApi<?>> createPost(@Valid @RequestBody CreatePostDto post) {
//        FileDto fileDto = fileService.uploadSingleFile(file);
        log.info("Post from client in controller: {}", post);
        try {
            var postObj = postService.createPost(post);
            return ResponseEntity.ok(BaseApi.builder().code(200).isSuccess(true).message("Post has been created").payload(postObj).build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(BaseApi.builder().code(400).isSuccess(false).message(e.getMessage()).build());
        }

    }

    @GetMapping
    public ResponseEntity<BaseApi<?>> getAllPosts(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                  @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                  @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_USERS_BY, required = false) String sortBy,
                                                  @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder,
                                                  @RequestParam(name = "caption", defaultValue = "", required = false) String caption) {
        try {
            // Fetch posts using the service layer method
            var postDtos = postService.getAllPosts(pageNumber, pageSize, sortBy, sortOrder, caption);

            // Return DTOs instead of entities in the response payload
            return ResponseEntity.ok(BaseApi.builder().code(200).isSuccess(true).timestamp(LocalDate.now())
                    .message("Posts have been fetched").payload(postDtos).build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(BaseApi.builder().code(400).isSuccess(false)
                    .message(e.getMessage()).build());
        }
    }

    @GetMapping("/{caption}")
    public ResponseEntity<BaseApi<?>> getPostByCaption(@PathVariable String caption) {
        try {
            var postDto = postService.getPostByCaption(caption);
            return ResponseEntity.ok(BaseApi.builder().code(200).isSuccess(true).message("Post has been fetched").payload(postDto).build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(BaseApi.builder().code(400).isSuccess(false).message(e.getMessage()).build());
        }
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<BaseApi<?>> deletePost(@PathVariable String uuid) {
        boolean isPostExists = postRepository.existsByUuid(uuid);
        if (!isPostExists) {
            return ResponseEntity.badRequest().body(BaseApi.builder()
                    .code(400)
                    .isSuccess(false)
                    .message("Post does not exist")
                    .timestamp(LocalDate.now())
                    .payload(null)
                    .build());
        }
        try {
            postService.deletePost(uuid);
            return ResponseEntity.ok(BaseApi.builder()
                    .code(200).
                    isSuccess(true)
                    .message("Post has been deleted")
                    .timestamp(LocalDate.now())
                    .payload(null)
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(BaseApi.builder().code(400).isSuccess(false).message(e.getMessage()).build());
        }
    }
}
