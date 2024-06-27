package br.com.gabrieldias.gestao_vagas.exceptions;
import org.springframework.security.core.AuthenticationException;

public class AuthenticationExceptionImpl extends AuthenticationException {
    public AuthenticationExceptionImpl(String msg) {
        super(msg);
    }
}
