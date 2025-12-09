package com.jugueria.sistemaventas.repository;
import com.jugueria.sistemaventas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByIdusuario(String idusuario); // Busca por el 'idusuario' (username)
}