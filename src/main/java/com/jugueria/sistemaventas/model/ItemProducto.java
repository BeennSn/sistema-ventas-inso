package com.jugueria.sistemaventas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "item_producto")
public class ItemProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // ID interno para la base de datos

    private int cantidad; // Diagrama dice 'int'

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto; // Diagrama dice 'producto: Producto'

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido; // Referencia necesaria para guardar en BD

    // Constructor
    public ItemProducto() {}

    public ItemProducto(Producto producto, int cantidad, Pedido pedido) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.pedido = pedido;
    }

    // Método del diagrama
    public float getSubTotal() {
        return this.cantidad * this.producto.getPrecio();
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
}