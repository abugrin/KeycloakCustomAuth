package com.abugrin.kc.extensions.forms;

import org.keycloak.Config;
import org.keycloak.authentication.FormAction;
import org.keycloak.authentication.FormActionFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.List;

public class CustomFormActionFactory implements FormActionFactory {
    public static final String PROVIDER_ID = "custom-form-action";
    private static final AuthenticationExecutionModel.Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.DISABLED
    };
    @Override
    public String getDisplayType() {
        return "custom-display-type";
    }

    @Override
    public String getReferenceCategory() {
        return "custom-category";
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public AuthenticationExecutionModel.Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return true;
    }

    @Override
    public String getHelpText() {
        return "This is custom form";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {

        return null;
    }

    @Override
    public FormAction create(KeycloakSession keycloakSession) {
        return new CustomFormAction();
    }

    @Override
    public void init(Config.Scope scope) {


    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {

    }

    @Override
    public void close() {

    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }
}
