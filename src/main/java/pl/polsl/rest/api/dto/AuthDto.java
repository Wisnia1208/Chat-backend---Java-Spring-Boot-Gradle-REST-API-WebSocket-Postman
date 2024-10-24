package pl.polsl.rest.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthDto(
        @JsonProperty("userName") String userName,
        @JsonProperty("userPassword") String userPassword
) {
}
