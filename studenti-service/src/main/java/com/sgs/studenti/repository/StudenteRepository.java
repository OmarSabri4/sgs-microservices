package com.sgs.studenti.repository;

import com.sgs.studenti.model.Studente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudenteRepository extends JpaRepository<Studente, Long> {

    Optional<Studente> findByCodiceStudente(String codiceStudente);
    Optional<Studente> findByCodiceFiscale(String codiceFiscale);
    List<Studente> findByIdClasse(Long idClasse);
    List<Studente> findByAttivo(Boolean attivo);
    List<Studente> findByCognomeContainingIgnoreCase(String cognome);
    boolean existsByCodiceFiscale(String codiceFiscale);
    boolean existsByEmailScuola(String emailScuola);
}
