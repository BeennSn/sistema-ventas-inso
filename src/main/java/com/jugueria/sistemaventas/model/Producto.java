package com.jugueria.sistemaventas.model;

import jakarta.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // Diagrama: id (int)

    private String nombre;      // Diagrama: nombre
    private float precio;       // Diagrama: precio (float)
    private String descripcion; // Diagrama: descripcion
    private int stock;          // Requerido por interfaz

    public Producto() {}

    // Diagrama: buscarProducto()
    public void buscarProducto() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public float getPrecio() { return precio; }
    public void setPrecio(float precio) { this.precio = precio; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}