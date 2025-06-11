package ir.sinatech.rial_digital_service_bus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import reactor.core.publisher.Mono;

public class WebClientLoggingFilter {

    private static final Logger logger = LoggerFactory.getLogger(WebClientLoggingFilter.class);

    public static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            logger.info("Request: {} {}", request.method(), request.url());
            request.headers().forEach((name, values) ->
                    values.forEach(value -> logger.info("Request Header: {}={}", name, value)));
            return Mono.just(request);
        });
    }

    public static ExchangeFilterFunction logResponse() {
        return ExchangeFilterFunction.ofResponseProcessor(response -> {
            logger.info("Response Status: {}", response.statusCode());
            response.headers().asHttpHeaders()
                    .forEach((name, values) ->
                            values.forEach(value -> logger.info("Response Header: {}={}", name, value)));
            return Mono.just(response);
        });
    }
}
