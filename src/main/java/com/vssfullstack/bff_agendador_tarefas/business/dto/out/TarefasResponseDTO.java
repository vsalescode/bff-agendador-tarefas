package com.vssfullstack.bff_agendador_tarefas.business.dto.out;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.vssfullstack.bff_agendador_tarefas.business.enums.StatusNotificacaoEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarefasResponseDTO {

    private String id;
    private String nomeTarefa;
    private String descricao;
    private String emailUsuario;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy HH:mm:ss")
    private LocalDateTime dataCriacao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy HH:mm:ss")
    private LocalDateTime dataEvento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy HH:mm:ss")
    private LocalDateTime dataAlteracao;


    private StatusNotificacaoEnum statusNotificacaoEnum;

}
