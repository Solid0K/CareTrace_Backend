package com.krishu.caretracev2.Repository;

import com.krishu.caretracev2.AlertStatus;
import com.krishu.caretracev2.AlertType;
import com.krishu.caretracev2.Model.Alert;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AlertRepo extends MongoRepository<Alert,String> {
    List<Alert> findByPatientId(String patientId);
    Optional<Alert> findByPatientIdAndTypeAndStatus(String patientId, AlertType type, AlertStatus status);
}
