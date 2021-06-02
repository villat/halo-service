package com.empirical.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.empirical.dao.OwnerDao;
import com.empirical.domain.Owner;
import com.empirical.utils.CognitoUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CompanyHandler {

    private final OwnerDao ownerDao = new OwnerDao();

    public APIGatewayProxyResponseEvent getCompanies(APIGatewayProxyRequestEvent event, Context context) {

        Optional<Owner> owner = ownerDao.get(1L);
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonInString = mapper.writeValueAsString(owner.get());

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            return new APIGatewayProxyResponseEvent().withStatusCode(200).withHeaders(headers).withBody(jsonInString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new APIGatewayProxyResponseEvent().withStatusCode(500);
        }
    }

    public APIGatewayProxyResponseEvent createCompany(APIGatewayProxyRequestEvent request, Context context) {
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("It creates!");
    }
}
