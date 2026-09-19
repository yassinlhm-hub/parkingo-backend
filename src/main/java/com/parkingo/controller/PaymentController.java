package com.parkingo.controller;

import com.parkingo.dto.ChargeRequest;
import com.parkingo.model.Payment;
import com.parkingo.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/charge")
    public ResponseEntity<Payment> charge(@Valid @RequestBody ChargeRequest req) {
        return ResponseEntity.ok(paymentService.charge(req));
    }
}
