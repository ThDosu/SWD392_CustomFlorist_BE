package edu.fpt.customflorist.services.Payment;

import edu.fpt.customflorist.components.RandomStringGenerator;
import edu.fpt.customflorist.components.VnpayUtil;
import edu.fpt.customflorist.configurations.VnpayConfig;
import edu.fpt.customflorist.dtos.Payment.PaymentDTO;
import edu.fpt.customflorist.exceptions.DataNotFoundException;
import edu.fpt.customflorist.models.Enums.PaymentMethod;
import edu.fpt.customflorist.models.Enums.PaymentStatus;
import edu.fpt.customflorist.models.Enums.Status;
import edu.fpt.customflorist.models.Order;
import edu.fpt.customflorist.models.Payment;
import edu.fpt.customflorist.repositories.OrderRepository;
import edu.fpt.customflorist.repositories.PaymentRepository;
import edu.fpt.customflorist.responses.Payment.VnpayResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService{
    private final VnpayConfig vnPayConfig;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final RandomStringGenerator randomStringGenerator;

    @Override
    public String createVnPayPayment(HttpServletRequest request, PaymentDTO paymentDTO) throws DataNotFoundException {
        long amount = (int)paymentDTO.getFinalAmount() * 100L;
        String bankCode = paymentDTO.getBankCode();
        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
        vnpParamsMap.put("vnp_OrderInfo", paymentDTO.getOrderId() + "");
        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }
        vnpParamsMap.put("vnp_IpAddr", VnpayUtil.getIpAddress(request));
        //build query url
        String queryUrl = VnpayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VnpayUtil.getPaymentURL(vnpParamsMap, false);
        String vnpSecureHash = VnpayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        if (!paymentRepository.existsByOrderId(paymentDTO.getOrderId())){
            Payment payment = new Payment();
            Order order = orderRepository.findById(paymentDTO.getOrderId()).orElseThrow(()-> new DataNotFoundException("order not found"));
            payment.setOrder(order);
            payment.setAmount(BigDecimal.valueOf(paymentDTO.getFinalAmount()));
            payment.setPaymentMethod(PaymentMethod.VNPAY);
            payment.setPaymentDate(LocalDateTime.now());
            payment.setStatus(PaymentStatus.PENDING);
            payment.setIsActive(true);
            payment.setTransactionCode(randomStringGenerator.generateRandomString(20));

            paymentRepository.save(payment);
        }

        return paymentUrl;
    }

    @Override
    public void updatePayment(Long orderId, String statusPayment) throws DataNotFoundException {
        Order order = orderRepository.findById(orderId).orElseThrow(()-> new DataNotFoundException("order not found"));
        order.setStatus(Status.PROCESSING);
        orderRepository.save(order);

        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new DataNotFoundException("payment not found for orderId: " + orderId));
        payment.setStatus(PaymentStatus.valueOf(statusPayment));
        paymentRepository.save(payment);
    }
}
