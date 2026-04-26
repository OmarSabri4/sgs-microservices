package com.sgs.docenti.controller;

import com.sgs.docenti.dto.DocenteDTO;
import com.sgs.docenti.service.DocenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docenti")
@RequiredArgsConstructor
public class DocenteController {

    private final DocenteService service;

    // GET /api/docenti
    @GetMapping
    public ResponseEntity<List<DocenteDTO>> getAll(
            @RequestParam(required = false) String stato,
            @RequestParam(required = false) String cognome) {

        if (stato != null)   return ResponseEntity.ok(service.findByStato(stato));
        if (cognome != null) return ResponseEntity.ok(service.findByCognome(cognome));
        return ResponseEntity.ok(service.findAll());
    }

    // GET /api/docenti/{id}
    @GetMapping("/{id}")
    public ResponseEntity<DocenteDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // POST /api/docenti
    @PostMapping
    public ResponseEntity<DocenteDTO> create(@Valid @RequestBody DocenteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    // PUT /api/docenti/{id}
    @PutMapping("/{id}")
    public ResponseEntity<DocenteDTO> update(@PathVariable Long id,
                                              @Valid @RequestBody DocenteDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // DELETE /api/docenti/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
