package com.krishu.caretracev2.Repository;

import com.krishu.caretracev2.Model.CareTaker;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CareTakerRepo extends MongoRepository<CareTaker,String> {
    Optional<CareTaker> findByUserId(String user_id);
}
