package com.profITsoft.block2.messaging;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class SendMailMessage {

    private String subject;

    private String text;

    private String recipient;

}
