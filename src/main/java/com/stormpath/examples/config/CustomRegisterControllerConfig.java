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
package com.stormpath.examples.config;

import com.stormpath.examples.controller.CustomRegisterController;
import com.stormpath.sdk.lang.Assert;
import com.stormpath.sdk.servlet.form.DefaultField;
import com.stormpath.sdk.servlet.form.Field;
import com.stormpath.spring.config.AbstractStormpathWebMvcConfiguration;
import com.stormpath.spring.mvc.SpringController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.Controller;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CustomRegisterControllerConfig extends AbstractStormpathWebMvcConfiguration {

    @Bean
    public Controller stormpathRegisterController() {
        if (idSiteEnabled) {
            return createIdSiteController(idSiteRegisterUri);
        }

        //otherwise standard registration:
        CustomRegisterController controller = new CustomRegisterController();
        controller.setCsrfTokenManager(stormpathCsrfTokenManager());
        controller.setClient(client);
        controller.setEventPublisher(stormpathRequestEventPublisher());
        controller.setFormFields(toDefaultFields(stormpathRegisterFormFields()));
        controller.setLocaleResolver(stormpathLocaleResolver());
        controller.setMessageSource(stormpathMessageSource());
        controller.setAuthenticationResultSaver(stormpathAuthenticationResultSaver());
        controller.setUri(registerUri);
        controller.setView(registerView);
        controller.setNextUri(registerNextUri);
        controller.setLoginUri(loginUri);
        controller.setVerifyViewName(verifyView);
        controller.init();

        SpringController springController = new SpringController(controller);
        if (urlPathHelper != null) {
            springController.setUrlPathHelper(urlPathHelper);
        }
        return springController;
    }

    private List<DefaultField> toDefaultFields(List<Field> fields) {
        List<DefaultField> defaultFields = new ArrayList<DefaultField>(fields.size());
        for (Field field : fields) {
            Assert.isInstanceOf(DefaultField.class, field);
            defaultFields.add((DefaultField) field);
        }

        return defaultFields;
    }
}
