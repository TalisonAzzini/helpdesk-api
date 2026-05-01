package com.helpdesk.helpdesk_api.infra.exceptions;

public class UsuarioDuplicadoException extends RuntimeException{

    public UsuarioDuplicadoException() {
        super("Email já cadastrado!");
    }
}
