package com.beaverhosting.containermanager.controllers;

import com.beaverhosting.containermanager.resources.ContainerStatus;
import com.beaverhosting.containermanager.services.ContainerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(path = "/container")
public class ContainerController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/create")
    public ResponseEntity<ContainerStatus> createContainer(
            @RequestParam(value="name", defaultValue="") String name) {
        return ContainerService.createContainer(name);
    }

    @RequestMapping("/delete")
    public ResponseEntity<ContainerStatus> deleteContainer(
            @RequestParam(value="name", defaultValue="") String name) {

        return ContainerService.deleteContainer(name);
    }

    @RequestMapping("/status")
    public ResponseEntity<ContainerStatus> statusContainer(
            @RequestParam(value="name", defaultValue="") String name) {

        return ContainerService.statusContainer(name);
    }

    @RequestMapping("/ip")
    public ResponseEntity<ContainerStatus> getIPContainer(
            @RequestParam(value="name", defaultValue="") String name) {
        return ContainerService.getIPContainer(name);
    }

    @RequestMapping("/port")
    public ResponseEntity<ContainerStatus> getPortContainer(
            @RequestParam(value="name", defaultValue="") String name) {
        return ContainerService.getPortContainer(name);
    }

}
