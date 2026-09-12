package com.krishu.caretracev2.Repository;

import com.krishu.caretracev2.Model.SafeZone;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SafeZoneRepo extends MongoRepository<SafeZone,String> {
    List<SafeZone> findByPatientId(String patientId);
}
