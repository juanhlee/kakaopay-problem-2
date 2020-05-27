package com.kakao.yebgi.server.service;

import com.kakao.yebgi.server.model.entity.SendPayload;
import com.kakao.yebgi.server.payload.Payload;
import com.kakao.yebgi.server.payload.PayloadSerializer;
import com.kakao.yebgi.server.repository.SendPayloadRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SendService {
    @Resource
    private SendPayloadRepository sendPayloadRepository;

    public SendPayload send(Payload payload) {
        return sendPayloadRepository.save(
                new SendPayload(
                        payload.getId(),
                        PayloadSerializer.serialize(payload)
                )
        );
    }
}