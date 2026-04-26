package com.sgs.studenti.exception;

public class StudenteNotFoundException extends RuntimeException {
    public StudenteNotFoundException(Long id) {
        super("Studente non trovato con id: " + id);
    }
    public StudenteNotFoundException(String msg) {
        super(msg);
    }
}
