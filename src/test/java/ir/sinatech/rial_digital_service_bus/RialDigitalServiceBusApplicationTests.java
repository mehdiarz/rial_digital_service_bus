package ir.sinatech.rial_digital_service_bus;

import ir.sinatech.rial_digital_service_bus.model.ProcessModel;
import ir.sinatech.rial_digital_service_bus.service.ProcessService;
import ir.sinatech.rial_digital_service_bus.utils.AppConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
class RialDigitalServiceBusApplicationTests {


    @Autowired
    private ProcessService processService;

    @Autowired
    private AppConfig appConfig;

    @Test
    void contextLoads() {
        System.out.println(appConfig.getBaseUrl());
        List<ProcessModel> processes = processService.getProcesses("Sina_SpecificProcesses");

        System.out.println(processes);

        processes.sort(Comparator.comparing(p -> p.version));
        ProcessModel latest = processes.get(processes.size() - 1);
        List<String> empty = new ArrayList<String>();

        System.out.println("startprocesswithreturn: " + processService.startProcessWithReturn("Sina_SpecificProcesses" , empty, null, true));



    }

}
