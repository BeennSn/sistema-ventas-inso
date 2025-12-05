package com.jugueria.sistemaventas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private float precio;
    private String descripcion;

    // --- NUEVO ATRIBUTO ---
    private int stock;

    public Producto() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public float getPrecio() { return precio; }
    public void setPrecio(float precio) { this.precio = precio; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    // --- NUEVOS GETTER Y SETTER ---
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public void buscarProducto() {}
}