package com.sgs.docenti.controller;

import com.sgs.docenti.dto.DocenteDTO;
import com.sgs.docenti.service.DocenteService;
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
@RequestMapping("/api/docenti")
@RequiredArgsConstructor
@Tag(name = "Docenti", description = "API per la gestione dei docenti")
public class DocenteController {

    private final DocenteService service;

    @Operation(summary = "Lista tutti i docenti", description = "Restituisce tutti i docenti, con filtri opzionali per stato o cognome")
    @ApiResponse(responseCode = "200", description = "Lista restituita con successo")
    @GetMapping
    public ResponseEntity<List<DocenteDTO>> getAll(
            @Parameter(description = "Filtra per stato (es. attivo, in_pensione)")
            @RequestParam(required = false) String stato,
            @Parameter(description = "Filtra per cognome (ricerca parziale)")
            @RequestParam(required = false) String cognome) {

        if (stato != null)   return ResponseEntity.ok(service.findByStato(stato));
        if (cognome != null) return ResponseEntity.ok(service.findByCognome(cognome));
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Trova docente per ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Docente trovato"),
        @ApiResponse(responseCode = "404", description = "Docente non trovato")
    })
    @GetMapping("/{id}")
    public ResponseEntity<DocenteDTO> getById(
            @Parameter(description = "ID del docente") @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Crea un nuovo docente")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Docente creato con successo"),
        @ApiResponse(responseCode = "400", description = "Dati non validi"),
        @ApiResponse(responseCode = "409", description = "Codice fiscale o email già esistenti")
    })
    @PostMapping
    public ResponseEntity<DocenteDTO> create(@Valid @RequestBody DocenteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @Operation(summary = "Aggiorna un docente esistente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Docente aggiornato con successo"),
        @ApiResponse(responseCode = "404", description = "Docente non trovato"),
        @ApiResponse(responseCode = "409", description = "Codice fiscale o email già esistenti")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DocenteDTO> update(
            @Parameter(description = "ID del docente") @PathVariable Long id,
            @Valid @RequestBody DocenteDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @Operation(summary = "Elimina un docente")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Docente eliminato con successo"),
        @ApiResponse(responseCode = "404", description = "Docente non trovato")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID del docente") @PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
