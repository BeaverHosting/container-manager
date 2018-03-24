package com.beaverhosting.containermanager.services;

import com.beaverhosting.containermanager.dto.ContainerWriteDto;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ContainerService {

    @Value("${deployment.path}")
    private String DEPLOYMENTS_PATH;

    @Autowired
    SystemService systemService;

    public Map<String, String> createContainer(ContainerWriteDto containerCreationForm) { //TODO: Return http status code 500 if kubectl fail
        return systemService.execCmd(String.format("kubectl create -f %s%s%s", DEPLOYMENTS_PATH, containerCreationForm.getName(), "/deployment.yaml"));
    }

    public Map<String, String> statusContainer(String name) { //TODO: Return http status code 500 if kubectl fail
        return systemService.execCmd(String.format("kubectl get -f %s%s%s", DEPLOYMENTS_PATH, name, "/deployment.yaml"));
    }

    public Map<String, String> deleteContainer(String name) { //TODO: Return http status code 500 if kubectl fail
        return systemService.execCmd(String.format("kubectl delete -f %s%s%s", DEPLOYMENTS_PATH, name, "/deployment.yaml"));
    }

    public Map<String, String> getIPContainer(String name) { //TODO: Return http status code 500 if kubectl fail
        Map<String, String> results = systemService.execCmd(String.format("kubectl describe -f %s%s%s", DEPLOYMENTS_PATH, name, "/deployment.yaml"));
        if(results.get("status").equals("0")){
            //Extracting IP from result
            Pattern p = Pattern.compile("IP:(.*)");
            Matcher m = p.matcher(results.get("stdOut"));
            m.find();
            String ip = m.group(1).trim();
            results.put("stdOut", ip);
        }
        return results;
    }

    public Map<String, String> getPortContainer(String name) { //TODO: Return http status code 500 if kubectl fail
        Map<String, String> results = systemService.execCmd(String.format("kubectl describe -f %s%s%s", DEPLOYMENTS_PATH, name, "/deployment.yaml"));
        if(results.get("status").equals("0")){
            //Extracting Port from result
            Pattern p = Pattern.compile("NodePort:(.*)");
            Matcher m = p.matcher(results.get("stdOut"));
            m.find();
            String port = m.group(1).replace("<unset>", "").replace("/TCP", "").trim();
            results.put("stdOut", port);
        }
        return results;
    }

}
