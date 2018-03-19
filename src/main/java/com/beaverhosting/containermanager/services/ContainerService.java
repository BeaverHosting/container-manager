package com.beaverhosting.containermanager.services;

import com.beaverhosting.containermanager.resources.ContainerStatus;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public abstract class ContainerService {

    static final String DEPLOYMENTS_PATH = "/home/pierre/deployments/";
    //TODO: Merge create/status/delete in one method ?
    public static ResponseEntity<ContainerStatus> createContainer(String name) { //TODO: Return http status code 500 if kubectl fail
        Map<String, String> results = execCmd("kubectl create -f " + DEPLOYMENTS_PATH + name +"/deployment.yaml");
        return new ResponseEntity<>(ContainerStatus.builder()
                .name(name)
                .status(results.get("status"))
                .stdout(results.get("stdOut"))
                .stderr(results.get("stdErr"))
                .build(), HttpStatus.OK);
    }

    public static ResponseEntity<ContainerStatus> statusContainer(String name) { //TODO: Return http status code 500 if kubectl fail
        Map<String, String> results = execCmd("kubectl get -f " + DEPLOYMENTS_PATH + name +"/deployment.yaml");
        return new ResponseEntity<>(ContainerStatus.builder()
                .name(name)
                .status(results.get("status"))
                .stdout(results.get("stdOut"))
                .stderr(results.get("stdErr"))
                .build(), HttpStatus.OK);
    }

    public static ResponseEntity<ContainerStatus> deleteContainer(String name) { //TODO: Return http status code 500 if kubectl fail
        Map<String, String> results = execCmd("kubectl delete -f " + DEPLOYMENTS_PATH + name +"/deployment.yaml");
        return new ResponseEntity<>(ContainerStatus.builder()
                .name(name)
                .status(results.get("status"))
                .stdout(results.get("stdOut"))
                .stderr(results.get("stdErr"))
                .build(), HttpStatus.OK);
    }

    public static ResponseEntity<ContainerStatus> getIPContainer(String name) { //TODO: Return http status code 500 if kubectl fail
        Map<String, String> results = execCmd("kubectl describe -f " + DEPLOYMENTS_PATH + name + "/deployment.yaml");
        if(results.get("status").equals("0")){
            //Extracting IP from result
            Pattern p = Pattern.compile("IP:(.*)");
            Matcher m = p.matcher(results.get("stdOut"));
            m.find();
            String ip = m.group(1).trim();
            results.put("stdOut", ip);
        }
        return new ResponseEntity<>(ContainerStatus.builder()
                .name(name)
                .status(results.get("status"))
                .stdout(results.get("stdOut"))
                .stderr(results.get("stdErr"))
                .build(), HttpStatus.OK);
    }

    public static ResponseEntity<ContainerStatus> getPortContainer(String name) { //TODO: Return http status code 500 if kubectl fail
        Map<String, String> results = execCmd("kubectl describe -f " + DEPLOYMENTS_PATH + name + "/deployment.yaml");
        if(results.get("status").equals("0")){
            //Extracting Port from result
            Pattern p = Pattern.compile("NodePort:(.*)");
            Matcher m = p.matcher(results.get("stdOut"));
            m.find();
            String port = m.group(1).replace("<unset>", "").replace("/TCP", "").trim();
            results.put("stdOut", port);
        }
        return new ResponseEntity<>(ContainerStatus.builder()
                .name(name)
                .status(results.get("status"))
                .stdout(results.get("stdOut"))
                .stderr(results.get("stdErr"))
                .build(), HttpStatus.OK);
    }

    private static Map<String, String> execCmd(String cmd) {
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
