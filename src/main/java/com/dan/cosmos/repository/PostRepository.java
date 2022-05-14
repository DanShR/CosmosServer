package com.dan.cosmos.repository;

import com.dan.cosmos.model.AppUser;
import com.dan.cosmos.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAppUser(AppUser appUser);
}
