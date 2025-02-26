package edu.fpt.customflorist.services.Payment;

import edu.fpt.customflorist.components.VnpayUtil;
import edu.fpt.customflorist.configurations.VnpayConfig;
import edu.fpt.customflorist.dtos.Payment.PaymentDTO;
import edu.fpt.customflorist.exceptions.DataNotFoundException;
import edu.fpt.customflorist.responses.Payment.VnpayResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService{
    private final VnpayConfig vnPayConfig;

    @Override
    public VnpayResponse createVnPayPayment(HttpServletRequest request, PaymentDTO paymentDTO) throws DataNotFoundException {

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
        return VnpayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl)
                .build();
    }
}
