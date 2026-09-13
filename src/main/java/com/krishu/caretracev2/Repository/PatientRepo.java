package com.krishu.caretracev2.Repository;

import com.krishu.caretracev2.Model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepo extends MongoRepository<Patient,String> {
    Optional<Patient> findByUserId(String user_id);
    List<Patient> findByCareTakerId(String careTakerId);
}
