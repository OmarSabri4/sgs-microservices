package com.sgs.docenti.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class DocenteDTO {

    private Long idDocente;

    @NotBlank(message = "Il codice docente è obbligatorio")
    @Size(max = 20)
    private String codiceDocente;

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(max = 50)
    private String nome;

    @NotBlank(message = "Il cognome è obbligatorio")
    @Size(max = 50)
    private String cognome;

    @NotBlank(message = "Il codice fiscale è obbligatorio")
    @Size(min = 16, max = 16, message = "Il codice fiscale deve essere di 16 caratteri")
    private String codiceFiscale;

    @NotBlank(message = "L'email istituzionale è obbligatoria")
    @Email(message = "Formato email non valido")
    private String emailIstituzionale;

    private String telefono;
    private LocalDate dataAssunzione;
    private String stato;
}
