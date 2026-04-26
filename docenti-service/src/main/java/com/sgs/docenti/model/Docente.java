package com.sgs.docenti.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Docenti", schema = "sgs_core")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_docente")
    private Long idDocente;

    @Column(name = "codice_docente", nullable = false, unique = true, length = 20)
    private String codiceDocente;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "cognome", nullable = false, length = 50)
    private String cognome;

    @Column(name = "codice_fiscale", nullable = false, unique = true, length = 16)
    private String codiceFiscale;

    @Column(name = "email_istituzionale", nullable = false, unique = true, length = 100)
    private String emailIstituzionale;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Column(name = "data_assunzione")
    private LocalDate dataAssunzione;

    @Column(name = "stato", length = 20)
    private String stato;
}
