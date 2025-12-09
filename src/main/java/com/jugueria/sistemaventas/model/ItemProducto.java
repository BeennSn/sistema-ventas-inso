package com.jugueria.sistemaventas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "item_producto")
public class ItemProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int cantidad; // Diagrama: 'cantidad: int'

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto; // Diagrama: 'producto: Producto'

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public ItemProducto() {}

    public ItemProducto(Producto producto, int cantidad, Pedido pedido) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.pedido = pedido;
    }

    // Método del diagrama: getSubTotal()
    public float getSubTotal() {
        return this.cantidad * this.producto.getPrecio();
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
}