package com.abugrin.kc.extensions.forms;

import jakarta.ws.rs.core.MultivaluedMap;
import org.jboss.logging.Logger;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormContext;
import org.keycloak.authentication.ValidationContext;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class CustomFormAction implements FormAction {

    private static final Logger logger = Logger.getLogger(CustomFormAction.class);

    @Override
    public void buildPage(FormContext context, LoginFormsProvider form) {
        logger.info("Building page");
        MultivaluedMap<String, String> map = context.getHttpRequest().getHttpHeaders().getRequestHeaders();
        map.forEach((k, l) -> l.forEach(v -> logger.info("Header: k: " + k + " v: " + v)));

    }

    @Override
    public void validate(ValidationContext context) {
        logger.info("Validating");
        MultivaluedMap<String, String> map = context.getHttpRequest().getHttpHeaders().getRequestHeaders();
        map.forEach((k, l) -> l.forEach(v -> logger.info("Header: k: " + k + " v: " + v)));
        //context.
        context.success();
    }

    @Override
    public void success(FormContext context) {
        UserModel user = context.getUser();
        if (user != null) {
            // Do something useful with the user here ...
        }
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession keycloakSession, RealmModel realmModel, UserModel userModel) {

    }

    @Override
    public void close() {

    }
}
