package com.sgs.docenti.repository;

import com.sgs.docenti.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {

    Optional<Docente> findByCodiceDocente(String codiceDocente);
    Optional<Docente> findByCodiceFiscale(String codiceFiscale);
    Optional<Docente> findByEmailIstituzionale(String email);
    List<Docente> findByStato(String stato);
    List<Docente> findByCognomeContainingIgnoreCase(String cognome);
    boolean existsByCodiceFiscale(String codiceFiscale);
    boolean existsByEmailIstituzionale(String email);
}
