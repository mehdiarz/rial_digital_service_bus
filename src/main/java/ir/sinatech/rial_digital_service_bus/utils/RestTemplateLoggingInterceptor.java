package ir.sinatech.rial_digital_service_bus.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import java.io.IOException;

public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateLoggingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        logger.debug("Request: {} {}", request.getMethod(), request.getURI());
        logger.debug("Headers: {}", request.getHeaders());
        logger.debug("Body: {}", new String(body, "UTF-8"));

        ClientHttpResponse response = execution.execute(request, body);

        logger.debug("Response: {}", response.getStatusCode());
        return response;
    }
}