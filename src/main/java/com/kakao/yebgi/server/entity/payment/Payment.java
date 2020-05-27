package com.kakao.yebgi.server.entity.payment;

import com.kakao.yebgi.server.constant.PaymentType;
import lombok.Data;

import javax.persistence.*;

import static com.kakao.yebgi.server.constant.Constants.ID_SIZE;

@Data
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
@Entity
public class Payment {
    @Id
    @Column(length = ID_SIZE)
    private String id;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long vat;

    @Column
    private String encryptedCardInfo;

    public PaymentType getType() {
        return PaymentType.valueOf(
                getClass()
                        .getAnnotation(DiscriminatorValue.class)
                        .value()
        );
    }
}