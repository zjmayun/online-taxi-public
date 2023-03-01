package com.wish.internal.common.request;

import lombok.Data;

@Data
public class VerificationDTO {

    private String passengerPhone;

    private String verificationCode;

}
