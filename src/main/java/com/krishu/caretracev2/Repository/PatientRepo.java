package com.krishu.caretracev2.Repository;

import com.krishu.caretracev2.Model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PatientRepo extends MongoRepository<Patient,String> {
    List<Patient> findByUserId(String user_id);
}
