package com.currency.exchange.Utill;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


public class RequestUtil {

    public static PrintWriter getWriter(HttpServletResponse response) throws IOException {
        return response.getWriter();
    }

    public static <T> T fromJson(HttpServletRequest request, ObjectMapper objectMapper, Class<T> type) throws IOException {
        return objectMapper.readValue(request.getReader(), type);
    }

}
