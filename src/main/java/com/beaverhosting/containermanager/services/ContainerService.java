package com.beaverhosting.containermanager.services;

import com.beaverhosting.containermanager.forms.ContainerCreationForm;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ContainerService {

    @Value("${deployment.path}")
    private String DEPLOYMENTS_PATH;

    public Map<String, String> createContainer(ContainerCreationForm containerCreationForm) { //TODO: Return http status code 500 if kubectl fail
        return execCmd(String.format("kubectl create -f %s%s%s", DEPLOYMENTS_PATH, containerCreationForm.getName(), "/deployment.yaml"));
    }

    public Map<String, String> statusContainer(String name) { //TODO: Return http status code 500 if kubectl fail
        return execCmd(String.format("kubectl get -f %s%s%s", DEPLOYMENTS_PATH, name, "/deployment.yaml"));
    }

    public Map<String, String> deleteContainer(String name) { //TODO: Return http status code 500 if kubectl fail
        return execCmd(String.format("kubectl delete -f %s%s%s", DEPLOYMENTS_PATH, name, "/deployment.yaml"));
    }

    public Map<String, String> getIPContainer(String name) { //TODO: Return http status code 500 if kubectl fail
        Map<String, String> results = execCmd(String.format("kubectl describe -f %s%s%s", DEPLOYMENTS_PATH, name, "/deployment.yaml"));
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
        Map<String, String> results = execCmd(String.format("kubectl describe -f %s%s%s", DEPLOYMENTS_PATH, name, "/deployment.yaml"));
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

    private Map<String, String> execCmd(String cmd) {
        Map<String, String> result = new HashMap<>();
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) { //TODO Error handling
            e.printStackTrace();
        }
        String stdOut = "";
        String stdErr = "";
        Integer exitCode = 0;
        try {
            exitCode = process.waitFor();
            stdOut = IOUtils.toString(process.getInputStream(), "UTF-8");
            stdErr = IOUtils.toString(process.getErrorStream(), "UTF-8");
        } catch (InterruptedException|IOException e){ //TODO Error handling
            e.printStackTrace();
        }
        result.put("status", exitCode.toString());
        result.put("stdOut", stdOut);
        result.put("stdErr", stdErr);
        return result;
    }
}
