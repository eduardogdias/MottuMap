package br.com.fiap.mottumap.model.service;

import br.com.fiap.mottumap.model.dto.usuario.UsuarioMapper;
import br.com.fiap.mottumap.model.dto.usuario.UsuarioRequestDTO;
import br.com.fiap.mottumap.model.dto.usuario.UsuarioResponseDTO;
import br.com.fiap.mottumap.model.entity.Usuario;
import br.com.fiap.mottumap.model.repository.UsuarioRepository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.*;


class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private UsuarioMapper usuarioMapper;

    @InjectMocks
    private UsuarioService usuarioService;


    private List<Usuario> usuarios;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deveListarTodosOsUsuarior() {

        // arrange
        Usuario u1 = new Usuario(1, "ADMIN", "admin@mottu.com", "{noop}123456", "ADMIN");
        Usuario u2 = new Usuario(2, "USER", "user@mottu.com", "{noop}123456", "USER");

        List<Usuario> usuarios = Arrays.asList(u1, u2);

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        when(usuarioMapper.toDTO(u1)).thenReturn(new UsuarioResponseDTO(u1.getId(), u1.getNome(), u1.getEmail()));
        when(usuarioMapper.toDTO(u2)).thenReturn(new UsuarioResponseDTO(u2.getId(), u2.getNome(), u2.getEmail()));

        // act
        List<UsuarioResponseDTO> lista = usuarioService.listar();

        // assert
        assertEquals(2, lista.size());
        assertEquals("admin@mottu.com", lista.get(0).email());
        assertEquals("USER", lista.get(1).nome());
    }


    @Test
    public void deveRetornarOptionalVazioAoBuscarUsuarioNaoCadastrado() {

        // arrange
        when(usuarioRepository.findById(3)).thenReturn(Optional.empty());

        // act
        Optional<Usuario> result = usuarioService.buscarPorId(3);

        // assert
        assertTrue(result.isEmpty());
        verify(usuarioRepository, times(1)).findById(eq(3));
    }


    @Test
    public void deveConfigurarStatusDoNovoUsuarioComoUser() {
        // arrange
        UsuarioRequestDTO dto = new UsuarioRequestDTO("Juliano Testador", "juliano.teste@mottu.com", "{noop}654321");
        Usuario u3 = new Usuario(3, dto.nome(), dto.email(), dto.senha(), "USER");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(u3);

        // act
        Usuario result = usuarioService.criar(dto);

        // assert
        assertEquals("USER", result.getRole());
        assertNotEquals("ADMIN", result.getRole());

    }


    @Test
    public void deveLancarExcecaoDeUsuarioNaoEncotrado() {
        when(usuarioRepository.findById(anyInt())).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> usuarioService.buscarEntityPorId(5));

        assertEquals("Usuário não encontrado", exception.getMessage());
    }


    @Test
    public void deveAtualizarUmUsuarioEncontrado() {
        Integer id = 1;
        Usuario usuarioExistente = new Usuario(id, "Antigo", "antigo@mottu.com", "123", "USER");
        UsuarioRequestDTO dto = new UsuarioRequestDTO("Novo", "novo@mottu.com", "321");
        UsuarioResponseDTO dtoResposta = new UsuarioResponseDTO(id, dto.nome(), dto.email());

        when(usuarioRepository.findById(usuarioExistente.getId())).thenReturn(Optional.of(usuarioExistente));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioExistente);
        when(usuarioMapper.toDTO(usuarioExistente)).thenReturn(dtoResposta);

        UsuarioResponseDTO result = usuarioService.atualizar(id, dto);

        assertEquals(dtoResposta, result);
        assertEquals("Novo", usuarioExistente.getNome());
        assertEquals("novo@mottu.com", usuarioExistente.getEmail());
        assertEquals("321", usuarioExistente.getSenha());
    }


    @Test
    public void deveDeletarUsuarioComSucesso() {
        // arrange
        Integer id = 1;
        Usuario usuario = new Usuario(id, "Juliano", "juliano@mottu.com", "123", "USER");
        UsuarioResponseDTO dto = new UsuarioResponseDTO(id, usuario.getNome(), usuario.getEmail());

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toDTO(usuario)).thenReturn(dto);

        // act
        UsuarioResponseDTO result = usuarioService.deletar(id);

        // assert
        assertEquals(dto, result);
        verify(usuarioRepository, times(1)).findById(id);
        verify(usuarioRepository, times(1)).deleteById(id);

    }
}