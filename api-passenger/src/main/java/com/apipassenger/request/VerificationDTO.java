package com.apipassenger.request;

import lombok.Data;

@Data
public class VerificationDTO {

    private String passengerPhone;

    private String verificationCode;

}
