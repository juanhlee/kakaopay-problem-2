package com.kakao.yebgi.server.repository;

import com.kakao.yebgi.server.entity.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository<T extends Payment> extends JpaRepository<T, String> {
}