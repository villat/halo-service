package com.empirical.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.empirical.dao.CompanyDao;
import com.empirical.dao.OwnerDao;
import com.empirical.dao.UserDao;
import com.empirical.domain.Company;
import com.empirical.domain.Owner;
import com.empirical.domain.User;
import com.empirical.dto.CompanyRequest;
import com.empirical.utils.CognitoUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CompanyHandler {

    private static final Logger logger = Logger.getLogger(CompanyHandler.class.getName());

    private final UserDao userDao = new UserDao();
    private final OwnerDao ownerDao = new OwnerDao();
    private final CompanyDao companyDao = new CompanyDao();
    private final ObjectMapper mapper = new ObjectMapper();

    public APIGatewayProxyResponseEvent getCompanies(APIGatewayProxyRequestEvent event, Context context) {
        return new APIGatewayProxyResponseEvent().withStatusCode(501);
    }

    public APIGatewayProxyResponseEvent createCompany(APIGatewayProxyRequestEvent event, Context context) {
        User user = userDao.getByUsername(CognitoUtils.getAuthorizedUserUsername(event));

        if (user != null && user.getRole().equals(User.Role.COMPANY)) {
            Owner owner = (Owner) user;
            if (owner.getCompany() == null) {
                try {
                    CompanyRequest createCompanyRequest = mapper.readValue(event.getBody(), CompanyRequest.class);

                    Company company = Company.Builder.create()
                            .withName(createCompanyRequest.getName())
                            .withType(createCompanyRequest.getType())
                            .withOwner(owner)
                            .build();

                    companyDao.saveOrUpdate(company);
                    owner.setCompany(company);
                    ownerDao.saveOrUpdate(owner);

                    return new APIGatewayProxyResponseEvent().withStatusCode(201);
                } catch (JsonProcessingException e) {
                    logger.log(Level.SEVERE, "Exception creating a new company", e);
                    return new APIGatewayProxyResponseEvent().withStatusCode(500);
                }
            }
        }
        return new APIGatewayProxyResponseEvent().withStatusCode(403);
    }
}
