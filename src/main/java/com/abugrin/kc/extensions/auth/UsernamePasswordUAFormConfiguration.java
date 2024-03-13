package com.abugrin.kc.extensions.auth;

import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;

import java.util.List;

public interface UsernamePasswordUAFormConfiguration {

    String USER_AGENT_NAME = "user_agent_name";
    String USER_AGENT_VERSION = "user_agent_version";

    List<ProviderConfigProperty> PROPS = ProviderConfigurationBuilder.create()

            .property()
            .name(USER_AGENT_NAME)
            .type(ProviderConfigProperty.STRING_TYPE)
            .label("User Agent name")
            .helpText("User agent name validation")
            .add()

            .property()
            .name(USER_AGENT_VERSION)
            .type(ProviderConfigProperty.STRING_TYPE)
            .label("User Agent version")
            .helpText("User agent version validation")
            .add()

            .build();

}