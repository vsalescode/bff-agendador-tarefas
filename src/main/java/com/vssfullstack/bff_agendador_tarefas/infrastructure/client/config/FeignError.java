package com.vssfullstack.bff_agendador_tarefas.infrastructure.client.config;

import com.vssfullstack.bff_agendador_tarefas.infrastructure.exceptions.BusinessException;
import com.vssfullstack.bff_agendador_tarefas.infrastructure.exceptions.ConflictException;
import com.vssfullstack.bff_agendador_tarefas.infrastructure.exceptions.ResourceNotFoundException;
import com.vssfullstack.bff_agendador_tarefas.infrastructure.exceptions.UnauthorizedException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class FeignError implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {

        String message = extractMessageFromResponse(response);

        switch (response.status()) {
            case 409:
                return new ConflictException(message != null ? message : "Erro de conflito (409)");

            case 404:
                return new ResourceNotFoundException(message != null ? message : "Recurso não encontrado (404)");

            case 401:
            case 403:
                return new UnauthorizedException(message != null ? message : "Acesso negado ou não autorizado");

            default:

                return defaultErrorDecoder.decode(methodKey, response);
        }
    }

    private String extractMessageFromResponse(Response response) {
        try {
            if (response.body() != null) {
                InputStream bodyIs = response.body().asInputStream();
                if (bodyIs != null) {

                    return new String(bodyIs.readAllBytes(), StandardCharsets.UTF_8);
                }
            }
        } catch (IOException e) {

            return null;
        }
        return null;
    }
}