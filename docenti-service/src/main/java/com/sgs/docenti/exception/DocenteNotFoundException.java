package com.sgs.docenti.exception;

public class DocenteNotFoundException extends RuntimeException {
    public DocenteNotFoundException(Long id) {
        super("Docente non trovato con id: " + id);
    }
    public DocenteNotFoundException(String msg) {
        super(msg);
    }
}
