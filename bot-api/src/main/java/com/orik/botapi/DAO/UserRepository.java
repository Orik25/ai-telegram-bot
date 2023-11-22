package com.orik.botapi.DAO;

import com.orik.botapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByChatId(Long id);
}
