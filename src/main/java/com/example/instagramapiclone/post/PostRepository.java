package com.example.instagramapiclone.post;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


    Optional<Post> findByCaption(String caption);

    @Transactional
    void deleteByUuid(String uuid);

    boolean existsByUuid(String uuid);
}
