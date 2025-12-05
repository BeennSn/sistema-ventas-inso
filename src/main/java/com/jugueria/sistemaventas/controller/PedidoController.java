package com.jugueria.sistemaventas.controller;

import com.jugueria.sistemaventas.model.ItemProducto;
import com.jugueria.sistemaventas.model.Pedido;
import com.jugueria.sistemaventas.model.Producto;
import com.jugueria.sistemaventas.repository.PedidoRepository;
import com.jugueria.sistemaventas.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PedidoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/pedido/nuevo")
    public String mostrarInterfazPedido(Model model) {
        List<Producto> productos = productoRepository.findAll();

        PedidoForm form = new PedidoForm();
        List<PedidoForm.ItemForm> itemsForm = new ArrayList<>();

        for (Producto p : productos) {
            PedidoForm.ItemForm item = new PedidoForm.ItemForm();
            item.setProductoId(p.getId());
            item.setCantidad(0);
            itemsForm.add(item);
        }
        form.setItems(itemsForm);

        model.addAttribute("productos", productos);
        model.addAttribute("pedidoForm", form);

        return "mozo/realizar_pedido";
    }

    @PostMapping("/pedido/guardar")
    public String registrarPedido(@ModelAttribute PedidoForm form) {
        Pedido pedido = new Pedido();
        boolean tieneProductos = false; // Bandera para saber si hay items

        for (PedidoForm.ItemForm itemForm : form.getItems()) {
            if (itemForm.getCantidad() != null && itemForm.getCantidad() > 0) {
                Producto prod = productoRepository.findById(itemForm.getProductoId()).orElse(null);
                if (prod != null) {
                    ItemProducto item = new ItemProducto(prod, itemForm.getCantidad(), pedido);
                    pedido.agregarProducto(item);
                    tieneProductos = true;
                }
            }
        }

        // --- VALIDACIÓN: Si no hay productos, devolvemos error ---
        if (!tieneProductos) {
            return "redirect:/pedido/nuevo?error=vacio";
        }

        pedidoRepository.save(pedido);
        return "redirect:/pedido/nuevo?exito";
    }
}