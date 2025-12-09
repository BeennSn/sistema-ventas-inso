package com.jugueria.sistemaventas.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CAJERO")
public class Cajero extends Usuario {

    private String estado; // Diagrama: estado

    public Cajero() {}

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    // Diagrama: Métodos
    public void emitirComprobante() {}
    public void agregarProducto() {}
    public void verificarPago() {}
    public void obtenerDetalle() {}
    public void calcularTotal() {}
}