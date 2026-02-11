package com.vssfullstack.bff_agendador_tarefas.business;

import com.vssfullstack.bff_agendador_tarefas.business.dto.out.TarefasResponseDTO;
import com.vssfullstack.bff_agendador_tarefas.infrastructure.client.EmailClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class EmailService {

    private final EmailClient emailClient;

    public void enviaEmail(TarefasResponseDTO tarefasResponseDTO) {
        emailClient.enviarEmail(tarefasResponseDTO);
    }

}
