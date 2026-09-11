package com.krishu.caretracev2;

import com.krishu.caretracev2.Repository.PatientRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CareTracev2Application {

    public static void main(String[] args) {
        SpringApplication.run(CareTracev2Application.class, args);
    }

    @Bean
    public CommandLineRunner debugPatients(PatientRepo patientRepo) {
        return args -> {
            long count = patientRepo.count();
            System.out.println("=== DEBUG: total patients found = " + count);
            patientRepo.findAll().forEach(p ->
                System.out.println("=== DEBUG patient: " + p.getId())
            );
        };
    }

}
