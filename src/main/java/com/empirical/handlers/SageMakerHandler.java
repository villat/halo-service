package com.empirical.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.sagemakerruntime.AmazonSageMakerRuntime;
import com.amazonaws.services.sagemakerruntime.AmazonSageMakerRuntimeClientBuilder;
import com.amazonaws.services.sagemakerruntime.model.InvokeEndpointRequest;
import com.amazonaws.services.sagemakerruntime.model.InvokeEndpointResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class SageMakerHandler {

    private final ObjectMapper mapper = new ObjectMapper();

    public APIGatewayProxyResponseEvent getPrediction(APIGatewayProxyRequestEvent event, Context context) {
        InvokeEndpointRequest invokeEndpointRequest = new InvokeEndpointRequest();
        invokeEndpointRequest.setContentType("application/json");
        invokeEndpointRequest.setBody(ByteBuffer.wrap(event.getBody().getBytes(StandardCharsets.UTF_8)));
        invokeEndpointRequest.setEndpointName(System.getenv("SAGEMAKER_ENDPOINT"));
        invokeEndpointRequest.setAccept("application/json");

        AmazonSageMakerRuntime amazonSageMaker = AmazonSageMakerRuntimeClientBuilder.defaultClient();
        InvokeEndpointResult result = amazonSageMaker.invokeEndpoint(invokeEndpointRequest);
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(StandardCharsets.UTF_8.decode(result.getBody()).toString());
    }

}
