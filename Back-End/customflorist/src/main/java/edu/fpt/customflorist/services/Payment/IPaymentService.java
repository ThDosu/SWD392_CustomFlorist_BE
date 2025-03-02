package edu.fpt.customflorist.services.Payment;

import edu.fpt.customflorist.dtos.Payment.PaymentDTO;
import edu.fpt.customflorist.exceptions.DataNotFoundException;
import edu.fpt.customflorist.responses.Payment.VnpayResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface IPaymentService {
    VnpayResponse createVnPayPayment(HttpServletRequest request, PaymentDTO paymentDTO) throws DataNotFoundException;
    void updatePayment(Long orderId) throws DataNotFoundException;
}
