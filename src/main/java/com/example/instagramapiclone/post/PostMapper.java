package com.example.instagramapiclone.post;

import com.example.instagramapiclone.post.web.CreatePostDto;
import com.example.instagramapiclone.post.web.PostDto;
import com.example.instagramapiclone.users.User;
import com.example.instagramapiclone.users.web.UserDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDto toDto(Post post);

    Post topost(PostDto postDto);

    Post createPostDtoToPost(CreatePostDto post);

    CreatePostDto toCreatePostDto(Post post);
    Iterable<PostDto> toDtoList(Iterable<Post> users);
}
