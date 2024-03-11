package com.example.room_rental.utils.paypal.controller;

import com.example.room_rental.utils.paypal.dto.PaymentDTO;
import com.example.room_rental.utils.paypal.service.PayPalService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paypal")
public class PayPalController {

    @Autowired
    private PayPalService payPalService;

    @PostMapping("/create-payment")
    public Payment createPayment(@RequestBody PaymentDTO paymentDTO) throws PayPalRESTException {
        return payPalService.createPayment(paymentDTO);
    }

    @PostMapping("/execute-payment")
    public Payment executePayment(@RequestParam("paymentId") String paymentId,
                                  @RequestParam("payerId") String payerId) throws PayPalRESTException {
        return payPalService.executePayment(paymentId, payerId);
    }
}
