package pl.polsl.rest.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_EMPTY)
public record LoggedInDto(
        @JsonProperty("loggedIn") boolean isLoggedIn,
        @JsonProperty("userName") String userName,
        @JsonProperty("userId") Integer userId
) {
    public LoggedInDto(@JsonProperty("loggedIn") final boolean isLoggedIn) {
        this(isLoggedIn, null, null);
    }
}
