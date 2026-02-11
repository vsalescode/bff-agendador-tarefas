package com.vssfullstack.bff_agendador_tarefas.business;

import com.vssfullstack.bff_agendador_tarefas.business.dto.in.TarefasRequestDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.out.TarefasResponseDTO;
import com.vssfullstack.bff_agendador_tarefas.business.enums.StatusNotificacaoEnum;
import com.vssfullstack.bff_agendador_tarefas.infrastructure.client.TarefasClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class TarefasService {

    private final TarefasClient tarefasClient;

    public TarefasResponseDTO salvarTarefas(String token, TarefasRequestDTO tarefasDTO) {
     return tarefasClient.salvarTarefas(tarefasDTO, token);
    }

    public List<TarefasResponseDTO> buscaTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal, String token) {
        return tarefasClient.buscaListaDeTarefasPorPeriodo(dataInicial, dataFinal, token);
    }


    public List<TarefasResponseDTO> buscarTarefasPorEmail(String token) {
        return tarefasClient.buscaListaDeTarefasPorEmail(token);
    }

    public TarefasResponseDTO alteraStatusTarefa(StatusNotificacaoEnum statusNotificacaoEnum, String id, String token) {
        return tarefasClient.alteraStatusNotificacao(statusNotificacaoEnum, id, token);


    }

    public TarefasResponseDTO atualizarTarefas(TarefasRequestDTO tarefasDTO, String id, String token) {
      return tarefasClient.atualizarTarefas(tarefasDTO, id, token);


    }

    public void deletaTarefaPorId(String id, String token) {
       tarefasClient.deletaTarefaPorId(id , token);
    }


}
