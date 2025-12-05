package com.jugueria.sistemaventas.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String estado; // Diagrama dice 'String'

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemProducto> listaItems = new ArrayList<>(); // Diagrama: 'listaItems: List<ItemProducto>'

    public Pedido() {
        this.estado = "Pendiente"; // Estado inicial por defecto
    }

    // --- MÉTODOS EXACTOS DEL DIAGRAMA DE CLASES ---

    public void agregarProducto(ItemProducto item) {
        this.listaItems.add(item);
        item.setPedido(this); // Vincular en BD
    }

    public float calcularTotal() {
        float total = 0;
        for (ItemProducto item : listaItems) {
            total += item.getSubTotal();
        }
        return total;
    }

    // Estos métodos están en el diagrama, los dejamos definidos aunque la lógica vaya en el Controller
    public void emitirComprobante() { /* Lógica futura */ }
    public void verificarPago() { /* Lógica futura */ }
    public void obtenerDetalle() { /* Lógica futura */ }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public List<ItemProducto> getListaItems() { return listaItems; }
    public void setListaItems(List<ItemProducto> listaItems) { this.listaItems = listaItems; }
}