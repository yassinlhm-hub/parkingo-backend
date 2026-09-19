package com.parkingo.service;

import com.parkingo.dto.ChargeRequest;
import com.parkingo.exception.ApiException;
import com.parkingo.model.Payment;
import com.parkingo.repository.PaymentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Simula el cobro de un servicio. El proveedor real (Stripe, Redsys, etc.)
 * se conecta aquí sustituyendo el cuerpo de charge() por la llamada real
 * al SDK del proveedor — el resto del flujo (persistencia, estado) no cambia.
 */
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment charge(ChargeRequest req) {
        if (paymentRepository.findByRequestId(req.requestId()).isPresent()) {
            throw new ApiException(HttpStatus.CONFLICT, "Esta solicitud ya tiene un pago registrado");
        }

        String providerReference = "SIM-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase();
        Payment payment = new Payment(req.requestId(), req.amountCents(), providerReference);
        return paymentRepository.save(payment);
    }
}
