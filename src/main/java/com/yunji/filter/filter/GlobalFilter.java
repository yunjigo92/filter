package com.yunji.filter.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/api/temp/*")
public class GlobalFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        //전처리
        ContentCachingRequestWrapper httpServletRequest = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper httpServletResponse = new ContentCachingResponseWrapper((HttpServletResponse) response);


        chain.doFilter(httpServletRequest,httpServletResponse);

        String url = httpServletRequest.getRequestURI();


        //후처리
        String requestContent = new String(httpServletRequest.getContentAsByteArray());

        log.info("Request body:{}" , requestContent);

        String responseContent =  new String(httpServletResponse.getContentAsByteArray());
        int httpStatusCode = httpServletResponse.getStatus();

        httpServletResponse.copyBodyToResponse();

        log.info("Response status : {}, response body :{}",httpStatusCode, responseContent);
    }
}
