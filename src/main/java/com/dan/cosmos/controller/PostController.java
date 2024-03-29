package com.dan.cosmos.controller;

import com.dan.cosmos.dto.PostDTO;
import com.dan.cosmos.event.EventPublisher;
import com.dan.cosmos.model.Post;
import com.dan.cosmos.security.MyUserDetails;
import com.dan.cosmos.service.PostService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final ModelMapper modelMapper;
    private final EventPublisher eventPublisher;

    @GetMapping("/")
    public ResponseEntity<?> userPosts(@AuthenticationPrincipal MyUserDetails myUserDetails) {
        List<Post> postList = postService.findAllAppUserPosts(myUserDetails.getAppUser());
        List<PostDTO> postDtoList = modelMapper.map(postList, new TypeToken<List<PostDTO>>(){}.getType());
        return new ResponseEntity<>(postDtoList, HttpStatus.OK);
    }

    @PostMapping(value = "/newpost")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PostDTO> addPost(@AuthenticationPrincipal MyUserDetails myUserDetails,
                                           @RequestPart(name = "file", required = false) MultipartFile file,
                                           @RequestPart(name = "text") String text) {

        Post post =  postService.createPost(myUserDetails.getAppUser(), text, file);
        return new ResponseEntity<>(modelMapper.map(post, PostDTO.class), HttpStatus.OK);
    }

    @DeleteMapping("/deletePost")
    public ResponseEntity<?> deletePost(@RequestParam(name = "id") Long id) {
        if (postService.deletePostById(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updatepost")
    public ResponseEntity<?> updatePost(@RequestParam(name = "id") Post oldPost, @RequestBody PostDTO newPostDTO) {
        Post post = postService.updatePost(oldPost, newPostDTO);
        return new ResponseEntity<>(modelMapper.map(post, PostDTO.class), HttpStatus.OK);
    }
}
