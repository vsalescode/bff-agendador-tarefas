package com.vssfullstack.bff_agendador_tarefas.infrastructure.exceptions;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String mensagem) {
        super(mensagem);
    }

}
