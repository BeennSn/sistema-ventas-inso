package com.jugueria.sistemaventas.controller;

import com.jugueria.sistemaventas.model.Cliente;
import com.jugueria.sistemaventas.model.ItemProducto;
import com.jugueria.sistemaventas.model.Pedido;
import com.jugueria.sistemaventas.model.Usuario;
import com.jugueria.sistemaventas.repository.PedidoRepository;
import com.jugueria.sistemaventas.repository.UsuarioRepository; // <--- Importante
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cajero")
public class CajeroController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository; // <--- Necesario para liberar la mesa

    @GetMapping("")
    public String home() {
        return "cajero/home";
    }

    @GetMapping("/verificar")
    public String verificarPedidos(Model model) {
        List<Pedido> pendientes = pedidoRepository.findAll().stream()
                .filter(p -> "Pendiente".equals(p.getEstado()))
                .collect(Collectors.toList());
        model.addAttribute("pedidos", pendientes);
        return "cajero/verificar_pedidos";
    }

    @GetMapping("/api/detalle/{id}")
    @ResponseBody
    public List<Map<String, Object>> apiDetalle(@PathVariable Integer id) {
        Pedido p = pedidoRepository.findById(id).orElse(null);
        List<Map<String, Object>> resp = new ArrayList<>();
        if (p != null) {
            for (ItemProducto i : p.getListaItems()) {
                resp.add(Map.of(
                        "nombreProducto", i.getProducto().getNombre(),
                        "cantidad", i.getCantidad(),
                        "stock", i.getProducto().getStock()
                ));
            }
        }
        return resp;
    }

    // --- VALIDAR (Pago Exitoso) ---
    @GetMapping("/validar/{id}")
    public String validar(@PathVariable Integer id) {
        Pedido p = pedidoRepository.findById(id).orElse(null);
        if (p != null) {
            p.setEstado("Validado");
            // Nota: Aquí NO liberamos la mesa, porque los clientes siguen comiendo.
            // La mesa la libera el Mozo cuando se van.
            pedidoRepository.save(p);
        }
        return "redirect:/cajero/verificar?validado";
    }

    // --- CANCELAR (Pago Fallido / Error) ---
    @GetMapping("/cancelar/{id}")
    public String cancelar(@PathVariable Integer id) {
        Pedido p = pedidoRepository.findById(id).orElse(null);

        if (p != null) {
            // 1. Cambiamos estado del pedido
            p.setEstado("Cancelado");
            pedidoRepository.save(p);

            // 2. LIBERAMOS LA MESA AUTOMÁTICAMENTE
            if (p.getCliente() != null) {
                Usuario mesa = usuarioRepository.findById(p.getCliente().getId()).orElse(null);
                if (mesa instanceof Cliente) {
                    ((Cliente) mesa).setEstado("Disponible"); // Volvemos a verde
                    usuarioRepository.save(mesa);
                }
            }
        }
        return "redirect:/cajero/verificar?cancelado";
    }
}