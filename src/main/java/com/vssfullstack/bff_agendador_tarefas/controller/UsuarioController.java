package com.vssfullstack.bff_agendador_tarefas.controller;

import com.vssfullstack.bff_agendador_tarefas.business.UsuarioService;
import com.vssfullstack.bff_agendador_tarefas.business.dto.in.EnderecoRequestDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.in.TelefoneRequestDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.in.UsuarioLoginRequestDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.in.UsuarioRequestDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.out.EnderecoResponseDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.out.TelefoneResponseDTO;
import com.vssfullstack.bff_agendador_tarefas.business.dto.out.UsuarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuário", description = "Cadastro, login e gerenciamento de dados de usuários")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    @Operation(summary = "Salvar usuário", description = "Cria um novo cadastro de usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Usuário já cadastrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UsuarioResponseDTO> salvarUsuario(@RequestBody UsuarioRequestDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    @Operation(summary = "Login de usuário", description = "Autentica o usuário e retorna o token de acesso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public String login(@RequestBody UsuarioLoginRequestDTO usuarioDTO) {
        return usuarioService.loginUsuario(usuarioDTO);
    }

    @GetMapping
    @Operation(summary = "Buscar usuário por email", description = "Retorna os dados detalhados de um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Proibido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UsuarioResponseDTO> buscarUsuarioPorEmail(@RequestParam("email") String email,
                                                                    @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(usuarioService.buscarUsuarioPorEmail(email, token));
    }

    @DeleteMapping("/{email}")
    @Operation(summary = "Deletar usuário", description = "Remove o cadastro de um usuário através do email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Proibido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deletaUsuarioPorEmail(@PathVariable String email,
                                                      @RequestHeader(name = "Authorization", required = false) String token) {
        usuarioService.deletaUsuarioPorEmail(email, token);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary = "Atualizar dados do usuário", description = "Atualiza as informações principais do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Proibido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<UsuarioResponseDTO> atualizaDadosUsuario(@RequestBody UsuarioRequestDTO usuarioDTO,
                                                                   @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(usuarioService.atualizaDadosUsuario(token, usuarioDTO));
    }

    @PutMapping("/endereco")
    @Operation(summary = "Atualizar endereço", description = "Atualiza um endereço específico associado ao usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Proibido"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<EnderecoResponseDTO> atualizaDadosEndereco(@RequestBody EnderecoRequestDTO enderecoDTO,
                                                                     @RequestParam("id") Long id,
                                                                     @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(usuarioService.atualizaDadosEndereco(id, enderecoDTO, token));
    }

    @PutMapping("/telefone")
    @Operation(summary = "Atualizar telefone", description = "Atualiza um telefone específico associado ao usuário pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Telefone atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Proibido"),
            @ApiResponse(responseCode = "404", description = "Telefone não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<TelefoneResponseDTO> atualizaDadosTelefone(@RequestBody TelefoneRequestDTO telefoneDTO,
                                                                     @RequestParam("id") Long id,
                                                                     @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(usuarioService.atualizaDadosTelefone(id, telefoneDTO, token));
    }

    @PostMapping("/endereco")
    @Operation(summary = "Cadastrar novo endereço", description = "Adiciona um novo endereço à lista de endereços do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço cadastrado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Proibido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<EnderecoResponseDTO> cadastraEndereco(@RequestBody EnderecoRequestDTO enderecoDTO,
                                                                @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(usuarioService.cadastraNovoEndereco(token, enderecoDTO));
    }

    @PostMapping("/telefone")
    @Operation(summary = "Cadastrar novo telefone", description = "Adiciona um novo telefone à lista de contatos do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Telefone cadastrado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Proibido"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<TelefoneResponseDTO> cadastraTelefone(@RequestBody TelefoneRequestDTO telefoneDTO,
                                                                @RequestHeader(name = "Authorization", required = false) String token) {
        return ResponseEntity.ok(usuarioService.cadastraNovoTelefone(token, telefoneDTO));
    }
}