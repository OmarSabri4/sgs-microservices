package com.sgs.studenti.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Studenti", schema = "sgs_core")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Studente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_studente")
    private Long idStudente;

    @Column(name = "codice_studente", nullable = false, unique = true, length = 20)
    private String codiceStudente;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "cognome", nullable = false, length = 50)
    private String cognome;

    @Column(name = "codice_fiscale", nullable = false, unique = true, length = 16)
    private String codiceFiscale;

    @Column(name = "data_nascita")
    private LocalDate dataNascita;

    @Column(name = "indirizzo", length = 200)
    private String indirizzo;

    @Column(name = "id_classe")
    private Long idClasse;

    @Column(name = "email_scuola", unique = true, length = 100)
    private String emailScuola;

    @Column(name = "email_personale", length = 100)
    private String emailPersonale;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "telefono_emergenza", length = 20)
    private String telefonoEmergenza;

    @Column(name = "attivo")
    private Boolean attivo;
}
