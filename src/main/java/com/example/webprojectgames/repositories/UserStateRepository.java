package com.example.webprojectgames.repositories;

import com.example.webprojectgames.model.entities.UserState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserStateRepository extends JpaRepository<UserState, Long> {
    Optional<UserState> findUserStateByUserId(Long userId);
}
