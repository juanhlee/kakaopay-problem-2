package com.kakao.yebgi.server.service.payment;

import com.kakao.yebgi.server.card.model.CardInfo;
import com.kakao.yebgi.server.card.model.MaskedCardInfo;
import com.kakao.yebgi.server.constant.ApiError;
import com.kakao.yebgi.server.entity.payment.ApplyPayment;
import com.kakao.yebgi.server.entity.payment.Payment;
import com.kakao.yebgi.server.exception.ApiException;
import com.kakao.yebgi.server.repository.PaymentRepository;
import com.kakao.yebgi.server.request.payment.SearchPaymentRequest;
import com.kakao.yebgi.server.response.payment.SearchPaymentResponse;
import com.kakao.yebgi.server.service.CardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class SearchPaymentService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CardService cardService;

    @Resource
    private PaymentRepository<Payment> paymentRepository;

    private ResponseFactory responseFactory;

    @PostConstruct
    void init() {
        responseFactory = new ResponseFactory();
    }

    public SearchPaymentResponse doWork(SearchPaymentRequest request) throws ApiException {
        Payment payment = paymentRepository
                .findById(request.getId())
                .orElseThrow(() -> new ApiException(ApiError.PAYMENT_NOT_FOUND));

        CardInfo cardInfo = cardService.decrypt(payment.getEncryptedCardInfo());

        if (payment instanceof ApplyPayment) {
            return responseFactory.ofApplyPayment((ApplyPayment) payment, cardInfo);
        } else {
            return responseFactory.ofDefault(payment, cardInfo);
        }
    }

    private class ResponseFactory {
        SearchPaymentResponse ofApplyPayment(ApplyPayment payment, CardInfo cardInfo) {
            return SearchPaymentResponse
                    .builder()
                    .id(payment.getId())
                    .cardInfo(modelMapper.map(cardInfo, MaskedCardInfo.class))
                    .paymentType(payment.getType())
                    .price(payment.getRemainingPrice())
                    .vat(payment.getRemainingVat())
                    .build();
        }

        SearchPaymentResponse ofDefault(Payment payment, CardInfo cardInfo) {
            return SearchPaymentResponse
                    .builder()
                    .id(payment.getId())
                    .cardInfo(modelMapper.map(cardInfo, MaskedCardInfo.class))
                    .paymentType(payment.getType())
                    .price(payment.getPrice())
                    .vat(payment.getVat())
                    .build();
        }
    }
}