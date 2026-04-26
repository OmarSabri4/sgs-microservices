package com.sgs.studenti.service;

import com.sgs.studenti.dto.StudenteDTO;
import com.sgs.studenti.exception.StudenteNotFoundException;
import com.sgs.studenti.model.Studente;
import com.sgs.studenti.repository.StudenteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudenteService {

    private final StudenteRepository repository;

    public List<StudenteDTO> findAll() {
        log.debug("Recupero lista studenti");
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public StudenteDTO findById(Long id) {
        log.debug("Ricerca studente id={}", id);
        return toDTO(repository.findById(id)
                .orElseThrow(() -> new StudenteNotFoundException(id)));
    }

    public List<StudenteDTO> findByClasse(Long idClasse) {
        return repository.findByIdClasse(idClasse).stream().map(this::toDTO).toList();
    }

    public List<StudenteDTO> findByAttivo(Boolean attivo) {
        return repository.findByAttivo(attivo).stream().map(this::toDTO).toList();
    }

    public List<StudenteDTO> findByCognome(String cognome) {
        return repository.findByCognomeContainingIgnoreCase(cognome).stream().map(this::toDTO).toList();
    }

    @Transactional
    public StudenteDTO create(StudenteDTO dto) {
        log.debug("Creazione studente: {}", dto.getCodiceStudente());
        if (repository.existsByCodiceFiscale(dto.getCodiceFiscale())) {
            throw new IllegalArgumentException("Codice fiscale già presente: " + dto.getCodiceFiscale());
        }
        if (dto.getEmailScuola() != null && repository.existsByEmailScuola(dto.getEmailScuola())) {
            throw new IllegalArgumentException("Email scuola già in uso: " + dto.getEmailScuola());
        }
        return toDTO(repository.save(toEntity(dto)));
    }

    @Transactional
    public StudenteDTO update(Long id, StudenteDTO dto) {
        log.debug("Aggiornamento studente id={}", id);
        Studente existing = repository.findById(id)
                .orElseThrow(() -> new StudenteNotFoundException(id));

        if (!existing.getCodiceFiscale().equals(dto.getCodiceFiscale())
                && repository.existsByCodiceFiscale(dto.getCodiceFiscale())) {
            throw new IllegalArgumentException("Codice fiscale già presente: " + dto.getCodiceFiscale());
        }
        if (dto.getEmailScuola() != null
                && !dto.getEmailScuola().equals(existing.getEmailScuola())
                && repository.existsByEmailScuola(dto.getEmailScuola())) {
            throw new IllegalArgumentException("Email scuola già in uso: " + dto.getEmailScuola());
        }

        existing.setCodiceStudente(dto.getCodiceStudente());
        existing.setNome(dto.getNome());
        existing.setCognome(dto.getCognome());
        existing.setCodiceFiscale(dto.getCodiceFiscale());
        existing.setDataNascita(dto.getDataNascita());
        existing.setIndirizzo(dto.getIndirizzo());
        existing.setIdClasse(dto.getIdClasse());
        existing.setEmailScuola(dto.getEmailScuola());
        existing.setEmailPersonale(dto.getEmailPersonale());
        existing.setTelefono(dto.getTelefono());
        existing.setTelefonoEmergenza(dto.getTelefonoEmergenza());
        existing.setAttivo(dto.getAttivo());

        return toDTO(repository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Eliminazione studente id={}", id);
        if (!repository.existsById(id)) {
            throw new StudenteNotFoundException(id);
        }
        repository.deleteById(id);
    }

    // ── Mapping ───────────────────────────────────────────────────────────────

    private StudenteDTO toDTO(Studente s) {
        return StudenteDTO.builder()
                .idStudente(s.getIdStudente())
                .codiceStudente(s.getCodiceStudente())
                .nome(s.getNome())
                .cognome(s.getCognome())
                .codiceFiscale(s.getCodiceFiscale())
                .dataNascita(s.getDataNascita())
                .indirizzo(s.getIndirizzo())
                .idClasse(s.getIdClasse())
                .emailScuola(s.getEmailScuola())
                .emailPersonale(s.getEmailPersonale())
                .telefono(s.getTelefono())
                .telefonoEmergenza(s.getTelefonoEmergenza())
                .attivo(s.getAttivo())
                .build();
    }

    private Studente toEntity(StudenteDTO dto) {
        return Studente.builder()
                .codiceStudente(dto.getCodiceStudente())
                .nome(dto.getNome())
                .cognome(dto.getCognome())
                .codiceFiscale(dto.getCodiceFiscale())
                .dataNascita(dto.getDataNascita())
                .indirizzo(dto.getIndirizzo())
                .idClasse(dto.getIdClasse())
                .emailScuola(dto.getEmailScuola())
                .emailPersonale(dto.getEmailPersonale())
                .telefono(dto.getTelefono())
                .telefonoEmergenza(dto.getTelefonoEmergenza())
                .attivo(dto.getAttivo())
                .build();
    }
}
