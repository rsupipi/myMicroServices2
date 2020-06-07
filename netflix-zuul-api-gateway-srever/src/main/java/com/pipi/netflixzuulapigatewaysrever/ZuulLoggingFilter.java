package com.pipi.netflixzuulapigatewaysrever;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ZuulLoggingFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public int filterOrder() {
        // If we have multiple filter we can set priority.
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        // This filter should be executed or not
        // Here we can implement business logic and check whether execut  ed this or not
        return true;
    }

    @Override
    public String filterType() {
        // When should the filtering happening, before executed or after the execution.
//        return "error";
        return "pre";
    }

    @Override
    public Object run() throws ZuulException {
        // Real logic of the interception goes.
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        logger.info("request -> {} requst uri -> {}", request, request.getRequestURI());
        return null;
    }
}
