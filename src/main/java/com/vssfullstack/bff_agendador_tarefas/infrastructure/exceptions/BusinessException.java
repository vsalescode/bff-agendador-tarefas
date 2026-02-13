package com.vssfullstack.bff_agendador_tarefas.infrastructure.exceptions;

public class BusinessException extends RuntimeException {

    public BusinessException(String mensagem) {
        super(mensagem);
    }

}
