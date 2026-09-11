package com.krishu.caretracev2.DTO;

public class AIQueryRequest {

    private String question;
    private String patientId;

    public String getQuestion() {
        return question;
    }
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId){
        this.patientId = patientId;
    }


    public void setQuestion(String question) {
        this.question = question;
    }
    
}
