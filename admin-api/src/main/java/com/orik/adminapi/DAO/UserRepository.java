package com.orik.adminapi.DAO;

import com.orik.adminapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByChatId(Long id);
    Optional<User> findByUserName(String userName);
}
