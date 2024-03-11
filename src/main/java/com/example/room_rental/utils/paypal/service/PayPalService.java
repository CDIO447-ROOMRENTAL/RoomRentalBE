package com.example.room_rental.utils.paypal.service;

import com.example.room_rental.utils.paypal.dto.PaymentDTO;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayPalService {

    @Autowired
    private APIContext apiContext;

    public Payment createPayment(PaymentDTO paymentDTO) throws PayPalRESTException {
        return paymentDTO.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = Payment.get(apiContext, paymentId);
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }
}
