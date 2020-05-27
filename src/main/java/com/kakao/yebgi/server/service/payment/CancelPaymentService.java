package com.kakao.yebgi.server.service.payment;

import com.kakao.yebgi.server.card.model.CardInfo;
import com.kakao.yebgi.server.constant.ApiError;
import com.kakao.yebgi.server.constant.Constants;
import com.kakao.yebgi.server.constant.PaymentType;
import com.kakao.yebgi.server.entity.payment.ApplyPayment;
import com.kakao.yebgi.server.entity.payment.CancelPayment;
import com.kakao.yebgi.server.exception.ApiException;
import com.kakao.yebgi.server.payload.Payload;
import com.kakao.yebgi.server.repository.PaymentRepository;
import com.kakao.yebgi.server.request.payment.CancelPaymentRequest;
import com.kakao.yebgi.server.response.payment.CancelPaymentResponse;
import com.kakao.yebgi.server.service.CardService;
import com.kakao.yebgi.server.service.SendService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.support.locks.LockRegistry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;

@Service
public class CancelPaymentService {
    @Autowired
    @Qualifier(Constants.PAYLOAD_ID)
    private Callable<String> randomId;

    @Autowired
    private LockRegistry lockRegistry;

    @Autowired
    private CardService cardService;

    @Autowired
    private SendService sendService;

    @Resource
    private PaymentRepository<ApplyPayment> applyPaymentRepository;

    @Transactional
    public CancelPaymentResponse doWork(CancelPaymentRequest request) throws ApiException {
        ApplyPayment applyPayment = applyPaymentRepository
                .findById(request.getPaymentId())
                .orElseThrow(() -> new ApiException(ApiError.PAYMENT_NOT_FOUND));

        ValidResult validResult = validate(applyPayment, request);

        CardInfo cardInfo = cardService.decrypt(applyPayment.getEncryptedCardInfo());

        Lock lock = lockPayment(applyPayment);

        try {
            long requireVat = validResult.getRequireVat();

            CancelPayment cancelPayment = new CancelPayment();
            cancelPayment.setId(randomId.call());
            cancelPayment.setPrice(request.getPrice());
            cancelPayment.setVat(requireVat);
            cancelPayment.setEncryptedCardInfo(applyPayment.getEncryptedCardInfo());

            applyPayment.addCancelPayment(cancelPayment);
            applyPaymentRepository.save(applyPayment);

            sendService.send(
                    Payload
                            .builder()
                            .id(cancelPayment.getId())
                            .type(PaymentType.CANCEL)
                            .paymentId(request.getPaymentId())
                            .paymentMonths(0)
                            .paymentPrice(request.getPrice())
                            .encryptedCardInfo(applyPayment.getEncryptedCardInfo())
                            .vat(requireVat)
                            .cardNumber(cardInfo.getNumber())
                            .cardExpiryDate(cardInfo.getExpiryDate())
                            .cardVerificationCode(cardInfo.getVerificationCode())
                            .build()
            );

            return CancelPaymentResponse
                    .builder()
                    .id(cancelPayment.getId())
                    .build();
        } catch (Exception e) {
            throw new ApiException(ApiError.ERROR, e.getLocalizedMessage());
        } finally {
            lock.unlock();
        }
    }

    private Lock lockPayment(ApplyPayment applyPayment) throws ApiException {
        String key = applyPayment.getId();
        Lock lock = lockRegistry.obtain(key);

        if (lock.tryLock()) {
            return lock;
        } else {
            throw new ApiException(ApiError.PAYMENT_LOCKED);
        }
    }

    private ValidResult validate(ApplyPayment applyPayment, CancelPaymentRequest request) throws ApiException {
        long remainingPrice = applyPayment.getRemainingPrice();
        long remainingVat = applyPayment.getRemainingVat();

        long requirePrice = request.getPrice();
        long requireVat = Optional
                .ofNullable(request.getVat())
                .orElse(Math.min(remainingVat, request.getDefaultVat()));

        if (requirePrice > remainingPrice) {
            throw new ApiException(ApiError.NOT_ENOUGH_PRICE);
        }

        if (requireVat > remainingVat) {
            throw new ApiException(ApiError.NOT_ENOUGH_VAT);
        }

        if (remainingPrice - requirePrice < remainingVat - requireVat) {
            throw new ApiException(ApiError.VAT_GREATER_THAN_PRICE);
        }

        return new ValidResult(requireVat);
    }

    @Data
    private static class ValidResult {
        private long requireVat;

        ValidResult(long requireVat) {
            this.requireVat = requireVat;
        }
    }
}