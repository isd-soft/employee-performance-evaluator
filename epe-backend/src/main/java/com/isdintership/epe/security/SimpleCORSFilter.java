package com.isdintership.epe.security;

import com.isdintership.epe.config.AppConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class SimpleCORSFilter implements Filter {

    private final AppConfig appConfig;

    ArrayList<String> AllowedOrigins = new ArrayList<>();
    ArrayList<String> AllowedMethods = new ArrayList<>();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        AllowedOrigins.add(appConfig.getFrontendUrl());
        AllowedOrigins.add(appConfig.getFrontendHttpUrl());
        AllowedMethods.addAll(Arrays.asList("POST", "PUT", "GET", "DELETE", "OPTIONS"));

        String reqOrigin;
        reqOrigin = req.getHeader("Origin");

        for (String origin : AllowedOrigins) {
            if (origin.equals(reqOrigin)) {
                res.setHeader("Access-Control-Allow-Origin", reqOrigin);
                break;
            }
        }

        if (req.getMethod().equals("OPTIONS")) {


            String reqMethod;
            reqMethod = req.getHeader("Access-Control-Request-Method");

            for (String method : AllowedMethods) {
                if (method.equals(reqMethod)) {
                    res.setHeader("Access-Control-Allow-Methods", reqMethod);
                }
            }

            res.setHeader("Access-Control-Max-Age", "3600");
            res.setHeader("Access-Control-Allow-Credentials", "true");
            res.setHeader("Access-Control-Allow-Headers",
                    "cache-control, if-modified-since, pragma, Content-Type, Authorization, "
                            + "Access-Control-Allow-Headers, X-Requested-With, Expires");


            //Checks if Allow-Method and Allow-Origin got set. 200 OK if both are set.
            if (!res.getHeader("Access-Control-Allow-Methods").equals("") && !res.getHeader("Access-Control-Allow-Origin").equals("")) {
                res.setStatus(200);
            }
        } else {
            filterChain.doFilter(req, res);
        }
    }
}