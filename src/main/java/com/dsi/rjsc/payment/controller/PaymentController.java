package com.dsi.rjsc.payment.controller;

import com.dsi.rjsc.payment.dto.PaymentDTO;
import com.dsi.rjsc.payment.service.PaymentRequestService;
import com.dsi.rjsc.payment.service.PaymentService;
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
    private PaymentService paymentService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public String index(Model model) {
        try {
            String redirectUrl = paymentService.processPayment();
            model.addAttribute("redirectUrl", redirectUrl);
            return "redirect";
        } catch (Exception ex) {
            logger.error(":: ERROR:: Exception occurred :: ", ex);
            return "redirect:/errorPage";
        }
    }
}
