package com.example.instagramapiclone.post;

import com.example.instagramapiclone.post.web.CreatePostDto;
import com.example.instagramapiclone.post.web.PostDto;
import com.example.instagramapiclone.users.User;
import com.example.instagramapiclone.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserService userService;

    @Override
    public PostDto createPost(CreatePostDto post) {

        Post postObj = new Post();
        postObj.setUuid(UUID.randomUUID().toString());
        postObj.setCaption(post.caption());
        postObj.setPostImg(post.postImg());
        postObj.setCreatedAt(LocalDateTime.now());
        postObj.setUpdatedAt(LocalDateTime.now());
        postObj.setIsDeleted(false);
        User user = userService.getUserById(post.userId());
        postObj.setUser(user);
        try {
            var postObj1 = postRepository.save(postObj);
            return postMapper.toDto(postObj1);
        } catch (DataAccessException e) {
            throw new RuntimeException("Post could not be created");
        }
    }

    @Override
    public Iterable<PostDto> getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder, String caption) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        // Fetch associations explicitly if needed
        // For example, if Post has associations like comments, fetch them explicitly
        // postRepository.findAllWithComments(pageable);

        // Creating criteria for fetching posts
        Post postCriteria = new Post();
        postCriteria.setCaption(caption);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("caption", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<Post> example = Example.of(postCriteria, matcher);

        // Fetch posts using the criteria and pageable
        Page<Post> posts = postRepository.findAll(example, pageable);

        // Map fetched Post entities to DTOs
        return postMapper.toDtoList(posts);
    }

    @Override
    public PostDto updatePost(String uuid, PostDto post) {
        return null;
    }

    @Override
    public void deletePost(String uuid) {
        postRepository.deleteByUuid(uuid);

    }

    @Override
    public PostDto getPostByCaption(String caption) {
        Post post = postRepository.findByCaption(caption).orElseThrow(() -> new RuntimeException("Post not found!!!"));
        return postMapper.toDto(post);

    }
}
