package com.kakao.yebgi.server.entity.payment;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import static com.kakao.yebgi.server.constant.Constants.ID_SIZE;

@Data
@Entity
@DiscriminatorValue("CANCEL")
public class CancelPayment extends Payment {
    @Column(length = ID_SIZE)
    private String paymentId;
}