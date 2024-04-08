package com.boot.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class MaxSessionsAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof SessionAuthenticationException) {
            // Maximum sessions limit reached, take appropriate action
            // For example, redirect to a login error page with a message
            getRedirectStrategy().sendRedirect(request, response, "/login?error=maxSessions");
        } else {
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}

