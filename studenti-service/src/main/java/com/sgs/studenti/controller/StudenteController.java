package com.sgs.studenti.controller;

import com.sgs.studenti.dto.StudenteDTO;
import com.sgs.studenti.service.StudenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/studenti")
@RequiredArgsConstructor
@Tag(name = "Studenti", description = "API per la gestione degli studenti")
public class StudenteController {

    private final StudenteService service;

    @Operation(summary = "Lista tutti gli studenti", description = "Restituisce tutti gli studenti, con filtri opzionali per classe, stato o cognome")
    @ApiResponse(responseCode = "200", description = "Lista restituita con successo")
    @GetMapping
    public ResponseEntity<List<StudenteDTO>> getAll(
            @Parameter(description = "Filtra per ID classe")
            @RequestParam(required = false) Long idClasse,
            @Parameter(description = "Filtra per stato attivo/non attivo")
            @RequestParam(required = false) Boolean attivo,
            @Parameter(description = "Filtra per cognome (ricerca parziale)")
            @RequestParam(required = false) String cognome) {

        if (idClasse != null) return ResponseEntity.ok(service.findByClasse(idClasse));
        if (attivo != null)   return ResponseEntity.ok(service.findByAttivo(attivo));
        if (cognome != null)  return ResponseEntity.ok(service.findByCognome(cognome));
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Trova studente per ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Studente trovato"),
        @ApiResponse(responseCode = "404", description = "Studente non trovato")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudenteDTO> getById(
            @Parameter(description = "ID dello studente") @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Crea un nuovo studente")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Studente creato con successo"),
        @ApiResponse(responseCode = "400", description = "Dati non validi"),
        @ApiResponse(responseCode = "409", description = "Codice fiscale o email già esistenti")
    })
    @PostMapping
    public ResponseEntity<StudenteDTO> create(@Valid @RequestBody StudenteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @Operation(summary = "Aggiorna uno studente esistente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Studente aggiornato con successo"),
        @ApiResponse(responseCode = "404", description = "Studente non trovato"),
        @ApiResponse(responseCode = "409", description = "Codice fiscale o email già esistenti")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StudenteDTO> update(
            @Parameter(description = "ID dello studente") @PathVariable Long id,
            @Valid @RequestBody StudenteDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Elimina uno studente")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Studente eliminato con successo"),
        @ApiResponse(responseCode = "404", description = "Studente non trovato")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID dello studente") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
