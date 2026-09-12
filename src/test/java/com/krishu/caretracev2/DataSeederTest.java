package com.krishu.caretracev2;

import com.krishu.caretracev2.ClientRole;
import com.krishu.caretracev2.Day;
import com.krishu.caretracev2.ReminderType;
import com.krishu.caretracev2.Model.*;
import com.krishu.caretracev2.Repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
public class DataSeederTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CareTakerRepo careTakerRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Autowired
    private ImportantPersonRepo importantPersonRepo;

    @Autowired
    private MedicationRepo medicationRepo;

    @Autowired
    private RoutineRepo routineRepo;

    @Autowired
    private ReminderRepo reminderRepo;

    @Test
    void seedAllTestData() {
        System.out.println("========== SEEDING MONGODB WITH CARETRACE TEST DATA ==========");

        // 1. Patient User
        Client patientUser = new Client();
        patientUser.setId("user_patient_123");
        patientUser.setName("Ramesh Kumar");
        patientUser.setEmail("ramesh@caretrace.com");
        patientUser.setPassword("password123");
        patientUser.setRole(ClientRole.PATIENT);
        userRepo.save(patientUser);

        // 2. Caretaker User
        Client caretakerUser = new Client();
        caretakerUser.setId("user_caretaker_456");
        caretakerUser.setName("Dr. Sunita Sharma");
        caretakerUser.setEmail("sunita@caretrace.com");
        caretakerUser.setPassword("password123");
        caretakerUser.setRole(ClientRole.CARETAKER);
        userRepo.save(caretakerUser);

        // 3. Caretaker Profile
        CareTaker careTaker = new CareTaker();
        careTaker.setId("ct_456");
        careTaker.setUserId("user_caretaker_456");
        careTaker.setPatientIds(List.of("patient_123"));
        careTakerRepo.save(careTaker);

        // 4. Patient Profile
        Patient patient = new Patient();
        patient.setId("patient_123");
        patient.setUserId("user_patient_123");
        patient.setAge(76);
        patient.setPreferred_language("English");
        patient.setCareTakerId("ct_456");
        patient.setNotes("Loves morning tea on the balcony and gardening. Experiences mild disorientation and restlessness around sundown (5 PM - 7 PM). Responds very well to calm Indian classical music.");
        patientRepo.save(patient);

        // 5. Important Persons (Family)
        ImportantPerson person1 = new ImportantPerson();
        person1.setId("person_1");
        person1.setPatientId("patient_123");
        person1.setName("Aarav Kumar");
        person1.setRelation("Son");
        person1.setPhoneNo(987654321);
        importantPersonRepo.save(person1);

        ImportantPerson person2 = new ImportantPerson();
        person2.setId("person_2");
        person2.setPatientId("patient_123");
        person2.setName("Priya Sharma");
        person2.setRelation("Daughter");
        person2.setPhoneNo(912345678);
        importantPersonRepo.save(person2);

        // 6. Medications
        Medication med1 = new Medication();
        med1.setId("med_1");
        med1.setPatientId("patient_123");
        med1.setName("Donepezil");
        med1.setDosage("5mg");
        med1.setFrequency("Once daily at bedtime");
        med1.setStartDate(LocalDate.of(2026, 1, 1));
        med1.setEndDate(LocalDate.of(2026, 12, 31));
        med1.setInstructions("Take with water right before bed for memory support.");
        medicationRepo.save(med1);

        Medication med2 = new Medication();
        med2.setId("med_2");
        med2.setPatientId("patient_123");
        med2.setName("Metformin");
        med2.setDosage("500mg");
        med2.setFrequency("Twice daily");
        med2.setStartDate(LocalDate.of(2026, 1, 1));
        med2.setEndDate(LocalDate.of(2026, 12, 31));
        med2.setInstructions("Take after meals (breakfast and dinner) for blood sugar.");
        medicationRepo.save(med2);

        // 7. Routines
        Routine routine1 = new Routine();
        routine1.setId("routine_1");
        routine1.setPatientId("patient_123");
        routine1.setTitle("Morning Balcony Tea & Walk");
        routine1.setDescription("Sit in the morning sun on the balcony, drink warm chai, and do a light 10-minute walk.");
        routine1.setTime(LocalTime.of(7, 30));
        routine1.setDays(List.of(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY, Day.SATURDAY, Day.SUNDAY));
        routineRepo.save(routine1);

        Routine routine2 = new Routine();
        routine2.setId("routine_2");
        routine2.setPatientId("patient_123");
        routine2.setTitle("Evening Soothing Music");
        routine2.setDescription("Sit on the couch and listen to relaxing old songs to ease evening sundowning confusion.");
        routine2.setTime(LocalTime.of(17, 30));
        routine2.setDays(List.of(Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY, Day.FRIDAY, Day.SATURDAY, Day.SUNDAY));
        routineRepo.save(routine2);

        // 8. Reminders
        Reminder reminder1 = new Reminder();
        reminder1.setId("reminder_1");
        reminder1.setDate(LocalDate.of(2026, 9, 12));
        reminder1.setTime(LocalTime.of(7, 30));
        reminder1.setType(ReminderType.ROUTINE);
        reminder1.setRoutineId("routine_1");
        reminderRepo.save(reminder1);

        Reminder reminder2 = new Reminder();
        reminder2.setId("reminder_2");
        reminder2.setDate(LocalDate.of(2026, 9, 12));
        reminder2.setTime(LocalTime.of(21, 30));
        reminder2.setType(ReminderType.MEDICATION);
        reminder2.setMedicationId("med_1");
        reminderRepo.save(reminder2);

        System.out.println("========== SEEDING COMPLETE! ALL DOCUMENTS INSERTED INTO MONGODB ==========");
    }
}
