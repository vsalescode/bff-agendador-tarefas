package com.vssfullstack.bff_agendador_tarefas.controller;

import com.vssfullstack.bff_agendador_tarefas.business.TarefasService;
import com.vssfullstack.bff_agendador_tarefas.business.dto.in.TarefasRequestDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.out.TarefasResponseDTO;
import com.vssfullstack.bff_agendador_tarefas.business.enums.StatusNotificacaoEnum;
import com.vssfullstack.bff_agendador_tarefas.infrastructure.config.SecurityConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
@Tag(name = "Tarefas", description = "Gerenciamento de tarefas e eventos")
@SecurityRequirement(name = SecurityConfig.SECURITY_SCHEME)
public class TarefasController {

    private final TarefasService tarefasService;

    @PostMapping
    @Operation(summary = "Salvar tarefa", description = "Cria e agenda uma nova tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa salva com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<TarefasResponseDTO> salvarTarefas(@RequestBody TarefasRequestDTO tarefasDTO,
                                                            @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefasService.salvarTarefas(token, tarefasDTO));
    }

    @GetMapping("/eventos")
    @Operation(summary = "Buscar tarefas por período", description = "Lista tarefas agendadas dentro de um intervalo de datas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<TarefasResponseDTO>> buscaListaDeTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFinal,
            @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefasService.buscaTarefasAgendadasPorPeriodo(dataInicial, dataFinal, token));
    }

    @GetMapping
    @Operation(summary = "Buscar tarefas do usuário", description = "Lista todas as tarefas associadas ao usuário logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de tarefas retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<TarefasResponseDTO>> buscaListaDeTarefasPorEmail(@RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefasService.buscarTarefasPorEmail(token));
    }

    @PatchMapping
    @Operation(summary = "Alterar status da notificação", description = "Atualiza o status de notificação de uma tarefa específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<TarefasResponseDTO> alteraStatusNotificacao(@RequestParam("status") StatusNotificacaoEnum statusNotificacaoEnum,
                                                                      @RequestParam String id,
                                                                      @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefasService.alteraStatusTarefa(statusNotificacaoEnum, id, token));
    }

    @PutMapping
    @Operation(summary = "Atualizar tarefa", description = "Atualiza os dados de uma tarefa existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<TarefasResponseDTO> atualizarTarefas(@RequestBody TarefasRequestDTO tarefasDTO,
                                                               @RequestParam("id") String id,
                                                               @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(tarefasService.atualizarTarefas(tarefasDTO, id, token));
    }

    @DeleteMapping
    @Operation(summary = "Deletar tarefa", description = "Remove uma tarefa do sistema pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa deletada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam("id") String id,
                                                  @RequestHeader(name = "Authorization", required = false) String token) {
        tarefasService.deletaTarefaPorId(id, token);

        return ResponseEntity.noContent().build();
    }
}