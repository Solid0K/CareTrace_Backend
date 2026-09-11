package com.krishu.caretracev2.Service;

import com.krishu.caretracev2.CustomExceptions.NotFoundException;
import com.krishu.caretracev2.CustomExceptions.NotRelatedException;
import com.krishu.caretracev2.CustomExceptions.UnauthorizedException;
import com.krishu.caretracev2.DTO.CareTakerPatientPair;
import com.krishu.caretracev2.DTO.RoutineRequest;
import com.krishu.caretracev2.DTO.RoutineResponse;
import com.krishu.caretracev2.Model.CareTaker;
import com.krishu.caretracev2.Model.Patient;
import com.krishu.caretracev2.Model.Routine;
import com.krishu.caretracev2.Repository.CareTakerRepo;
import com.krishu.caretracev2.Repository.PatientRepo;
import com.krishu.caretracev2.Repository.RoutineRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineService {

    private final RoutineRepo routineRepo;
    private final PatientRepo patientRepo;
    private final CareTakerRepo careTakerRepo;

    public RoutineService(RoutineRepo routineRepo, PatientRepo patientRepo, CareTakerRepo careTakerRepo) {
        this.routineRepo = routineRepo;
        this.patientRepo = patientRepo;
        this.careTakerRepo = careTakerRepo;
    }

    public RoutineResponse createRoutine(RoutineRequest request, String patientId, Authentication authentication){
        CareTakerPatientPair pair=careTakerAndPatient(authentication,patientId);
        CareTaker careTaker=pair.getCareTaker();
        Patient patient=pair.getPatient();
        Routine routine=new Routine();
        routine.setTitle(request.getTitle());
        routine.setDays(request.getDays());
        routine.setDescription(request.getDescription());
        routine.setPatientId(patientId);
        routine.setTime(request.getTime());
        Routine savedRoutine=routineRepo.save(routine);
        return mapToRoutineResponse(savedRoutine);
    }

    public List<RoutineResponse> getPatientsRoutine(String patientId, Authentication authentication){
        CareTakerPatientPair pair=careTakerAndPatient(authentication,patientId);
        CareTaker careTaker=pair.getCareTaker();
        Patient patient=pair.getPatient();
        List<Routine> routines=routineRepo.findByPatientId(patientId);
        return routines.stream().map(this::mapToRoutineResponse).toList();
    }

    public RoutineResponse updateRoutine(RoutineRequest request,String routineId,String patientId,Authentication authentication){
        CareTakerPatientPair pair=careTakerAndPatient(authentication,patientId);
        CareTaker careTaker=pair.getCareTaker();
        Patient patient=pair.getPatient();
        Routine routine=routineRepo.findById(routineId).orElseThrow(()->new NotFoundException("Routine not found"));
        if(!routine.getPatientId().equals(patient.getId())){
            throw new NotRelatedException("Routine does not related the patient");
        }
        routine.setTime(request.getTime());
        routine.setDescription(request.getDescription());
        routine.setDays(request.getDays());
        routine.setTitle(request.getTitle());
        Routine savedRoutine=routineRepo.save(routine);
        return mapToRoutineResponse(savedRoutine);
    }

    public void deleteRoutine(String routineId,String patientId,Authentication authentication){
        CareTakerPatientPair pair=careTakerAndPatient(authentication,patientId);
        CareTaker careTaker=pair.getCareTaker();
        Patient patient=pair.getPatient();
        Routine routine=routineRepo.findById(routineId).orElseThrow(()->new NotFoundException("Routine not found"));
        if(!routine.getPatientId().equals(patient.getId())){
            throw new NotRelatedException("Routine does not related the patient");
        }
        routineRepo.delete(routine);
    }

    private CareTakerPatientPair careTakerAndPatient(Authentication authentication, String patientId){
        CareTaker careTaker=careTakerRepo.findByUserId(authentication.getName()).
                orElseThrow(()->new NotFoundException("CareTaker not found"));
        Patient patient=patientRepo.findById(patientId).orElseThrow(()->new NotFoundException("Patient not found"));
        if(!patient.getCareTakerId().equals(careTaker.getId())){
            throw new UnauthorizedException("You are not authorized for this Patient");
        }
        CareTakerPatientPair pair=new CareTakerPatientPair(careTaker,patient);
        return pair;
    }

    private RoutineResponse mapToRoutineResponse(Routine routine){
        RoutineResponse response=new RoutineResponse();
        response.setId(routine.getId());
        response.setDays(routine.getDays());
        response.setDescription(routine.getDescription());
        response.setTitle(routine.getTitle());
        response.setTime(routine.getTime());
        return response;
    }
}
