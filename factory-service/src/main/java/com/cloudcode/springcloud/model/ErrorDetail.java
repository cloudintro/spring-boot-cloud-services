package com.cloudcode.springcloud.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class ErrorDetail {
    private Integer status;
    private String error;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();
}
