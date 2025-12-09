package com.jugueria.sistemaventas.controller;

import com.jugueria.sistemaventas.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // Redirección inicial (Opcional, pero útil)
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // --- CASO DE USO: CONSULTAR LISTA DE PRODUCTOS ---
    // Cumple con el Diagrama de Secuencia "Consultar producto"
    @GetMapping("/productos/consultar")
    public String consultarProductos(Model model, @RequestParam(value = "palabraClave", required = false) String palabraClave) {
        // Simplemente obtenemos todo para mostrar la tabla de precios
        // (Podrías agregar el buscador aquí si quieres, como hicimos antes)
        model.addAttribute("productos", productoRepository.findAll());

        return "mozo/lista_productos"; // HTML de solo lectura
    }
}