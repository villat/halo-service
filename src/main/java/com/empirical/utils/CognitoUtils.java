package com.empirical.utils;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.empirical.dto.UserRequest;
import org.json.JSONObject;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AdminCreateUserResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.AttributeType;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CognitoIdentityProviderException;

import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CognitoUtils {

    static Logger logger = Logger.getLogger(CognitoUtils.class.getName());

    public static void createNewUser(UserRequest request){
        try (CognitoIdentityProviderClient cognitoclient = CognitoIdentityProviderClient.builder()
                .region(Region.US_EAST_1)
                .httpClient(ApacheHttpClient.builder().build())
                .build()) {
            AttributeType userAttrs = AttributeType.builder()
                    .name("email")
                    .value(request.getEmail())
                    .build();

            AdminCreateUserRequest userRequest = AdminCreateUserRequest.builder()
                    .userPoolId(System.getenv("USER_POOL_ID"))
                    .username(request.getUsername())
                    .temporaryPassword(request.getPassword())
                    .userAttributes(userAttrs)
                    .messageAction("SUPPRESS")
                    .build();

            AdminCreateUserResponse response = cognitoclient.adminCreateUser(userRequest);
            logger.info("User " + response.user().username() + "is created. Status: " + response.user().userStatus());
        } catch (CognitoIdentityProviderException e) {
            logger.log(Level.SEVERE, "Exception creating Cognito user", e);
            System.exit(1);
        }
    }

    public static String getAuthorizedUserUsername(APIGatewayProxyRequestEvent event){
        return getAuthorizedUserField(event,"cognito:username");
    }

    public static String getAuthorizedUserEmail(APIGatewayProxyRequestEvent event){
        return getAuthorizedUserField(event,"email");
    }

    private static String getAuthorizedUserField(APIGatewayProxyRequestEvent event, String field) {
        String[] tokenChunks = event.getHeaders().get("Authorization").split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(tokenChunks[1]));
        return new JSONObject(payload).getString(field);
    }

}
