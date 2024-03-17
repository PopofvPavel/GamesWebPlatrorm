package com.example.webprojectgames.repositories;

import com.example.webprojectgames.model.entities.Game;
import com.example.webprojectgames.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findByUsername(String username);

    Optional<User> findByTelegramChatId(Long chatId);
}
