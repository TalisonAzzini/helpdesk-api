package com.helpdesk.helpdesk_api.infra.exception;

public class UsuarioDuplicadoException extends RuntimeException{

    public UsuarioDuplicadoException() {
        super("Email já cadastrado!");
    }
}
