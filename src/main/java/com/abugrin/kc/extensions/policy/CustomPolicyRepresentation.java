package com.abugrin.kc.extensions.policy;

import org.keycloak.representations.idm.authorization.GroupPolicyRepresentation;

public class CustomPolicyRepresentation extends GroupPolicyRepresentation {

    public CustomPolicyRepresentation() {
        System.out.println("A is being created " + this.getClass().getSimpleName());
        }

    public String getType() {
        System.out.println("Getting the type of " + this.getClass().getSimpleName());
        return "customPolicy";
    }
}