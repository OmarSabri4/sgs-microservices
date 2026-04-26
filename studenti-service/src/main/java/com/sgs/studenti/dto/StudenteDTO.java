package com.sgs.studenti.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudenteDTO {

    private Long idStudente;

    @NotBlank(message = "Il codice studente è obbligatorio")
    @Size(max = 20)
    private String codiceStudente;

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(max = 50)
    private String nome;

    @NotBlank(message = "Il cognome è obbligatorio")
    @Size(max = 50)
    private String cognome;

    @NotBlank(message = "Il codice fiscale è obbligatorio")
    @Size(min = 16, max = 16, message = "Il codice fiscale deve essere di 16 caratteri")
    private String codiceFiscale;

    private LocalDate dataNascita;

    @Size(max = 200)
    private String indirizzo;

    @NotNull(message = "La classe è obbligatoria")
    private Long idClasse;

    @Email(message = "Formato email scuola non valido")
    private String emailScuola;

    @Email(message = "Formato email personale non valido")
    private String emailPersonale;

    private String telefono;
    private String telefonoEmergenza;
    private Boolean attivo;
}
