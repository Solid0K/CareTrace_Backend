package com.krishu.caretracev2.Repository;

import com.krishu.caretracev2.Model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepo extends MongoRepository<Message,String> {
    List<Message> findByPatientIdOrderByCreatedAtAsc(String patientId);
    List<Message> findByReceiverIdAndReadFalse(String receiverId);
}
