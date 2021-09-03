package com.cinema.app.infrastructure.routing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

/**
 * Data transfer object used to communication through api
 * @author Szymon Sawicki
 */

public class ResponseDto<T> {

    /**
     * data to be transported
     */

    private T data;

    /**
     * error message generated when something goes wrong
     */

    @Builder.Default
    private String error = "";

    public static <T> ResponseDto<T> toResponse(T data) { return ResponseDto.<T>builder().data(data).build();}

    public static ResponseDto<?> toError(String message) { return ResponseDto.builder().error(message).build(); }
}
