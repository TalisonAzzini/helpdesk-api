package com.helpdesk.helpdesk_api.enums;

import lombok.Getter;

@Getter
public enum Cargo {
    TECNICO("ROLE_TECNICO"),
    ASSISTENTE("ROLE_ASSISTENTE"),
    ANALISTA("ROLE_ANALISTA"),
    SUPERVISOR("ROLE_SUPERVISOR"),
    GERENTE("ROLE_GERENTE"),
    DIRETOR("ROLE_DIRETOR"),
    ADM("ROLE_ADM"),
    ROOT("ROLE_ROOT");

    private final String role;

    Cargo(String role) {
        this.role = role;
    }

}