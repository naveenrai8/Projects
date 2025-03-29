package com.nr.mq.controller;

import com.nr.mq.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/client")
public class ClientController {

    private final ClientService service;

    @PostMapping()
    public ResponseEntity<?> registerClient(){
        var clientId = service.registerClient();
        return new ResponseEntity<>(clientId, HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteClient(@RequestParam("id") String clientId){
        service.deregisterClient(clientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
