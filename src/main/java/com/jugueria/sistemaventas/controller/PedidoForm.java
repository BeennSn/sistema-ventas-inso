package com.jugueria.sistemaventas.controller;

import java.util.List;

public class PedidoForm {
    private List<ItemForm> items;

    // Getters y Setters
    public List<ItemForm> getItems() { return items; }
    public void setItems(List<ItemForm> items) { this.items = items; }

    // Clase estática interna para cada fila de la tabla
    public static class ItemForm {
        private Integer productoId;
        private Integer cantidad;

        // Getters y Setters
        public Integer getProductoId() { return productoId; }
        public void setProductoId(Integer productoId) { this.productoId = productoId; }
        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    }
}