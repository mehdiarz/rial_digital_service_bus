package ir.sinatech.rial_digital_service_bus.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import ir.sinatech.rial_digital_service_bus.model.ProcessListResponse;
import ir.sinatech.rial_digital_service_bus.model.ProcessModel;
import ir.sinatech.rial_digital_service_bus.utils.AppConfig;
import ir.sinatech.rial_digital_service_bus.utils.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ProcessService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessService.class);

    private final AppConfig appConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final Set<String> cachedServiceStart = ConcurrentHashMap.newKeySet();
    private final Map<String, Map<String, Object>> cachedResults = new ConcurrentHashMap<>();

    @Autowired
    public ProcessService(AppConfig appConfig, RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.appConfig = appConfig;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public Map<String, Object> startProcessWithReturn(String prcId, List<String> selectedVars, String processVars, boolean byAdmin) {
        if (cachedServiceStart.contains(prcId)) {
            return cachedResults.getOrDefault(prcId, Collections.emptyMap());
        }

        Map<String, Object> resultVars = new HashMap<>();

        try {
            List<ProcessModel> processes = getProcesses(prcId);
            if (!processes.isEmpty()) {
                processes.sort(Comparator.comparing(p -> p.version));
                ProcessModel latest = processes.get(processes.size() - 1);

                Map<String, Object> result = startProcessWithResult(latest.id, processVars, byAdmin);
                if (result != null) {
                    extractVariables(result, resultVars, selectedVars);
                }
            }

            if (cachedServiceStart.contains(prcId)) {
                cachedResults.put(prcId, resultVars);
            }

        } catch (Exception e) {
            logger.error("startProcessWithReturn failed for {}: {}", prcId, e.getMessage(), e);
        }

        return resultVars;
    }

    public Map<String, Object> startProcessWithResult(String processId, String variables, boolean byAdmin) throws Exception {
        String businessKey = String.join(":", appConfig.getAdminUser(), processId, new Date().toString());
        String url = String.format("%s/prcs/%s/startWithReturn?businessKey=%s",
                appConfig.getBaseUrl(), processId,
                URLEncoder.encode(businessKey, StandardCharsets.UTF_8));

        HttpEntity<String> request = RequestUtils.createJsonRequest(variables, byAdmin, appConfig);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return objectMapper.readValue(response.getBody(), new TypeReference<>() {});
        } else {
            String body = response.getBody();
            Map<String, Object> error = parseError(body);
            throw new Exception(getProcessErrorMessage(error));
        }
    }

    public List<ProcessModel> getProcesses(String key) {
        try {
            String url = String.format("%s/prcs-by-key?start=0&size=400&order=desc&key=%s", appConfig.getBaseUrl(), key);
            HttpEntity<Void> request = RequestUtils.createEmptyJsonRequest( true, appConfig);

            ResponseEntity<ProcessListResponse> response = restTemplate.exchange(url, HttpMethod.GET, request, ProcessListResponse.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                return Optional.ofNullable(response.getBody())
                        .map(ProcessListResponse::getData)
                        .orElse(Collections.emptyList());
            }

            throw new RuntimeException("Failed to get processes: " + response.getStatusCode());
        } catch (Exception e) {
            logger.error("Error fetching processes for key {}: {}", key, e.getMessage(), e);
            throw new RuntimeException("Process fetch failed", e);
        }
    }

    private void extractVariables(Map<String, Object> result, Map<String, Object> output, List<String> selected) {
        Map<String, Object> vars = (Map<String, Object>) result.get("variables");
        if (vars == null || !(vars.get("details") instanceof List<?> detailsList)) return;

        for (Object item : detailsList) {
            if (item instanceof Map<?, ?> mapItem) {
                String name = (String) mapItem.get("name");
                Object value = mapItem.get("value");
                if (selected.isEmpty() || selected.contains(name)) {
                    output.put(name, value);
                }
            }
        }
    }

    private Map<String, Object> parseError(String body) {
        try {
            return objectMapper.readValue(body, new TypeReference<>() {});
        } catch (Exception ex) {
            logger.error("Failed to parse error response: {}", ex.getMessage());
            return Map.of("rawError", body);
        }
    }

    private static String getProcessErrorMessage(Map<String, Object> responseData) {
        return (String) responseData.getOrDefault("message", "Unknown error");
    }
}