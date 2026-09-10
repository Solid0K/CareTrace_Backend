package com.krishu.caretracev2.Service;

import com.krishu.caretracev2.ClientRole;
import com.krishu.caretracev2.CustomExceptions.NotFoundException;
import com.krishu.caretracev2.CustomExceptions.UnauthorizedException;
import com.krishu.caretracev2.CustomExceptions.UserAlreadyExistsException;
import com.krishu.caretracev2.DTO.PatientMakingRequest;
import com.krishu.caretracev2.DTO.PatientResponse;
import com.krishu.caretracev2.DTO.PatientUpdateRequest;
import com.krishu.caretracev2.Model.CareTaker;
import com.krishu.caretracev2.Model.Client;
import com.krishu.caretracev2.Model.Patient;
import com.krishu.caretracev2.Repository.CareTakerRepo;
import com.krishu.caretracev2.Repository.PatientRepo;
import com.krishu.caretracev2.Repository.UserRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final UserRepo userRepo;
    private final PatientRepo patientRepo;
    private final CareTakerRepo careTakerRepo;
    private final BCryptPasswordEncoder encoder;

    public PatientService(UserRepo userRepo, PatientRepo patientRepo, CareTakerRepo careTakerRepo, BCryptPasswordEncoder encoder) {
        this.userRepo = userRepo;
        this.patientRepo = patientRepo;
        this.careTakerRepo = careTakerRepo;
        this.encoder = encoder;
    }

    public PatientResponse createPatient(PatientMakingRequest request, Authentication authentication){
        String careTakerId=authentication.getName();
        System.out.println("Authentication name: " + authentication.getName());
        CareTaker careTaker=careTakerRepo.findByUserId(careTakerId).orElseThrow(()->new NotFoundException("CareTaker not found"));
        if(userRepo.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException("Patient already exist with this email");
        }
        Client newUser=new Client();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(encoder.encode(request.getPassword()));
        newUser.setRole(ClientRole.PATIENT);
        Client savedUser=userRepo.save(newUser);

        Patient patient=new Patient();
        patient.setAge(request.getAge());
        patient.setUserId(savedUser.getId());
        patient.setCareTakerId(careTaker.getId());
        patient.setPreferred_language(request.getPreferredLanguage());

        Patient savedPatient=patientRepo.save(patient);
        careTaker.getPatientIds().add(savedPatient.getId());
        careTakerRepo.save(careTaker);
        return mapToPatientResponse(patient,newUser);
    }

    public PatientResponse getPatient(String patientId,Authentication authentication){
        CareTaker careTaker=careTakerRepo.findByUserId(authentication.
                getName()).orElseThrow(()->new NotFoundException("CareTaker not found"));
        Patient patient=patientRepo.findById(patientId).orElseThrow(()->new NotFoundException("Patient not found"));
        if(!patient.getCareTakerId().equals(careTaker.getId())){
            throw new UnauthorizedException("You not allow to see this Patient");
        }
        Client patientUser=userRepo.findById(patient.getUserId()).orElseThrow(()->new NotFoundException("User not found"));
        return mapToPatientResponse(patient,patientUser);
    }

    public PatientResponse updatePatient(String patientId, PatientUpdateRequest request,Authentication authentication){
        CareTaker careTaker=careTakerRepo.findByUserId(authentication.
                getName()).orElseThrow(()->new NotFoundException("CareTaker not found"));
        Patient patient=patientRepo.findById(patientId).orElseThrow(()->new NotFoundException("Patient not found"));
        if(!patient.getCareTakerId().equals(careTaker.getId())){
            throw new UnauthorizedException("You not allow to see this Patient");
        }
        patient.setAge(request.getAge());
        patient.setPreferred_language(request.getLanguage());
        Patient savedPatient=patientRepo.save(patient);
        Client patientUser=userRepo.findById(patient.getUserId()).orElseThrow(()->new NotFoundException("User not found"));
        return mapToPatientResponse(savedPatient,patientUser);
    }

    private PatientResponse mapToPatientResponse(Patient patient,Client patientUser){
        PatientResponse response=new PatientResponse();
        response.setName(patientUser.getName());
        response.setEmail(patientUser.getEmail());
        response.setPassword(patientUser.getPassword());
        response.setAge(patient.getAge());
        return response;
    }
}
