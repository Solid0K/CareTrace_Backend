package com.krishu.caretracev2.Repository;

import com.krishu.caretracev2.Model.Medication;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MedicationRepo extends MongoRepository<Medication,String> {
    List<Medication> findByPatientId(String patientId);
}
