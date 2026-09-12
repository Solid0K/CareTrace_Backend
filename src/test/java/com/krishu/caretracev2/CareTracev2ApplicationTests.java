package com.krishu.caretracev2;

import com.krishu.caretracev2.Model.ImportantPerson;
import com.krishu.caretracev2.Model.Medication;
import com.krishu.caretracev2.Model.Routine;
import com.krishu.caretracev2.Repository.ImportantPersonRepo;
import com.krishu.caretracev2.Repository.MedicationRepo;
import com.krishu.caretracev2.Repository.RoutineRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalTime;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CareTracev2ApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ImportantPersonRepo importantPersonRepo;

    @Autowired
    private MedicationRepo medicationRepo;

    @Autowired
    private RoutineRepo routineRepo;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testAiQueryHttpEndpoint() throws Exception {
        String jsonPayload = """
                {
                    "patientId": "123",
                    "question": "What is the patient preferred language?"
                }
                """;

        mockMvc.perform(post("/api/ai/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientId").value("123"))
                .andExpect(jsonPath("$.question").value("What is the patient preferred language?"))
                .andExpect(jsonPath("$.context.patientId").value("123"))
                .andExpect(jsonPath("$.context.preferredLanguage").value("English"))
                .andExpect(jsonPath("$.prompt").isNotEmpty())
                .andExpect(jsonPath("$.response").isNotEmpty());
    }

    @Test
    void testAiQueryWithEnrichedContext() throws Exception {
        // 1. Seed test entities for patient "123"
        ImportantPerson person = new ImportantPerson();
        person.setName("Aarav");
        person.setRelation("Son");
        person.setPhoneNo(987654321);
        person.setPatientId("123");
        ImportantPerson savedPerson = importantPersonRepo.save(person);

        Medication med = new Medication();
        med.setName("Donepezil");
        med.setDosage("5mg");
        med.setFrequency("Daily at bedtime");
        med.setInstructions("Take with water");
        med.setPatientId("123");
        Medication savedMed = medicationRepo.save(med);

        Routine routine = new Routine();
        routine.setTitle("Evening Walk");
        routine.setTime(LocalTime.of(17, 30));
        routine.setDescription("In the apartment garden");
        routine.setPatientId("123");
        Routine savedRoutine = routineRepo.save(routine);

        try {
            // 2. Query AI endpoint asking about family and routine
            String jsonPayload = """
                    {
                        "patientId": "123",
                        "question": "Who is Aarav and what should I do in the evening?"
                    }
                    """;

            mockMvc.perform(post("/api/ai/query")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonPayload))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.patientId").value("123"))
                    .andExpect(jsonPath("$.context.importantPersons[0].name").value("Aarav"))
                    .andExpect(jsonPath("$.context.importantPersons[0].relation").value("Son"))
                    .andExpect(jsonPath("$.context.medications[0].name").value("Donepezil"))
                    .andExpect(jsonPath("$.context.routines[0].title").value("Evening Walk"))
                    .andExpect(jsonPath("$.prompt", containsString("Aarav (Son")))
                    .andExpect(jsonPath("$.prompt", containsString("Donepezil: Dosage: 5mg")))
                    .andExpect(jsonPath("$.prompt", containsString("Evening Walk at 17:30")))
                    .andExpect(jsonPath("$.response").isNotEmpty());
        } finally {
            // Clean up test records
            importantPersonRepo.deleteById(savedPerson.getId());
            medicationRepo.deleteById(savedMed.getId());
            routineRepo.deleteById(savedRoutine.getId());
        }
    }
}
