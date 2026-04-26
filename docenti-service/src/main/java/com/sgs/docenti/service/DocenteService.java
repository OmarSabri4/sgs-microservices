package com.sgs.docenti.service;

import com.sgs.docenti.dto.DocenteDTO;
import com.sgs.docenti.exception.DocenteNotFoundException;
import com.sgs.docenti.model.Docente;
import com.sgs.docenti.repository.DocenteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DocenteService {

    private final DocenteRepository repository;

    public List<DocenteDTO> findAll() {
        log.debug("Recupero lista docenti");
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public DocenteDTO findById(Long id) {
        log.debug("Ricerca docente id={}", id);
        return toDTO(repository.findById(id)
                .orElseThrow(() -> new DocenteNotFoundException(id)));
    }

    public List<DocenteDTO> findByStato(String stato) {
        return repository.findByStato(stato).stream().map(this::toDTO).toList();
    }

    public List<DocenteDTO> findByCognome(String cognome) {
        return repository.findByCognomeContainingIgnoreCase(cognome).stream().map(this::toDTO).toList();
    }

    @Transactional
    public DocenteDTO create(DocenteDTO dto) {
        log.debug("Creazione docente: {}", dto.getCodiceDocente());
        if (repository.existsByCodiceFiscale(dto.getCodiceFiscale())) {
            throw new IllegalArgumentException("Codice fiscale già presente: " + dto.getCodiceFiscale());
        }
        if (repository.existsByEmailIstituzionale(dto.getEmailIstituzionale())) {
            throw new IllegalArgumentException("Email già in uso: " + dto.getEmailIstituzionale());
        }
        Docente saved = repository.save(toEntity(dto));
        return toDTO(saved);
    }

    @Transactional
    public DocenteDTO update(Long id, DocenteDTO dto) {
        log.debug("Aggiornamento docente id={}", id);
        Docente existing = repository.findById(id)
                .orElseThrow(() -> new DocenteNotFoundException(id));

        // Controllo unicità solo se i valori sono cambiati
        if (!existing.getCodiceFiscale().equals(dto.getCodiceFiscale())
                && repository.existsByCodiceFiscale(dto.getCodiceFiscale())) {
            throw new IllegalArgumentException("Codice fiscale già presente: " + dto.getCodiceFiscale());
        }
        if (!existing.getEmailIstituzionale().equals(dto.getEmailIstituzionale())
                && repository.existsByEmailIstituzionale(dto.getEmailIstituzionale())) {
            throw new IllegalArgumentException("Email già in uso: " + dto.getEmailIstituzionale());
        }

        existing.setCodiceDocente(dto.getCodiceDocente());
        existing.setNome(dto.getNome());
        existing.setCognome(dto.getCognome());
        existing.setCodiceFiscale(dto.getCodiceFiscale());
        existing.setEmailIstituzionale(dto.getEmailIstituzionale());
        existing.setTelefono(dto.getTelefono());
        existing.setDataAssunzione(dto.getDataAssunzione());
        existing.setStato(dto.getStato());

        return toDTO(repository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        log.debug("Eliminazione docente id={}", id);
        if (!repository.existsById(id)) {
            throw new DocenteNotFoundException(id);
        }
        repository.deleteById(id);
    }

    // ── Mapping ───────────────────────────────────────────────────────────────

    private DocenteDTO toDTO(Docente d) {
        return DocenteDTO.builder()
                .idDocente(d.getIdDocente())
                .codiceDocente(d.getCodiceDocente())
                .nome(d.getNome())
                .cognome(d.getCognome())
                .codiceFiscale(d.getCodiceFiscale())
                .emailIstituzionale(d.getEmailIstituzionale())
                .telefono(d.getTelefono())
                .dataAssunzione(d.getDataAssunzione())
                .stato(d.getStato())
                .build();
    }

    private Docente toEntity(DocenteDTO dto) {
        return Docente.builder()
                .codiceDocente(dto.getCodiceDocente())
                .nome(dto.getNome())
                .cognome(dto.getCognome())
                .codiceFiscale(dto.getCodiceFiscale())
                .emailIstituzionale(dto.getEmailIstituzionale())
                .telefono(dto.getTelefono())
                .dataAssunzione(dto.getDataAssunzione())
                .stato(dto.getStato())
                .build();
    }
}
