package com.kakao.yebgi.server.model.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import static com.kakao.yebgi.server.constant.Constants.ID_SIZE;
import static com.kakao.yebgi.server.constant.Constants.PAYLOAD_SIZE;

@Data
@NoArgsConstructor
@Entity
public class SendPayload {
    @Id
    @Column(length = ID_SIZE)
    private String id;

    @Column(length = PAYLOAD_SIZE, nullable = false)
    private String body;

    public SendPayload(String id, String body) {
        this.id = id;
        this.body = body;
    }
}