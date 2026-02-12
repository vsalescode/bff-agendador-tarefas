package com.vssfullstack.bff_agendador_tarefas.business;


import com.vssfullstack.bff_agendador_tarefas.business.dto.in.UsuarioLoginRequestDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.out.TarefasResponseDTO;
import com.vssfullstack.bff_agendador_tarefas.business.enums.StatusNotificacaoEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CronService {
    private final TarefasService tarefasService;
    private final EmailService emailService;
    private final UsuarioService usuarioService;

    @Value("${usuario.email}")
    private String email;

    @Value("${usuario.senha}")
    private String senha;

    @Scheduled(cron = "${cron.horario}")
    public void BuscaTarefasProximaHora() {
        String token = login(converterParaRequestDTO());
        log.info("Iniciada a busca de tarefas");

        LocalDateTime horaAtual = LocalDateTime.now();
        LocalDateTime horaLimite = horaAtual.plusHours(1);


        List<TarefasResponseDTO> listaDeTarefas = tarefasService.buscaTarefasAgendadasPorPeriodo(horaAtual, horaLimite, token);


        log.info("Quantidade de tarefas encontradas: {}", listaDeTarefas.size());

        listaDeTarefas.forEach(tarefa -> {

            try {
                emailService.enviaEmail(tarefa);
                log.info("Email enviado para o usuario: {}", tarefa.getEmailUsuario());

                tarefasService.alteraStatusTarefa(StatusNotificacaoEnum.NOTIFICADO, tarefa.getId(), token);
            } catch (Exception e) {
                log.error("Erro ao processar tarefa id: {}. Erro: {}", tarefa.getId(), e.getMessage());

            }
        });

        log.info("Finalizado a busca e notificação de tarefas");
    }


    public String login(UsuarioLoginRequestDTO loginRequestDTO) {
        return usuarioService.loginUsuario(loginRequestDTO);

    }

    public UsuarioLoginRequestDTO converterParaRequestDTO() {
        return UsuarioLoginRequestDTO.builder()
                .email(email)
                .senha(senha)
                .build();
    }
}
