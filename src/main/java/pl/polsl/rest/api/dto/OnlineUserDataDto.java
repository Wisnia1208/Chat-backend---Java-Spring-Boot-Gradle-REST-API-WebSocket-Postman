package pl.polsl.rest.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OnlineUserDataDto(
        @JsonProperty("userId") int userId,
        @JsonProperty("userName") String userName,
        @JsonProperty("userIsOnline") Boolean userIsOnline
) {
    public OnlineUserDataDto(@JsonProperty("userId") int userId,
                             @JsonProperty("userName") String userName) {
        this(userId, userName, null);
    }
}
