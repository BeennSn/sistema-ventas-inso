package com.jugueria.sistemaventas.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MOZO")
public class Mozo extends Usuario {

    private String estado; // Diagrama: estado

    public Mozo() {}

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    // Diagrama: operation()
    public void operation() {
        // Lógica del negocio
    }
}