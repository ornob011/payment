package com.dsi.rjsc.payment.helpers;

import com.dsi.rjsc.payment.dto.PaymentDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PaymentDTOHelper {

    public static String toJSON(PaymentDTO paymentDTO) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(paymentDTO);
        } catch (JsonProcessingException e) {
            // Log this error or handle it as appropriate
            e.printStackTrace();
            return null;
        }
    }
}