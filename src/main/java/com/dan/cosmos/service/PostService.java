package com.dan.cosmos.service;

import com.dan.cosmos.dto.PostDTO;
import com.dan.cosmos.exception.postException.PostNotFoundException;
import com.dan.cosmos.model.AppUser;
import com.dan.cosmos.model.Post;
import com.dan.cosmos.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final FileService fileService;

    public List<Post> findAllAppUserPosts(AppUser appUser) {
      return postRepository.findByAppUser(appUser);
    }

    public Post createPost(AppUser appUser, String text, MultipartFile file) {
        Post post = new Post();
        post.setAppUser(appUser);
        post.setText(text);
        post.setCreated(new Date());
        if (file != null)
            post.setFilename(fileService.saveFile(file));

        postRepository.save(post);
        return post;
    }

    public boolean deletePostById(Long id) {
        Optional<Post> post = postRepository.findById(id);
        if (post.isEmpty()) {
            throw new PostNotFoundException();
        }
        postRepository.delete(post.get());
        return true;
    }

    public Post updatePost(Post post, PostDTO newPostDTO) {
        post.setText(newPostDTO.getText());
        postRepository.save(post);
        return post;
    }
}
