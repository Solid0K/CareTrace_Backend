package com.krishu.caretracev2.Service;

import com.krishu.caretracev2.CustomExceptions.NotFoundException;
import com.krishu.caretracev2.CustomExceptions.UnauthorizedException;
import com.krishu.caretracev2.DTO.MessageRequest;
import com.krishu.caretracev2.DTO.MessageResponse;
import com.krishu.caretracev2.Model.CareTaker;
import com.krishu.caretracev2.Model.Message;
import com.krishu.caretracev2.Model.Patient;
import com.krishu.caretracev2.Repository.CareTakerRepo;
import com.krishu.caretracev2.Repository.MessageRepo;
import com.krishu.caretracev2.Repository.PatientRepo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepo messageRepo;
    private final PatientRepo patientRepo;
    private final CareTakerRepo careTakerRepo;

    public MessageService(MessageRepo messageRepo, PatientRepo patientRepo, CareTakerRepo careTakerRepo) {
        this.messageRepo = messageRepo;
        this.patientRepo = patientRepo;
        this.careTakerRepo = careTakerRepo;
    }

    public MessageResponse sendMessage(String patientId, MessageRequest request, Authentication authentication){
        String userId=authentication.getName();
        Patient patient=patientRepo.findById(patientId).orElseThrow(()->new NotFoundException("Patient not found"));
        Message message=new Message();
        Patient senderPatient=patientRepo.findByUserId(userId).orElse(null);
        if(senderPatient!=null){
            if(!senderPatient.getId().equals(patient.getId())){
                throw new UnauthorizedException("You are not authorized to send message to this patient");
            }
            if(!senderPatient.getCareTakerId().equals(request.getReceiverId())){
                throw new UnauthorizedException("You can only message your careTaker");
            }
            message.setSenderId(senderPatient.getUserId());
            message.setReceiverId(request.getReceiverId());
        }else{
            CareTaker careTaker=careTakerRepo.findByUserId(userId).orElseThrow(()-> new NotFoundException("You are not authorized to send messages"));
            if(!careTaker.getPatientIds().contains(request.getReceiverId())){
                throw new UnauthorizedException("You are not authorize for this patient");
            }
            message.setSenderId(userId);
            message.setReceiverId(request.getReceiverId());
        }
        message.setContent(request.getMessage());
        message.setCreatedAt(LocalDateTime.now());
        message.setPatientId(patientId);
        message.setRead(false);
        Message savedMessage=messageRepo.save(message);
        return mapToMessageResponse(savedMessage);
    }

    public List<MessageResponse> getConversation(String patientId, Authentication authentication){
        String userId=authentication.getName();
        Patient patient=patientRepo.findById(patientId).orElseThrow(()->new NotFoundException("Patient not found"));
        Patient userPatient=patientRepo.findByUserId(userId).orElse(null);
        if(userPatient!=null){
            if(!userPatient.getId().equals(patientId)){
                throw new UnauthorizedException("You cannot access this conversation");
            }
        }else{
            CareTaker careTaker=careTakerRepo.findByUserId(userId).orElseThrow(()->new UnauthorizedException("You should be careTaker or a patient"));
            if(!careTaker.getPatientIds().contains(patientId)){
                throw new UnauthorizedException("You are not authorized for this conversation");
            }
        }
        return messageRepo.findByPatientIdOrderByCreatedAtAsc(patientId).stream().map(this::mapToMessageResponse).toList();
    }

    public void markAsRead(String messageId,Authentication authentication){
        String userId=authentication.getName();
        Message message=messageRepo.findById(messageId).orElseThrow(()->new NotFoundException("Message not found"));
        boolean isPatient=patientRepo.findByUserId(userId).map(patient->patient.getId().equals(message.getReceiverId())).orElse(false);
        boolean isCareTaker=careTakerRepo.findByUserId(userId).map(careTaker->careTaker.getId().equals(message.getReceiverId())).orElse(false);
        if(!isPatient && !isCareTaker){
            throw new UnauthorizedException("You are not authorized");
        }
        message.setRead(true);
        messageRepo.save(message);
    }

    public List<MessageResponse> getUnreadMessages(Authentication authentication) {
        String userId=authentication.getName();
        Patient patient=patientRepo.findByUserId(userId).orElse(null);
        List<Message> unreadMessages=new ArrayList<>();
        if(patient!=null){
            unreadMessages.addAll(messageRepo.findByReceiverIdAndReadFalse(patient.getId()));
        }else{
            CareTaker careTaker=careTakerRepo.findByUserId(userId).orElseThrow(()->new NotFoundException("Account not found"));
            unreadMessages.addAll(messageRepo.findByReceiverIdAndReadFalse(careTaker.getId()));
        }
        return unreadMessages.stream().map(this::mapToMessageResponse).toList();
    }

    private MessageResponse mapToMessageResponse(Message message){
        MessageResponse response=new MessageResponse();
        response.setId(message.getId());
        response.setContent(message.getContent());
        response.setCreatedAt(message.getCreatedAt());
        response.setPatientId(message.getPatientId());
        response.setRead(message.getRead());
        response.setReceiverId(message.getReceiverId());
        response.setSenderId(message.getSenderId());
        return response;
    }
}
