package com.jugueria.sistemaventas.controller;

import com.jugueria.sistemaventas.model.*;
import com.jugueria.sistemaventas.repository.PedidoRepository;
import com.jugueria.sistemaventas.repository.ProductoRepository;
import com.jugueria.sistemaventas.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mozo")
public class MozoController {

    @Autowired private ProductoRepository productoRepository;
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    @GetMapping("")
    public String home() { return "mozo/index"; }

    // --- 1. CONSULTAR MESAS (Ahora lee la verdad de la BD) ---
    @GetMapping("/mesas")
    public String consultarMesas(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<Cliente> mesas = new ArrayList<>();

        List<Pedido> pedidosPendientes = pedidoRepository.findAll().stream()
                .filter(p -> "Pendiente".equals(p.getEstado()))
                .collect(Collectors.toList());

        for (Usuario u : usuarios) {
            if (u instanceof Cliente) {
                Cliente mesa = (Cliente) u;

                // --- PROTECCIÓN CONTRA NULOS ---
                if (mesa.getEstado() == null) {
                    mesa.setEstado("Disponible"); // Valor por defecto en memoria
                }
                // -------------------------------

                boolean ocupada = pedidosPendientes.stream()
                        .anyMatch(p -> p.getCliente() != null && p.getCliente().getId().equals(mesa.getId()));

                if (ocupada) {
                    mesa.setEstado("Ocupado");
                }

                mesas.add(mesa);
            }
        }

        model.addAttribute("mesas", mesas);
        return "mozo/lista_mesas";
    }

    // --- 2. NUEVO MÉTODO: LIBERAR MESA ---
    // Esto simula que los clientes se fueron y el Mozo limpia la mesa
    @GetMapping("/mesas/liberar/{id}")
    public String liberarMesa(@PathVariable Integer id) {
        Usuario mesa = usuarioRepository.findById(id).orElse(null);
        if (mesa instanceof Cliente) {
            ((Cliente) mesa).setEstado("Disponible"); // Cambiamos estado
            usuarioRepository.save(mesa); // ¡GUARDAMOS EN BD!
        }
        return "redirect:/mozo/mesas";
    }

    // --- 3. PREPARAR FORMULARIO ---
    @GetMapping("/pedido/nuevo")
    public String nuevoPedido(Model model) {
        List<Producto> productos = productoRepository.findAll();

        // Solo enviamos las mesas para que el select las pinte
        // La validación visual se hará en el HTML
        List<Usuario> mesas = usuarioRepository.findAll().stream()
                .filter(u -> u instanceof Cliente)
                .collect(Collectors.toList());

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
        model.addAttribute("mesas", mesas);
        model.addAttribute("pedidoForm", form);
        return "mozo/realizar_pedido";
    }

    // --- 4. GUARDAR PEDIDO (Con Validación de Ocupado) ---
    @PostMapping("/pedido/guardar")
    public String guardarPedido(@ModelAttribute PedidoForm form) {
        Pedido pedido = new Pedido();

        Usuario mesaUsuario = usuarioRepository.findById(form.getClienteId()).orElse(null);

        // VALIDACIÓN DE SEGURIDAD:
        // Si la mesa ya está ocupada, rechazamos el pedido para evitar duplicados
        if (mesaUsuario instanceof Cliente) {
            Cliente mesa = (Cliente) mesaUsuario;
            if ("Ocupado".equalsIgnoreCase(mesa.getEstado())) {
                return "redirect:/mozo/pedido/nuevo?error=ocupada";
            }

            // Si está libre, la ocupamos y guardamos el cambio
            mesa.setEstado("Ocupado");
            usuarioRepository.save(mesa); // ¡PERSISTENCIA IMPORTANTE!

            pedido.setCliente(mesa);
        }

        boolean tieneItems = false;
        for (PedidoForm.ItemForm itemForm : form.getItems()) {
            if (itemForm.getCantidad() != null && itemForm.getCantidad() > 0) {
                Producto prod = productoRepository.findById(itemForm.getProductoId()).orElse(null);
                if (prod != null) {
                    ItemProducto item = new ItemProducto(prod, itemForm.getCantidad(), pedido);
                    pedido.agregarProducto(item);
                    tieneItems = true;
                }
            }
        }

        if (!tieneItems) return "redirect:/mozo/pedido/nuevo?error=vacio";

        pedidoRepository.save(pedido);
        return "redirect:/mozo/pedido/nuevo?exito";
    }
}