package io.locngo.support.portal.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.locngo.support.portal.constant.SecurityConstant;
import io.locngo.support.portal.domain.HttpResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(
        HttpServletRequest request, 
        HttpServletResponse response,
        AccessDeniedException accessDeniedException
    ) throws IOException, ServletException {
        log.error(accessDeniedException.getLocalizedMessage());

        final HttpResponse httpResponse = HttpResponse.of(
            HttpStatus.FORBIDDEN.value(), 
            HttpStatus.FORBIDDEN, 
            HttpStatus.FORBIDDEN.getReasonPhrase().toUpperCase(), 
            SecurityConstant.FOBBIDEN_MESSAGE
        );
        
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(
            this.objectMapper.writeValueAsString(httpResponse)
        );
    }
    
}
