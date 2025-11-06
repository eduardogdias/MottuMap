package br.com.fiap.mottumap.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fiap.mottumap.model.dto.usuario.UsuarioMapper;
import br.com.fiap.mottumap.model.dto.usuario.UsuarioRequestDTO;
import br.com.fiap.mottumap.model.dto.usuario.UsuarioResponseDTO;
import br.com.fiap.mottumap.model.entity.Usuario;
import br.com.fiap.mottumap.model.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;
    
    public List<UsuarioResponseDTO> listar(){
    	List<Usuario> usuarios = usuarioRepository.findAll();
    	List<UsuarioResponseDTO> resposta = new ArrayList<>();
    	
    	for (Usuario usuario : usuarios) {
    		
			resposta.add(usuarioMapper.toDTO(usuario));
		} 	
    	return resposta;
    			
    }
    
    public Usuario buscarEntityPorId(Integer id) {
    	return usuarioRepository.findById(id)
    			.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
    
    
    public Optional<Usuario> buscarPorId(Integer id) {    	
    	return usuarioRepository.findById(id);
    }
    
    public Usuario criar(UsuarioRequestDTO dto) {
    	Usuario usu = new Usuario(dto);
    	usu.setRole("USER");
    	return usuarioRepository.save(usu);
    }
    
    public UsuarioResponseDTO atualizar(Integer id, UsuarioRequestDTO dto) {
    	Usuario usu = buscarEntityPorId(id);
    	usu.setNome(dto.nome());
    	usu.setEmail(dto.email());
    	usu.setSenha(dto.senha());
    	usuarioRepository.save(usu);   
    	return usuarioMapper.toDTO(usu);
    }
	    
    public UsuarioResponseDTO deletar(Integer id) {
    	Usuario usu = buscarEntityPorId(id);
    	
    	usuarioRepository.deleteById(id);
    	
    	return usuarioMapper.toDTO(usu);
    }
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails usuario = usuarioRepository.findByEmail(username)
	            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
		return usuario;
	}

    
}

