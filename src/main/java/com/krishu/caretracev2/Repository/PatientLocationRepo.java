package com.krishu.caretracev2.Repository;

import com.krishu.caretracev2.Model.PatientLocation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PatientLocationRepo extends MongoRepository<PatientLocation,String> {
    Optional<PatientLocation> findByPatientId(String patientId);
}
