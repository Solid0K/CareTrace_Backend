package com.krishu.caretracev2.Repository;

import com.krishu.caretracev2.Model.Routine;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoutineRepo extends MongoRepository<Routine,String> {
    List<Routine> findByPatientId(String patientId);
}
