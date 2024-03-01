package br.com.gabrieldias.gestao_vagas.exceptions;

public class UserFoundException extends RuntimeException {

    public UserFoundException(String name) {
        super(name);
    }

}
