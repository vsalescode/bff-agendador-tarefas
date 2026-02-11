package com.vssfullstack.bff_agendador_tarefas.business.dto.in;

import com.vssfullstack.bff_agendador_tarefas.business.dto.out.EnderecoResponseDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.out.TelefoneResponseDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioRequestDTO {

    private String nome;
    private String email;
    private String senha;
    private List<EnderecoResponseDTO> enderecos;
    private List<TelefoneResponseDTO> telefones;
}
