package com.abugrin.kc.extensions.auth;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.authenticators.browser.UsernamePasswordForm;
import org.keycloak.events.Details;
import org.keycloak.events.Errors;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.forms.login.MessageType;
import org.keycloak.models.UserModel;
import org.keycloak.services.ServicesLogger;

import java.util.List;
import java.util.Map;

import static com.abugrin.kc.extensions.auth.UsernamePasswordUAFormConfiguration.*;
import static org.keycloak.services.validation.Validation.FIELD_PASSWORD;

public class UsernamePasswordUAForm extends UsernamePasswordForm implements Authenticator {

    protected static ServicesLogger log = ServicesLogger.LOGGER;

    @Override
    protected Response challenge(AuthenticationFlowContext context, String error, String field) {
        return super.challenge(context, null, null);
    }

    @Override
    protected Response challenge(AuthenticationFlowContext context, MultivaluedMap<String, String> formData) {
        return super.challenge(context, formData);
    }

    @Override
    protected boolean validateForm(AuthenticationFlowContext context, MultivaluedMap<String, String> formData) {
        return super.validateForm(context, formData)
                && isUserUAValid(context);
    }

    private boolean isUserUAValid(AuthenticationFlowContext context) {
        UserModel user = context.getUser();
        boolean uaValid = user != null && checkUserAgent(context);

        return uaValid || badUAHandler(context, user);
    }

    private boolean checkUserAgent(AuthenticationFlowContext context) {
        String uaName = context.getAuthenticatorConfig().getConfig().get(USER_AGENT_NAME);
        String uaVersion = context.getAuthenticatorConfig().getConfig().get(USER_AGENT_VERSION);
        log.info(context.getHttpRequest().getUri().getRequestUri().getRawUserInfo());

        MultivaluedMap<String, String> allHeaders = context.getHttpRequest().getHttpHeaders().getRequestHeaders();
        log.info("***** All Request Headers:");
        for (Map.Entry<String, List<String>> stringStringEntry : allHeaders.entrySet()) {
            String key = stringStringEntry.getKey();
            log.info("***** Header: " + key);
            List<String> value = stringStringEntry.getValue();
            for (String s : value) {
                log.info("***** Value: " + s);
            }

        }


        //List<String> uaHeaders = context.getHttpRequest().getHttpHeaders().getRequestHeaders().get("sec-ch-ua");
        List<String> uaHeaders = context.getHttpRequest().getHttpHeaders().getRequestHeaders().get("user-agent");
        List<String> customHeaders = context.getHttpRequest().getHttpHeaders().getRequestHeaders().get("X-Yandex-CustomHeader");

        boolean uaNameValid = false;
        //boolean uaVersionValid = true; // Ignore UA version test

        log.info("***** Check UA name: " + uaName);
        log.info("***** Check Custom Header (from UA version): " + uaVersion);
        //log.info("***** This release will ignore UA version");
        for (String uaHeader : uaHeaders) {
            log.info("***** Header: " + uaHeader);

            if (uaHeader.contains(uaName)) {
                uaNameValid = true;
                break;
            }
//            String[] uaTokens = uaHeader.split("\\s*,\\s*");
//            for (int i = 0; i < uaTokens.length; i++) {
//                log.info(" ***** uaTokens[" + i + "] : " + uaTokens[i]);
//                String[] uaInfo = uaTokens[i].split("\\s*;\\s*");
//                for (int j = 0; j < uaInfo.length; j++) {
//                    String unquotedInfo = uaInfo[j].replaceAll("\"", "");
//                    log.info("  ***** uaInfo[" + j + "] >" + unquotedInfo + "<");
//                    if  (unquotedInfo.equals(uaName)) {
//                        log.info("  **** UA name matched: " + unquotedInfo);
//                        uaNameValid = true;
//                    }
//                    if (unquotedInfo.startsWith("v=")){
//                        String version = unquotedInfo.substring(2);
//                        if (version.equals(uaVersion)) {
//                            log.info("  **** UA version matched: " + version);
//                            uaVersionValid = true;
//                        }
//
//                    }
//                }
//            }
        }
        if (customHeaders != null) {
            for (String customHeader : customHeaders) {
                log.info("***** Custom Header: " + customHeader);
                if (customHeader.contains(uaVersion)) {
                    uaNameValid = true;
                    break;
                }
            }
        }
        //return uaNameValid && uaVersionValid;
        return uaNameValid;
    }

    private boolean badUAHandler(AuthenticationFlowContext context, UserModel user) {
        context.getEvent().user(user);
        context.getEvent().error(Errors.INVALID_USER_CREDENTIALS);
        context.getEvent().detail(Details.REASON, "Invalid Browser Version!");
        context.form().setMessage(MessageType.ERROR, "Invalid Browser Version!");

        if (isUserAlreadySetBeforeUsernamePasswordAuth(context)) {
            LoginFormsProvider form = context.form();
            form.setAttribute(LoginFormsProvider.USERNAME_HIDDEN, true);
            form.setAttribute(LoginFormsProvider.REGISTRATION_DISABLED, true);

        }

        Response challengeResponse = challenge(context, getDefaultChallengeMessage(context), FIELD_PASSWORD);
        context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS, challengeResponse);
        context.clearUser();

        return false;
    }

}