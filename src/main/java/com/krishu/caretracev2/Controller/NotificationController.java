package com.krishu.caretracev2.Controller;

import com.krishu.caretracev2.Model.Notification;
import com.krishu.caretracev2.Service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/getNotification")
    public ResponseEntity<List<Notification>> getMyNotifications(Authentication authentication) {
        return ResponseEntity.ok(notificationService.getMyNotification(authentication));
    }

    @PutMapping("/markAsRead/{notificationId}")
    public ResponseEntity<Notification> markAsRead(@PathVariable String notificationId, Authentication authentication){
        return ResponseEntity.ok(notificationService.markAsRead(notificationId,authentication));
    }
}
