package pl.polsl.rest.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MessageDto(
        @JsonProperty("messageId") Integer messageId,
        @JsonProperty("messageSenderId") Integer messageSenderId,
        @JsonProperty("messageReceiverId") Integer messageReceiverId,
        @JsonProperty("messageText") String messageText
) {
    public MessageDto(@JsonProperty("messageSenderId") Integer messageSenderId,
                      @JsonProperty("messageReceiverId") Integer messageReceiverId,
                      @JsonProperty("messageText") String messageText) {
        this(null, messageSenderId, messageReceiverId, messageText);
    }
}
