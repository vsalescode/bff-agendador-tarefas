package com.vssfullstack.bff_agendador_tarefas.business.dto.in;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.vssfullstack.bff_agendador_tarefas.business.enums.StatusNotificacaoEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TarefasRequestDTO {


    private String nomeTarefa;
    private String descricao;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy HH:mm:ss")
    private LocalDateTime dataEvento;

}
