package com.jugueria.sistemaventas.controller;
import com.jugueria.sistemaventas.model.Producto;
import com.jugueria.sistemaventas.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductoController {
    @Autowired
    private ProductoRepository productoRepository;
    // redireccionar a pestaña productos despues del login, porque por ahora no tenemos pagina principal
    @GetMapping("/")
    public String home() {
        return "redirect:/productos";
    }

    //1. Leer: Mostrar la lista de productos
    @GetMapping("/productos")
    public String listarProdcutos(Model model, @RequestParam(value = "palabraClave", required = false) String palabraClave) {
        if (palabraClave != null) {
            // Si alguien escribió algo, buscamos filtrando
            model.addAttribute("productos", productoRepository.findByNombreContainingIgnoreCase(palabraClave));
        } else {
            // Si no, mostramos todo como antes
            model.addAttribute("productos", productoRepository.findAll());
        }
        // Devolvemos la palabra clave para mantenerla escrita en la cajita de búsqueda
        model.addAttribute("palabraClave", palabraClave);

        return "productos/lista";
    }
    //2. Crear: Mostrar el formulario para agegar
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model){
        model.addAttribute("producto", new Producto());
        return "productos/formulario";
    }
    //3. Guardar: Recibir los datos de formulario y guardar en la bd
    @PostMapping("/guardar")
    public String guardarProducto(Producto producto){
    productoRepository.save(producto);
    return "redirect:/productos";
    }
    //4. Editar: Mostar el formulario con datos de un producto existente
    @GetMapping("/editar/{id}")
    private String mostarFormularioEditar(@PathVariable Integer id, Model model){
        Producto producto = productoRepository.findById(id).orElseThrow(() ->
        new IllegalArgumentException("Id invalido: "+ id));
        model.addAttribute("producto", producto);
        return "productos/formulario";
    }
    //5. Eliminar: Borrar un producto
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id){
        productoRepository.deleteById(id);
        return "redirect:/productos";
    }

}
