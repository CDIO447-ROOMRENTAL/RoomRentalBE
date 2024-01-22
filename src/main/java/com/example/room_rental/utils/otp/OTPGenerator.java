package com.example.room_rental.utils.otp;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
@Service
public class OTPGenerator {

    private static final String OTP_CHARACTERS = "0123456789";
    private static final int OTP_LENGTH = 6;

    public String generateOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(OTP_LENGTH);

        for (int i = 0; i < OTP_LENGTH; i++) {
            int randomIndex = random.nextInt(OTP_CHARACTERS.length());
            otp.append(OTP_CHARACTERS.charAt(randomIndex));
        }

        return otp.toString();
    }
}