package com.jugueria.sistemaventas.repository;

import com.jugueria.sistemaventas.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    Page<Producto> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}