package br.com.fiap.mottumap.controller.api;

import java.util.List;
import java.util.Optional;

import br.com.fiap.mottumap.model.dto.usuario.UsuarioRequestEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.mottumap.model.dto.usuario.UsuarioMapper;
import br.com.fiap.mottumap.model.dto.usuario.UsuarioRequestDTO;
import br.com.fiap.mottumap.model.dto.usuario.UsuarioResponseDTO;
import br.com.fiap.mottumap.model.entity.Usuario;
import br.com.fiap.mottumap.model.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@SuppressWarnings("all")
public class UsuarioController {

	@Autowired
	public UsuarioService usuarioService;
	
	@Autowired
	public UsuarioMapper usuarioMapper;
	
	@GetMapping
	public ResponseEntity<List<UsuarioResponseDTO>> listar(){
		return ResponseEntity.ok(usuarioService.listar());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity buscarPorId(@PathVariable Integer id){
		Optional<Usuario> usu = usuarioService.buscarPorId(id);
		if(usu.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
		} 
		else {
			return ResponseEntity.ok(usuarioMapper.toDTO(usu));
		}
	}
	
	
	@PostMapping
	public ResponseEntity criar(@RequestBody @Valid UsuarioRequestDTO dto){
		Usuario usu = usuarioService.criar(dto);
		if(usu != null) return ResponseEntity.status(HttpStatus.CREATED).body(usuarioMapper.toDTO(usu)); 
		else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro na requisição");		
	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity editar(@PathVariable Integer id, @RequestBody @Valid UsuarioRequestDTO dto) {
		return ResponseEntity.ok(usuarioService.atualizar(id, dto));
	}
	
	@DeleteMapping("/{id}")
	public UsuarioResponseDTO deletar(@PathVariable Integer id) {
		return usuarioService.deletar(id);
	}

    // para a procedure:
    @PutMapping("/{id}/email")
    public ResponseEntity<String> atualizarEmail(@PathVariable Integer id, @RequestParam String novoEmail) {
        usuarioService.atualizarEmail(id, novoEmail);
        return ResponseEntity.ok("E-mail atualizado com sucesso!");
    }
}
