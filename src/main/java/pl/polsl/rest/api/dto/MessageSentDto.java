package pl.polsl.rest.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MessageSentDto(@JsonProperty("sent") Boolean sent, @JsonProperty("messageId") Integer messageId,
                             @JsonProperty("messageSenderId") Integer messageSenderId,
                             @JsonProperty("messageReceiverId") Integer messageReceiverId,
                             @JsonProperty("messageText") String messageText) {
    public MessageSentDto(@JsonProperty("sent") final boolean sent) {
        this(sent, null, null, null, null);
    }
}
