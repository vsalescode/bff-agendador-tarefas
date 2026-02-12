package com.vssfullstack.bff_agendador_tarefas.business;


import com.vssfullstack.bff_agendador_tarefas.business.dto.in.EnderecoRequestDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.in.TelefoneRequestDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.in.UsuarioLoginRequestDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.in.UsuarioRequestDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.out.EnderecoResponseDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.out.TelefoneResponseDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.out.UsuarioResponseDTO;
import com.vssfullstack.bff_agendador_tarefas.infrastructure.client.UsuarioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioClient usuarioClient;


    public UsuarioResponseDTO salvarUsuario(UsuarioRequestDTO usuarioDTO) {
        return usuarioClient.salvarUsuario(usuarioDTO);
    }


    public String loginUsuario(UsuarioLoginRequestDTO usuarioDTO) {
        return usuarioClient.login(usuarioDTO);
    }


    public UsuarioResponseDTO buscarUsuarioPorEmail(String email, String token) {
        return usuarioClient.buscarUsuarioPorEmail(email, token);
    }


    public void deletaUsuarioPorEmail(String email, String token) {
        usuarioClient.deletaUsuarioPorEmail(email, token);

    }


    public UsuarioResponseDTO atualizaDadosUsuario(String token, UsuarioRequestDTO usuarioDTO) {
        return usuarioClient.atualizaDadosUsuario(usuarioDTO, token);
    }


    public EnderecoResponseDTO atualizaDadosEndereco(Long idEndereco, EnderecoRequestDTO enderecoDTO, String token) {
        return usuarioClient.atualizaDadosEndereco(enderecoDTO, idEndereco, token);
    }


    public TelefoneResponseDTO atualizaDadosTelefone(Long idTelefone, TelefoneRequestDTO telefoneDTO, String token) {
        return usuarioClient.atualizaDadosTelefone(telefoneDTO, idTelefone, token);
    }


    public EnderecoResponseDTO cadastraNovoEndereco(String token, EnderecoRequestDTO enderecoDTO) {
        return usuarioClient.cadastraEndereco(enderecoDTO, token);
    }


    public TelefoneResponseDTO cadastraNovoTelefone(String token, TelefoneRequestDTO telefoneDTO) {
        return usuarioClient.cadastraTelefone(telefoneDTO, token);
    }

}
