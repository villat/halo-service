package com.empirical.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.empirical.dao.SuperAdminDao;
import com.empirical.dao.UserDao;
import com.empirical.domain.SuperAdmin;
import com.empirical.domain.User;
import com.empirical.dto.AdminRequest;
import com.empirical.utils.CognitoUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AdminHandler {

    private static final Logger logger = Logger.getLogger(AdminHandler.class.getName());
    private static final String BAD_REQUEST_ERROR = "User cannot be assigned as an admin";

    private final SuperAdminDao superAdminDao = new SuperAdminDao();
    private final UserDao userDao = new UserDao();
    private final ObjectMapper mapper = new ObjectMapper();

    public APIGatewayProxyResponseEvent assignAdminRole(APIGatewayProxyRequestEvent event, Context context) {
        String username = CognitoUtils.getAuthorizedUserUsername(event);

        try {
            if (userDao.getByUsername(username) == null) {
                String email = CognitoUtils.getAuthorizedUserEmail(event);
                AdminRequest assignAdminRequest = mapper.readValue(event.getBody(), AdminRequest.class);

                SuperAdmin superAdmin = SuperAdmin.Builder.create()
                        .withUsername(username)
                        .withEmail(email)
                        .withRole(User.Role.SUPER_ADMIN)
                        .withDescription(assignAdminRequest.getDescription())
                        .build();
                superAdminDao.saveOrUpdate(superAdmin);
                return new APIGatewayProxyResponseEvent().withStatusCode(200);
            } else {
                logger.info(BAD_REQUEST_ERROR);
                return new APIGatewayProxyResponseEvent().withStatusCode(400).withBody(BAD_REQUEST_ERROR);
            }
        } catch (JsonProcessingException e) {
            logger.log(Level.SEVERE, "Exception assigning a new admin", e);
            return new APIGatewayProxyResponseEvent().withStatusCode(500);
        }

    }

}
