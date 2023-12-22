package com.example.instagramapiclone.users;

import com.example.instagramapiclone.users.web.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);


    Optional<User> findByUuid(String uuid);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    void deleteByUuid(String uuid);
}
