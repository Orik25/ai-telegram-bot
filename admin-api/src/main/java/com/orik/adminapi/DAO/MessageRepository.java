package com.orik.adminapi.DAO;


import com.orik.adminapi.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findByFromUserUserIdOrToUserUserId(Long fromUserId,Long toUserId);
}
