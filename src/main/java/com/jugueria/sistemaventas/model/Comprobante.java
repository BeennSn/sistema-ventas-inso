package com.jugueria.sistemaventas.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "comprobante")
public class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tipo;        // Diagrama: 'tipo'
    private String estado;      // Diagrama: 'estado'
    private String cliente;     // Diagrama: 'cliente'

    @Column(name = "fecha_emision")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision;  // Diagrama: 'fechaEmision'

    @Column(name = "metodo_pago")
    private String metodoPago;  // Diagrama: 'metodoPago'

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;      // Diagrama: 'pedido'

    public Comprobante() {}

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getCliente() { return cliente; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public Date getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(Date fechaEmision) { this.fechaEmision = fechaEmision; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
}