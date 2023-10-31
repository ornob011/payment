package com.dsi.rjsc.payment.controller;

import com.dsi.rjsc.payment.dto.PaymentDTO;
import com.dsi.rjsc.payment.service.PaymentRequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class PaymentController {

    @Autowired
    private PaymentRequestService paymentRequestService;

    @Value("${service.url.token}")
    private String serviceUrlToken;

    @Value("${post.data.token}")
    private String postDataToken;

    @Value("${service.url.ecomm}")
    private String serviceUrlEcomm;

    @Value("${payment.merchantId}")
    private String merchantId;

    @Value("${payment.amount}")
    private String amount;

    @Value("${payment.currency}")
    private String currency;

    @Value("${payment.description}")
    private String description;

    @Value("${payment.approveUrl}")
    private String approveUrl;

    @Value("${payment.cancelUrl}")
    private String cancelUrl;

    @Value("${payment.declineUrl}")
    private String declineUrl;

    @Value("${payment.userName}")
    private String userName;

    @Value("${payment.passWord}")
    private String passWord;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public String index(Model model) {
        try {
            Map<String, Object> tokenData = paymentRequestService.processRequest(postDataToken, serviceUrlToken);
            if (tokenData == null) {
                logger.error(":: ERROR:: Token data is null");
                return "redirect:/errorPage";
            }

            String transactionId = (String) tokenData.get("transactionId");
            PaymentDTO paymentDTO = new PaymentDTO(merchantId, amount, currency, description, approveUrl, cancelUrl, declineUrl, userName, passWord, transactionId);
            String postDataEcomm = paymentDTO.toJSON();

            Map<String, Object> ecommData = paymentRequestService.processRequest(postDataEcomm, serviceUrlEcomm);
            if (ecommData == null) {
                logger.error(":: ERROR:: Ecomm data is null");
                return "redirect:/errorPage";
            }

            Map<String, Object> items = (Map<String, Object>) ecommData.get("items");
            if (items != null) {
                String redirectUrl = items.get("url") + "?ORDERID=" + items.get("orderId") + "&SESSIONID=" + items.get("sessionId");
                model.addAttribute("redirectUrl", redirectUrl);
                return "redirect";
            } else {
                logger.error(":: ERROR:: Items map is null");
                return "redirect:/errorPage";
            }
        } catch (Exception ex) {
            logger.error(":: ERROR:: Exception occurred :: ", ex);
            return "redirect:/errorPage";
        }
    }
}
