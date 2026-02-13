package com.vssfullstack.bff_agendador_tarefas.infrastructure.exceptions;

public class ConflictException extends RuntimeException {

    public ConflictException(String mensagem) {
        super(mensagem);
    }

}
