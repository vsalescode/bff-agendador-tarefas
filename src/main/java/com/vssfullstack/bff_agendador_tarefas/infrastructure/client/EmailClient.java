package com.vssfullstack.bff_agendador_tarefas.infrastructure.client;



import com.vssfullstack.bff_agendador_tarefas.business.dto.out.TarefasResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notificacao", url = "${notificacao.url}")
public interface EmailClient {

    void enviarEmail(@RequestBody TarefasResponseDTO tarefasDTO);
}
