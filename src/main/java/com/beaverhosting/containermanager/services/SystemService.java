package com.beaverhosting.containermanager.services;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class SystemService {
    public Map<String, String> execCmd(String cmd) {
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
