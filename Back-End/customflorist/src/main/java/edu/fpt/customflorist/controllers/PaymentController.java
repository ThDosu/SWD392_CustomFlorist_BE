package edu.fpt.customflorist.controllers;

import edu.fpt.customflorist.dtos.Payment.PaymentDTO;
import edu.fpt.customflorist.exceptions.DataNotFoundException;
import edu.fpt.customflorist.responses.Payment.VnpayResponse;
import edu.fpt.customflorist.responses.ResponseObject;
import edu.fpt.customflorist.services.Payment.IPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/api/v1/payment")
public class PaymentController {
    private final IPaymentService paymentService;

    @PostMapping("/vn-pay")
    public ResponseEntity<?> pay(
            HttpServletRequest request,
            @Valid @RequestBody PaymentDTO paymentDTO,
            BindingResult result
    ) throws DataNotFoundException {

        String vnpayResponse = paymentService.createVnPayPayment(request, paymentDTO);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Payment page")
                .status(HttpStatus.OK)
                .data(vnpayResponse)
                .build());
    }

    @GetMapping("/vn-pay-callback")
    public RedirectView payCallbackHandler(HttpServletRequest request) {
        try{
            String status = request.getParameter("vnp_ResponseCode");
            String vnpOrderInfo = request.getParameter("vnp_OrderInfo");
            Long orderId = Long.parseLong(vnpOrderInfo);

            RedirectView redirectView = new RedirectView();

            if (status.equals("00")) {
                if(orderId != 0){
                    paymentService.updatePayment(orderId, "COMPLETED");

                    redirectView = new RedirectView("/payment-success.html");
                    redirectView.addStaticAttribute("orderId", orderId);
                }
            } else {
                if(orderId != 0 ){
                    paymentService.updatePayment(orderId, "FAILED");

                    redirectView = new RedirectView("/payment-fail.html");
                    redirectView.addStaticAttribute("orderId", orderId);
                }
            }
            return redirectView;
        }catch (Exception e){
            return  new RedirectView("/error.html");
        }
    }
}
