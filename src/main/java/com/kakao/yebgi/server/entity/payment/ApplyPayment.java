package com.kakao.yebgi.server.entity.payment;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@DiscriminatorValue("PAYMENT")
public class ApplyPayment extends Payment {
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "paymentId")
    private Collection<CancelPayment> cancelPayments;

    public ApplyPayment() {
        this.cancelPayments = new ArrayList<>();
    }

    public boolean addCancelPayment(CancelPayment cancelPayment) {
        return cancelPayments.add(cancelPayment);
    }

    public Long getRemainingPrice() {
        Long canceledTotalPrice = cancelPayments
                .stream()
                .map(Payment::getPrice)
                .reduce(Long::sum)
                .orElse(0L);

        return getPrice() - canceledTotalPrice;
    }

    public Long getRemainingVat() {
        Long canceledTotalVat = cancelPayments
                .stream()
                .map(Payment::getVat)
                .reduce(Long::sum)
                .orElse(0L);

        return getVat() - canceledTotalVat;
    }
}