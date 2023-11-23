package com.orik.adminapi.DAO;

import com.orik.adminapi.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByChatId(Long id);
    Page<User> findUsersByRoleRoleId(Long id, PageRequest pageRequest);
    Optional<User> findByUserName(String userName);

    @Query(value = "SELECT u.*" +
            "FROM users u " +
            "WHERE " +
            "CASE " +
            "WHEN :fieldName = 'user_name' THEN LOWER(u.user_name) LIKE LOWER(CONCAT('%', :searchValue, '%')) " +
            "WHEN :fieldName = 'first_name' THEN LOWER(u.first_name) LIKE LOWER(CONCAT('%', :searchValue, '%')) " +
            "END",
            countQuery = "SELECT COUNT(*) FROM users u " +
                    "WHERE " +
                    "CASE " +
                    "WHEN :fieldName = 'user_name' THEN LOWER(u.user_name) LIKE LOWER(CONCAT('%', :searchValue, '%')) " +
                    "WHEN :fieldName = 'first_name' THEN LOWER(u.first_name) LIKE LOWER(CONCAT('%', :searchValue, '%')) " +
                    "END",
            nativeQuery = true)
    Page<User> findByFieldContainingIgnoreCase(@Param("fieldName") String fieldName, @Param("searchValue") String searchValue, Pageable pageable);
}
