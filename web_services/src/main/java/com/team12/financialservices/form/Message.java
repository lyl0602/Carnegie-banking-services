package com.team12.financialservices.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Message {
    private String message;

    public Message(String message) {
        this.message = message;
    }
}
