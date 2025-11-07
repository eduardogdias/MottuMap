package br.com.fiap.mottumap.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.mottumap.model.entity.Usuario;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);


    @Procedure(procedureName = "atualizar_email_usuario")
    void atualizarEmailUsuario(@Param("p_id") Integer id, @Param("p_email") String email);



}
