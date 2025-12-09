package com.jugueria.sistemaventas.controller;
import java.util.List;

public class PedidoForm {
    private Integer clienteId; // <--- NUEVO: Para seleccionar la mesa
    private List<ItemForm> items;

    // Getters y Setters
    public Integer getClienteId() { return clienteId; }
    public void setClienteId(Integer clienteId) { this.clienteId = clienteId; }
    public List<ItemForm> getItems() { return items; }
    public void setItems(List<ItemForm> items) { this.items = items; }

    public static class ItemForm {
        private Integer productoId;
        private Integer cantidad;
        // Getters Setters
        public Integer getProductoId() { return productoId; }
        public void setProductoId(Integer productoId) { this.productoId = productoId; }
        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    }
}