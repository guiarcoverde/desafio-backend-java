package com.picpaydesafio.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(String senderEmail, String receiverEmail, BigDecimal amount, String status, LocalDateTime timestamp) {
}
