package br.com.fiap.mottumap.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.fiap.mottumap.model.entity.Usuario;
import br.com.fiap.mottumap.model.repository.UsuarioRepository;

@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
    private UsuarioRepository usuarioRepository;
	
    @GetMapping("/login")
    public String login() {
        return "/login"; 
    }
    

    @GetMapping("/cadastro")
    public String formCadastro(Model model) {	
        model.addAttribute("usuario", new Usuario());
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String salvarCadastro(@ModelAttribute Usuario usuario, Model model) {
    	
    	if(usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
    		System.out.println("Usuario encontrado");
    		model.addAttribute("mensagem", "Email já cadastrado");
            return "cadastro";
    	}
    	
    	System.out.println("Usuario não encontrado");
        usuario.setSenha("{noop}" + usuario.getSenha()); 
        usuario.setRole("USER");

        usuarioRepository.save(usuario);

        return "redirect:/auth/login";
    }

}