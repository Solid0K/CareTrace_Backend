package com.krishu.caretracev2.Repository;

import com.krishu.caretracev2.Model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepo extends MongoRepository<Notification,String> {
    List<Notification> findByRecipientId(String recipientId);
    List<Notification> findByRecipientIdAndReadFalse(String recipientId);
    List<Notification> findByRecipientIdOrderByCreatedAtDesc(String recipientId);
    List<Notification> findByRecipientIdAndReadFalseOrderByCreatedAtDesc(String recipientId);
}
