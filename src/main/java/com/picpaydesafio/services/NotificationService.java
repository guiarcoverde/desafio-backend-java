package com.picpaydesafio.services;

import com.picpaydesafio.domain.user.User;
import com.picpaydesafio.dtos.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;

    public String sendNotification(User user, String message)  {
        String email = user.getEmail();

        NotificationDTO notificationRequest = new NotificationDTO(email, message);

        String notificationResponseToUser = "";



        try{
            ResponseEntity<Map> notificationResponse =
                    restTemplate.postForEntity("https://util.devi.tools/api/v1/notify", notificationRequest, Map.class);

            message = String.format("Notification sent to %s", email);
        } catch (RestClientException e){
            message = "Notification service is down.";
        }

        return message;
    }
}
