package com.krishu.caretracev2.Repository;

import com.krishu.caretracev2.Model.ImportantPerson;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ImportantPersonRepo extends MongoRepository<ImportantPerson,String> {
    List<ImportantPerson> findByPatientId(String patientId);
}
