package com.kakao.yebgi.server.repository;

import com.kakao.yebgi.server.model.entity.SendPayload;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendPayloadRepository extends JpaRepository<SendPayload, String> {
}