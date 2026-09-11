package com.krishu.caretracev2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CareTracev2ApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

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
}
