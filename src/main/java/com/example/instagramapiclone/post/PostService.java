package com.example.instagramapiclone.post;

import com.example.instagramapiclone.post.web.CreatePostDto;
import com.example.instagramapiclone.post.web.PostDto;

public interface PostService {
//    past new feet like instagram
    PostDto createPost(CreatePostDto post);
    Iterable<PostDto> getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder , String caption);
    PostDto getPostByCaption(String caption);

    PostDto updatePost(String uuid,PostDto post);

    void deletePost(String uuid);
}
