package com.currency.exchange.Utill;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class RequestUtil {

    public static PrintWriter getWriter(HttpServletResponse response) throws IOException {
        return response.getWriter();
    }
}
