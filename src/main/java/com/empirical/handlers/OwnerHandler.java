package com.empirical.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.empirical.dao.OwnerDao;
import com.empirical.dao.SuperAdminDao;
import com.empirical.domain.Owner;
import com.empirical.domain.User;
import com.empirical.dto.UserRequest;
import com.empirical.utils.CognitoUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class OwnerHandler {

    private static final Logger logger = Logger.getLogger(OwnerHandler.class.getName());

    private final OwnerDao ownerDao = new OwnerDao();
    private final SuperAdminDao superAdminDao = new SuperAdminDao();
    private final ObjectMapper mapper = new ObjectMapper();

    public APIGatewayProxyResponseEvent createOwner(APIGatewayProxyRequestEvent event, Context context) {
        if (superAdminDao.getByUsername(CognitoUtils.getAuthorizedUserUsername(event)) != null) {
            try {
                UserRequest createOwnerRequest = mapper.readValue(event.getBody(), UserRequest.class);

                logger.info(String.format("Creating Cognito user for %s", createOwnerRequest.getUsername()));
                CognitoUtils.createNewUser(createOwnerRequest);
                logger.info(String.format("Cognito user %s created", createOwnerRequest.getUsername()));

                Owner owner = Owner.Builder.create()
                        .withUsername(createOwnerRequest.getUsername())
                        .withEmail(createOwnerRequest.getEmail())
                        .withCreatedBy(CognitoUtils.getAuthorizedUserUsername(event))
                        .withRole(User.Role.COMPANY)
                        .build();

                ownerDao.saveOrUpdate(owner);
                return new APIGatewayProxyResponseEvent().withStatusCode(201);
            } catch (JsonProcessingException e) {
                logger.log(Level.SEVERE, "Exception creating a new owner", e);
                return new APIGatewayProxyResponseEvent().withStatusCode(500);
            }
        } else {
            return new APIGatewayProxyResponseEvent().withStatusCode(403);
        }
    }

}
