package io.locngo.support.portal.domain;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class HttpResponse {
    private final int httpStatusCode;
    private final HttpStatus status;
    private final String reason;
    private final String message;
    private final LocalDateTime timestamp = LocalDateTime.now();
}
