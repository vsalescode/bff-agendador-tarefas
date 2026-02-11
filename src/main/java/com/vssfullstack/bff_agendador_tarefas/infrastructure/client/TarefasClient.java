package com.vssfullstack.bff_agendador_tarefas.infrastructure.client;


import com.vssfullstack.bff_agendador_tarefas.business.dto.in.TarefasRequestDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.out.TarefasResponseDTO;
import com.vssfullstack.bff_agendador_tarefas.business.enums.StatusNotificacaoEnum;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "agendador-tarefas", url = "${agendador-tarefas.url}")
public interface TarefasClient {

    @PostMapping
    TarefasResponseDTO salvarTarefas(@RequestBody TarefasRequestDTO tarefasDTO,
                                     @RequestHeader("Authorization") String token);

    @GetMapping("/eventos")
    List<TarefasResponseDTO> buscaListaDeTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
            @RequestHeader("Authorization") String token);


    @GetMapping
    List<TarefasResponseDTO> buscaListaDeTarefasPorEmail(@RequestHeader("Authorization") String token);

    @PatchMapping
    TarefasResponseDTO alteraStatusNotificacao(@RequestParam("status") StatusNotificacaoEnum statusNotificacaoEnum,
                                               @RequestParam String id,
                                               @RequestHeader("Authorization") String token);

    @PutMapping
    TarefasResponseDTO atualizarTarefas(@RequestBody TarefasRequestDTO tarefasDTO, @RequestParam("id") String id,
                                        @RequestHeader("Authorization") String token);


    @DeleteMapping
    void deletaTarefaPorId(@RequestParam("id") String id,
                           @RequestHeader("Authorization") String token);
}
