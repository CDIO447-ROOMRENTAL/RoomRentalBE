package com.example.room_rental.utils.paypal.dto;

import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class PaymentDTO {
    private String intent;
    private Payer payer;
    private Transaction[] transactions;

    public Payment create(APIContext apiContext) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(Arrays.asList(transactions));

        return payment.create(apiContext);
    }
}
