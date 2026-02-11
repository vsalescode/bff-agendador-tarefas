package com.vssfullstack.bff_agendador_tarefas.infrastructure.client;


import com.vssfullstack.bff_agendador_tarefas.business.dto.in.EnderecoRequestDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.in.TelefoneRequestDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.in.UsuarioLoginRequestDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.in.UsuarioRequestDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.out.EnderecoResponseDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.out.TelefoneResponseDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.out.UsuarioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario", url = "${usuario.url}")
public interface UsuarioClient {

    @GetMapping("/usuario")
    UsuarioResponseDTO buscarUsuarioPorEmail(@RequestParam("email") String email,
                                             @RequestHeader("Authorization") String token);


    @PostMapping
    UsuarioResponseDTO salvarUsuario(@RequestBody UsuarioRequestDTO usuarioDTO);


    @PostMapping("/login")
    String login(@RequestBody UsuarioLoginRequestDTO usuarioDTO);


    @DeleteMapping("/{email}")
    void deletaUsuarioPorEmail(@PathVariable String email,
                               @RequestHeader("Authorization") String token);

    // Endpoint para atualizar os dados do usuário autenticado
    @PutMapping
    UsuarioResponseDTO atualizaDadosUsuario(@RequestBody UsuarioRequestDTO usuarioDTO,
                                            @RequestHeader("Authorization") String token);

    @PutMapping("/endereco")
    EnderecoResponseDTO atualizaDadosEndereco(@RequestBody EnderecoRequestDTO enderecoDTO,
                                              @RequestParam("id") Long id,
                                              @RequestHeader("Authorization") String token);

    // Endpoint para atualizar um telefone pelo ID
    @PutMapping("/telefone")
    TelefoneResponseDTO atualizaDadosTelefone(@RequestBody TelefoneRequestDTO telefoneDTO,
                                              @RequestParam("id") Long id,
                                              @RequestHeader("Authorization") String token);

    // Endpoint para cadastrar um novo endereço para o usuário autenticado
    @PostMapping("/endereco")
    EnderecoResponseDTO cadastraEndereco(@RequestBody EnderecoRequestDTO enderecoDTO,
                                         @RequestHeader("Authorization") String token);


    @PostMapping("/telefone")
    TelefoneResponseDTO cadastraTelefone(@RequestBody TelefoneRequestDTO telefoneDTO,
                                         @RequestHeader("Authorization") String token);
}
