package pl.polsl.rest.api.dto;

import java.util.List;

public record DataDto<T>(List<T> data) {

}