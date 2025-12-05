package com.jugueria.sistemaventas.repository;

import com.jugueria.sistemaventas.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    // Aquí podemos añadir métodos para buscar pedidos por estado ("Pendiente") más adelante
}