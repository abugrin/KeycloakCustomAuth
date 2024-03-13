package com.abugrin.kc.extensions.policy;

import org.keycloak.Config;
import org.keycloak.authorization.AuthorizationProvider;
import org.keycloak.authorization.model.Policy;
import org.keycloak.authorization.policy.provider.PolicyProvider;
import org.keycloak.authorization.policy.provider.PolicyProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;

public class CustomPolicyProviderFactory
        implements PolicyProviderFactory<CustomPolicyRepresentation> {
    private final CustomPolicyProvider provider = new CustomPolicyProvider();

    @Override
    public String getId() { return "customPolicy"; }

    @Override
    public String getName() {
        return "Custom Policy";
    }

    @Override
    public String getGroup() {
        return "Identity Based";
    }

    @Override
    public PolicyProvider create(AuthorizationProvider authorizationProvider) {
        //log.debugf("A %s is being created", CustomPolicyProvider.class.getSimpleName());
        System.out.println("Custom provider is being created");
        return provider;
    }

    @Override
    public CustomPolicyRepresentation toRepresentation(Policy policy, AuthorizationProvider authorization) {
        //log.debugf("A %s is being represented", CustomPolicyRepresentation.class.getSimpleName());
        System.out.println("Is being represented");
        return new CustomPolicyRepresentation();
    }

    @Override
    public Class<CustomPolicyRepresentation> getRepresentationType() {
        return CustomPolicyRepresentation.class;
    }

    @Override
    public void onCreate(Policy policy, CustomPolicyRepresentation representation,
                         AuthorizationProvider authorization) { }

    @Override
    public void onUpdate(Policy policy, CustomPolicyRepresentation representation,
                         AuthorizationProvider authorization) { }

    @Override
    public void onImport(Policy policy, PolicyRepresentation representation, AuthorizationProvider authorization) { }

    @Override
    public void onExport(Policy policy, PolicyRepresentation representation, AuthorizationProvider authorization) { }

    @Override
    public PolicyProvider create(KeycloakSession session) { return this.provider; }

    @Override
    public void init(Config.Scope config) { }

    @Override
    public void postInit(KeycloakSessionFactory factory) { }

    @Override
    public void close() { }
}
