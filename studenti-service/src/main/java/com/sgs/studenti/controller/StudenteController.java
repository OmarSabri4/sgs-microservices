package com.sgs.studenti.controller;

import com.sgs.studenti.dto.StudenteDTO;
import com.sgs.studenti.service.StudenteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/studenti")
@RequiredArgsConstructor
public class StudenteController {

    private final StudenteService service;

    // GET /api/studenti
    @GetMapping
    public ResponseEntity<List<StudenteDTO>> getAll(
            @RequestParam(required = false) Long idClasse,
            @RequestParam(required = false) Boolean attivo,
            @RequestParam(required = false) String cognome) {

        if (idClasse != null) return ResponseEntity.ok(service.findByClasse(idClasse));
        if (attivo != null)   return ResponseEntity.ok(service.findByAttivo(attivo));
        if (cognome != null)  return ResponseEntity.ok(service.findByCognome(cognome));
        return ResponseEntity.ok(service.findAll());
    }

    // GET /api/studenti/{id}
    @GetMapping("/{id}")
    public ResponseEntity<StudenteDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // POST /api/studenti
    @PostMapping
    public ResponseEntity<StudenteDTO> create(@Valid @RequestBody StudenteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    // PUT /api/studenti/{id}
    @PutMapping("/{id}")
    public ResponseEntity<StudenteDTO> update(@PathVariable Long id,
                                               @Valid @RequestBody StudenteDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // DELETE /api/studenti/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
