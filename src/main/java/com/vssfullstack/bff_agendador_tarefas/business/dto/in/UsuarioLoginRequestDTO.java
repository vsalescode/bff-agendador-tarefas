package com.vssfullstack.bff_agendador_tarefas.business.dto.in;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioLoginRequestDTO {

    private String email;
    private String senha;
}
