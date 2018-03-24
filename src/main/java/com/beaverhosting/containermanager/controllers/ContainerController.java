package com.beaverhosting.containermanager.controllers;

import com.beaverhosting.containermanager.dto.ContainerWriteDto;
import com.beaverhosting.containermanager.common.ApiResponse;
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
    public ResponseEntity<ApiResponse> createContainer(
            @RequestBody @Valid ContainerWriteDto containerWriteDto) {
        Map<String, String> results = containerService.createContainer(containerWriteDto);

        return new ResponseEntity<>(ApiResponse.builder()
                .name(containerWriteDto.getName())
                .status(results.get("status"))
                .stdout(results.get("stdOut"))
                .stderr(results.get("stdErr"))
                .build(), HttpStatus.OK);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<ApiResponse> deleteContainer(
            @PathVariable(value="name") String name) {


        Map<String, String> results = containerService.deleteContainer(name);

        return new ResponseEntity<>(ApiResponse.builder()
                .name(name)
                .status(results.get("status"))
                .stdout(results.get("stdOut"))
                .stderr(results.get("stdErr"))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ApiResponse> statusContainer(
            @PathVariable(value="name") String name) {

        Map<String, String> results = containerService.statusContainer(name);
        return new ResponseEntity<>(ApiResponse.builder()
                .name(name)
                .status(results.get("status"))
                .stdout(results.get("stdOut"))
                .stderr(results.get("stdErr"))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{name}/ip")
    public ResponseEntity<ApiResponse> getIPContainer(
            @PathVariable(value="name") String name) {
        Map<String, String> results = containerService.getIPContainer(name);
        return new ResponseEntity<>(ApiResponse.builder()
                .name(name)
                .status(results.get("status"))
                .stdout(results.get("stdOut"))
                .stderr(results.get("stdErr"))
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{name}/port")
    public ResponseEntity<ApiResponse> getPortContainer(
            @PathVariable(value="name") String name) {
        Map<String, String> results = containerService.getPortContainer(name);
        return new ResponseEntity<>(ApiResponse.builder()
                .name(name)
                .status(results.get("status"))
                .stdout(results.get("stdOut"))
                .stderr(results.get("stdErr"))
                .build(), HttpStatus.OK);
    }

}
