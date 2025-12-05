package com.jugueria.sistemaventas.repository;

import com.jugueria.sistemaventas.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    // Método para buscar productos (opcional, por si quieres el buscador)
    // List<Producto> findByNombreContainingIgnoreCase(String nombre);
}