package pl.polsl.rest.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDataDto(
        @JsonProperty("userId") int userId,
        @JsonProperty("userName") String userName
) {
}
