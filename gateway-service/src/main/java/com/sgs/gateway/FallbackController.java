package com.sgs.gateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/docenti")
    public ResponseEntity<Map<String, String>> fallbackDocenti() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "status", "503",
                        "messaggio", "Il servizio Docenti non è al momento disponibile. Riprovare più tardi."
                ));
    }

    @GetMapping("/studenti")
    public ResponseEntity<Map<String, String>> fallbackStudenti() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "status", "503",
                        "messaggio", "Il servizio Studenti non è al momento disponibile. Riprovare più tardi."
                ));
    }
}
