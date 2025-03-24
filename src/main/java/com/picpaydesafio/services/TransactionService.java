package com.picpaydesafio.services;

import com.picpaydesafio.domain.transaction.Transaction;
import com.picpaydesafio.domain.user.User;
import com.picpaydesafio.dtos.TransactionDTO;
import com.picpaydesafio.dtos.TransactionResponseDTO;
import com.picpaydesafio.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service

public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;


    public TransactionResponseDTO createTransaction(TransactionDTO transaction) throws Exception {
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById(transaction.receiverId());

        userService.validateTransaction(sender, transaction.amount());

        boolean isAuthorized = this.authorizeTransaction(sender, transaction.amount());

        if (!isAuthorized) {
            throw new Exception("Not authorized");
        }

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.amount());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transaction.amount()));
        receiver.setBalance(receiver.getBalance().add(transaction.amount()));

        this.repository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);

        String senderResponse = this.notificationService.sendNotification(sender, "Transaction successfully made");
        String receiverResponse = this.notificationService.sendNotification(receiver, "Transaction successfully made");

        System.out.println("notification sender response:" + senderResponse);
        System.out.println("notification receiver response:" + receiverResponse);

        String message = "Transaction successfully made. Notification sent.";

        if ("Notification service is down.".equalsIgnoreCase(senderResponse) || "Notification service is down.".equalsIgnoreCase(receiverResponse)) {
            message = "Transaction successfully made. Notification will be processed later.";
        }

        return new TransactionResponseDTO(sender.getEmail(), receiver.getEmail(), newTransaction.getAmount(), message, newTransaction.getTimestamp());
    }

    public boolean authorizeTransaction(User sender, BigDecimal amount) {
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://util.devi.tools/api/v2/authorize", Map.class);
        if (authorizationResponse.getStatusCode() != HttpStatus.OK) {
            return false;
        }

        String status = (String)authorizationResponse.getBody().get("status");
        return "success".equalsIgnoreCase(status);
    }
}
