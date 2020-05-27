package com.kakao.yebgi.server.controller;

import com.kakao.yebgi.server.exception.ApiException;
import com.kakao.yebgi.server.exception.ApiParameterException;
import com.kakao.yebgi.server.request.payment.ApplyPaymentRequest;
import com.kakao.yebgi.server.request.payment.CancelPaymentRequest;
import com.kakao.yebgi.server.request.payment.SearchPaymentRequest;
import com.kakao.yebgi.server.service.payment.ApplyPaymentService;
import com.kakao.yebgi.server.service.payment.CancelPaymentService;
import com.kakao.yebgi.server.service.payment.SearchPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = {"api/payment/v1"})
public class PaymentController {
    @Autowired
    private ApplyPaymentService applyPaymentService;

    @Autowired
    private CancelPaymentService cancelPaymentService;

    @Autowired
    private SearchPaymentService searchPaymentService;

    @PostMapping(value = "/apply")
    public Object doApply(@RequestBody @Valid ApplyPaymentRequest request, BindingResult bindingResult) throws ApiException {
        if (bindingResult.hasErrors()) {
            throw new ApiParameterException(bindingResult);
        } else {
            return applyPaymentService.doWork(request);
        }
    }

    @PostMapping(value = "/cancel")
    public Object doCancel(@RequestBody @Valid CancelPaymentRequest request, BindingResult bindingResult) throws ApiException {
        if (bindingResult.hasErrors()) {
            throw new ApiParameterException(bindingResult);
        } else {
            return cancelPaymentService.doWork(request);
        }
    }

    @PostMapping(value = "/search")
    public Object doSearch(@RequestBody @Valid SearchPaymentRequest request, BindingResult bindingResult) throws ApiException {
        if (bindingResult.hasErrors()) {
            throw new ApiParameterException(bindingResult);
        } else {
            return searchPaymentService.doWork(request);
        }
    }
}