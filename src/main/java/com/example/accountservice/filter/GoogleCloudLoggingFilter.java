package com.example.accountservice.filter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.*;

@Component
@Slf4j
public class GoogleCloudLoggingFilter extends OncePerRequestFilter {
        private final Logger logger = LogManager.getLogger(GoogleCloudLoggingFilter.class);
        private final Gson gson = new GsonBuilder().create();

        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                throws ServletException, IOException {
            ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request);
            ContentCachingResponseWrapper cachingResponseWrapper = new ContentCachingResponseWrapper(response);

            // Log request headers and body
            logRequest(request, cachingRequestWrapper);

            // Continue with the filter chain
            filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);

            // Log response headers and body
            logResponse(response, cachingResponseWrapper);

            cachingResponseWrapper.copyBodyToResponse();
        }

        private void logRequest(HttpServletRequest request, ContentCachingRequestWrapper cachingRequestWrapper) {
            Map<String, Object> logData = new LinkedHashMap<>();
            logData.put("request_method", request.getMethod());
            logData.put("request_url", request.getRequestURL().toString());
            logData.put("request_headers", extractHeaders(request));
            logData.put("request_body", getValueAsString(cachingRequestWrapper.getContentAsByteArray(), cachingRequestWrapper.getCharacterEncoding()));
            logger.info(gson.toJson(logData));
        }

        private void logResponse(HttpServletResponse response, ContentCachingResponseWrapper cachingResponseWrapper) {
            Map<String, Object> logData = new LinkedHashMap<>();
            logData.put("response_status", response.getStatus());
            logData.put("response_headers", extractHeaders(response));
            logData.put("response_body", getValueAsString(cachingResponseWrapper.getContentAsByteArray(), cachingResponseWrapper.getCharacterEncoding()));
            logger.info(gson.toJson(logData));
        }

        private Map<String, String> extractHeaders(HttpServletRequest request) {
            Map<String, String> headers = new HashMap<>();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                headers.put(headerName, headerValue);
            }
            return headers;
        }

        private Map<String, String> extractHeaders(HttpServletResponse response) {
            Map<String, String> headers = new HashMap<>();
            Collection<String> headerNames = response.getHeaderNames();
            for (String headerName : headerNames) {
                String headerValue = response.getHeader(headerName);
                headers.put(headerName, headerValue);
            }
            return headers;
        }

        private String getValueAsString(byte[] contentAsByteArray, String characterEncoding) {
            String dataAsString = "";
            try {
                dataAsString = new String(contentAsByteArray, characterEncoding);
            } catch (Exception e) {
                log.error("Exception occurred while converting byte into an array: {}", e.getMessage());
                e.printStackTrace();
            }
            return dataAsString;
        }
    }


