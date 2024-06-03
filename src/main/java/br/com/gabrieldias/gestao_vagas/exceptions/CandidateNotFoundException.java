package br.com.gabrieldias.gestao_vagas.exceptions;

public class CandidateNotFoundException extends RuntimeException {

    public CandidateNotFoundException(String message) {
        super(message);
    }
}
