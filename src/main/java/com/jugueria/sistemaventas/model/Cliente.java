package com.jugueria.sistemaventas.model;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CLIENTE")
public class Cliente extends Usuario {
    private String estado; // Disponible / Ocupado

    public Cliente() {}
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public void realizarPedido() {}
}