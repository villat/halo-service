package com.empirical.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.empirical.dao.SuperAdminDao;
import com.empirical.domain.SuperAdmin;
import com.empirical.domain.User;
import com.empirical.utils.CognitoUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Logger;

public class SuperAdminHandler {

    private static final Logger logger = Logger.getLogger(CognitoUtils.class.getName());

    private final SuperAdminDao superAdminDao = new SuperAdminDao();
    private final ObjectMapper mapper = new ObjectMapper();

    public APIGatewayProxyResponseEvent giveAdminRole(APIGatewayProxyRequestEvent event, Context context) {
        String username = CognitoUtils.getAuthorizedUser(event);

        SuperAdmin superAdmin = superAdminDao.getByUsername(username);

        if (superAdmin != null && superAdmin.getRole() == null) {
            superAdmin.setRole(User.Role.SUPER_ADMIN);
            superAdminDao.saveOrUpdate(superAdmin);
            return new APIGatewayProxyResponseEvent().withStatusCode(200);
        } else {
            logger.info("User cannot be assigned as an admin");
            return new APIGatewayProxyResponseEvent().withStatusCode(400);
        }
    }

}
