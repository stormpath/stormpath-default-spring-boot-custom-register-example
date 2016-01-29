/*
 * Copyright 2016 Stormpath, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stormpath.examples.controller;

import com.stormpath.examples.util.EmailValidator;
import com.stormpath.sdk.servlet.form.Form;
import com.stormpath.sdk.servlet.mvc.RegisterController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class CustomRegisterController extends RegisterController {
    EmailValidator emailValidator = new EmailValidator();

    protected void validate(HttpServletRequest request, HttpServletResponse response, Form form) {
        String password = form.getFieldValue("password");
        boolean matchesEmailAddress = EmailValidator.containsEmailAddress(password);
        if (matchesEmailAddress) {
            String msg = "Password cannot contain an email address.";
            throw new PasswordContainsEmailException(msg);
        }

        super.validate(request, response, form);
    }

    protected static class PasswordContainsEmailException extends RuntimeException {
        public PasswordContainsEmailException(String msg) {
            super(msg);
        }
    }

    @Override
    protected List<String> toErrors(HttpServletRequest request, Form form, Exception e) {
        List<String> errors = new ArrayList<String>();

        if (e instanceof PasswordContainsEmailException) {
            errors.add(e.getMessage());
        } else {
            errors = super.toErrors(request, form, e);
        }

        return errors;
    }
}