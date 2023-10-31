package com.dsi.rjsc.payment.service;

import com.dsi.rjsc.payment.config.PaymentConfig;
import com.dsi.rjsc.payment.dto.PaymentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Service
public class PaymentService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PaymentConfig paymentConfig;

    @Autowired
    private PaymentRequestService paymentRequestService;

    public interface PaymentStrategy {
        Map<String, Object> execute(String requestBody, String serviceUrl);
    }

    public class TokenPaymentStrategy implements PaymentStrategy {
        @Override
        public Map<String, Object> execute(String requestBody, String serviceUrl) {
            return paymentRequestService.processRequest(requestBody, serviceUrl);
        }
    }

    public class EcommPaymentStrategy implements PaymentStrategy {
        @Override
        public Map<String, Object> execute(String requestBody, String serviceUrl) {
            return paymentRequestService.processRequest(requestBody, serviceUrl);
        }
    }

    public String processPayment() {
        try {
            PaymentStrategy strategy;

            // Process token payment
            strategy = new TokenPaymentStrategy();
            Map<String, Object> tokenData = strategy.execute(paymentConfig.getPostDataToken(), paymentConfig.getServiceUrlToken());
            if (tokenData == null) {
                logger.error("Token data is null");
                return "redirect:/errorPage";
            }

            String transactionId = (String) tokenData.get("transactionId");

            // Process Ecomm payment
            strategy = new EcommPaymentStrategy();
            PaymentDTO paymentDTO = new PaymentDTO(paymentConfig.getMerchantId(), paymentConfig.getAmount(),
                    paymentConfig.getCurrency(), paymentConfig.getDescription(), paymentConfig.getApproveUrl(),
                    paymentConfig.getCancelUrl(), paymentConfig.getDeclineUrl(), paymentConfig.getUserName(),
                    paymentConfig.getPassWord(), transactionId);

            String postDataEcomm = paymentDTO.toJSON();
            Map<String, Object> ecommData = strategy.execute(postDataEcomm, paymentConfig.getServiceUrlEcomm());
            if (ecommData == null) {
                logger.error("Ecomm data is null");
                return "redirect:/errorPage";
            }

            Map<String, Object> items = (Map<String, Object>) ecommData.get("items");
            if (items != null) {
                return items.get("url") + "?ORDERID=" + items.get("orderId") + "&SESSIONID=" + items.get("sessionId");
            } else {
                logger.error("Items map is null");
                return "redirect:/errorPage";
            }
        } catch (Exception ex) {
            logger.error("Exception occurred: ", ex);
            return "redirect:/errorPage";
        }
    }
}
