package br.com.fiap.mottumap.model.dto.usuario;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.fiap.mottumap.model.entity.Usuario;

@Component
public class UsuarioMapper {

	public UsuarioResponseDTO toDTO(Usuario usu) {
		UsuarioResponseDTO dto = new UsuarioResponseDTO(
				usu.getId(),
				usu.getNome(),
				usu.getEmail());
		return dto;
	}
	
	public UsuarioResponseDTO toDTO(Optional<Usuario> optional) {
		UsuarioResponseDTO dto = new UsuarioResponseDTO(
				optional.get().getId(),
				optional.get().getNome(),
				optional.get().getEmail());
		return dto;
	}
	
	
	public Usuario toEntity(UsuarioRequestDTO dto) {
		Usuario usu = new Usuario(dto);
		return usu;
	}
}
