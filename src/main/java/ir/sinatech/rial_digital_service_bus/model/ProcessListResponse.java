package ir.sinatech.rial_digital_service_bus.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessListResponse {
    public List<ProcessModel> data;
    public int total;
    public int totalPage;
    public int start;
    public int size;
    public int pageNumber;
}
