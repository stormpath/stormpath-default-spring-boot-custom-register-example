package com.stormpath.examples.config;

import com.stormpath.examples.util.EmailValidator;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.servlet.mvc.ValidationException;
import com.stormpath.sdk.servlet.mvc.WebHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class RegisterHandlerConfig {

    @Bean
    public WebHandler registerPreHandler() {
        return new WebHandler() {
            @Override
            public boolean handle(HttpServletRequest req, HttpServletResponse res, Account account) {
                String password = req.getParameter("password");
                if (EmailValidator.containsEmailAddress(password)) {
                    throw new ValidationException("Password cannot contain an email address.");
                }

                return true;
            }
        };
    }
}
