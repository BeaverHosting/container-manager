package com.beaverhosting.containermanager.controllers;

import com.beaverhosting.containermanager.forms.ContainerCreationForm;
import com.beaverhosting.containermanager.resources.ContainerStatus;
import com.beaverhosting.containermanager.services.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(path = "/container")
public class ContainerController {

    @Autowired
    private ContainerService containerService;

    @PostMapping("/")
    public ResponseEntity<ContainerStatus> createContainer(
            @RequestBody @Valid ContainerCreationForm containerCreationForm) {
        Map<String, String> results = containerService.createContainer(containerCreationForm);

        return new ResponseEntity<>(ContainerStatus.builder()
                .name(containerCreationForm.getName())
                .status(results.get("status"))
                .stdout(results.get("stdOut"))
                .stderr(results.get("stdErr"))
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<ContainerStatus> deleteContainer(
            @PathVariable(value="name") String name) {


        Map<String, String> results = containerService.deleteContainer(name);

        return new ResponseEntity<>(ContainerStatus.builder()
                .name(name)
                .status(results.get("status"))
                .stdout(results.get("stdOut"))
                .stderr(results.get("stdErr"))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ContainerStatus> statusContainer(
            @PathVariable(value="name") String name) {

        Map<String, String> results = containerService.statusContainer(name);
        return new ResponseEntity<>(ContainerStatus.builder()
                .name(name)
                .status(results.get("status"))
                .stdout(results.get("stdOut"))
                .stderr(results.get("stdErr"))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{name}/ip")
    public ResponseEntity<ContainerStatus> getIPContainer(
            @PathVariable(value="name") String name) {
        Map<String, String> results = containerService.getIPContainer(name);
        return new ResponseEntity<>(ContainerStatus.builder()
                .name(name)
                .status(results.get("status"))
                .stdout(results.get("stdOut"))
                .stderr(results.get("stdErr"))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{name}/port")
    public ResponseEntity<ContainerStatus> getPortContainer(
            @PathVariable(value="name") String name) {
        Map<String, String> results = containerService.getPortContainer(name);
        return new ResponseEntity<>(ContainerStatus.builder()
                .name(name)
                .status(results.get("status"))
                .stdout(results.get("stdOut"))
                .stderr(results.get("stdErr"))
                .build(), HttpStatus.OK);
    }

}
