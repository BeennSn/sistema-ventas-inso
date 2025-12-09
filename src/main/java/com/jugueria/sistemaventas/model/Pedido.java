package com.jugueria.sistemaventas.model;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "pedido")
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String estado;
    @Column(name = "fecha_emision") @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente; // <--- LA MESA

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemProducto> listaItems = new ArrayList<>();

    public Pedido() { this.estado = "Pendiente"; this.fechaEmision = new Date(); }

    public void agregarProducto(ItemProducto item) {
        this.listaItems.add(item);
        item.setPedido(this);
    }
    public float calcularTotal() {
        float total = 0;
        for (ItemProducto item : listaItems) total += item.getSubTotal();
        return total;
    }

    // Getters Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public Date getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(Date fechaEmision) { this.fechaEmision = fechaEmision; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public List<ItemProducto> getListaItems() { return listaItems; }
    public void setListaItems(List<ItemProducto> listaItems) { this.listaItems = listaItems; }
}