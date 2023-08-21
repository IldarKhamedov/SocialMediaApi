package ru.khamedov.ildar.socialMedia.filter;

import jakarta.servlet.*;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;

public class TransactionFilter implements Filter {

    @Transactional
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
