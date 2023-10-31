package com.dsi.rjsc.payment.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private String merchantId;
    private String amount;
    private String currency;
    private String description;
    private String approveUrl;
    private String cancelUrl;
    private String declineUrl;
    private String userName;
    private String passWord;
    private String secureToken;

    public String toJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            // Log this error or handle it as appropriate
            e.printStackTrace();
            return null;
        }
    }
}



