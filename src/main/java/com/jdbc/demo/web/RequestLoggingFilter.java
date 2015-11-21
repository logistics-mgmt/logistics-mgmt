package com.jdbc.demo.web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Mateusz on 14-Nov-15.
 */

@WebFilter("/RequestLoggingFilter")
public class RequestLoggingFilter implements Filter {

    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        context.log(String.format("REQUEST: \n %s", servletRequest.toString()));
        if(servletRequest.getParameterMap().size()>0)
        {
            context.log("Request parameters:");
            for (Map.Entry<String, String[]> entry : servletRequest.getParameterMap().entrySet()) {
                context.log(String.format("\t%s:", entry.getKey()));
                for ( String val : entry.getValue()){
                    context.log(String.format("\t\t%s", val));
                }
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
