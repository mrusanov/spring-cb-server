package com.mrusanov.spring.cb.server.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ServerApi {

    @RequestMapping(value = "/server/delay/{delay}", method = RequestMethod.GET)
    public ResponseEntity<?> delayEndpoint(@PathVariable(value = "delay") int delay) {

        String responseBody = "Some data from server, returned after "+ delay +" second(s) delay...";

        try {
            System.out.println("Server logic which takes " + delay + " second(s)...");
            Thread.sleep(delay*1000);
        } catch (InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        return ResponseEntity.ok(responseBody);
    }

    @RequestMapping(value = "/server/error/{errorCode}", method = RequestMethod.GET)
    public ResponseEntity<?> errorEndpoint(@PathVariable(value = "errorCode") String errorCode) {

        // Except error codes 4xx and 5xx, code 200-OK is allowed to close the circuit again
        if(!errorCode.startsWith("4") && !errorCode.startsWith("5") && !errorCode.equals("200")) {
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(null);
        }
        HttpStatus status = HttpStatus.valueOf(Integer.valueOf(errorCode));
        return ResponseEntity.status(status).body(null);
    }

}
