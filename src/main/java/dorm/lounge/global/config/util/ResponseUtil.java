package dorm.lounge.global.config.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import dorm.lounge.global.ApiResponse;
import dorm.lounge.global.status.ErrorStatus;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ResponseUtil {

    private final ObjectMapper objectMapper;

    public void writeErrorResponse(HttpServletResponse response, ErrorStatus errorStatus, HttpStatus httpStatus) throws IOException {
        ApiResponse<Object> errorResponse = ApiResponse.onFailure(
                errorStatus.getReason().getCode(),
                errorStatus.getReason().getMessage(),
                "");
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
