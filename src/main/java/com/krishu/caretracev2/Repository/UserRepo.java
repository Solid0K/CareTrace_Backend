package com.krishu.caretracev2.Repository;

import com.krishu.caretracev2.Model.Client;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<Client,String> {
    Optional<Client> findByEmail(String email);
    boolean existsByEmail(String email);
}
