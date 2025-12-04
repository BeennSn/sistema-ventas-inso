package com.jugueria.sistemaventas.controller;
import com.jugueria.sistemaventas.model.Producto;
import com.jugueria.sistemaventas.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
    public String listarProdcutos(Model model,
                                  @RequestParam(value = "palabraClave", required = false) String palabraClave,
                                  @RequestParam(value = "page", defaultValue = "0") int page) {

        // Configuración: 5 productos por página
        int pageSize = 5;
        PageRequest pageable = PageRequest.of(page, pageSize);

        Page<Producto> productoPage; // Usamos Page en lugar de List

        if (palabraClave != null && !palabraClave.isEmpty()) {
            // Búsqueda con paginación
            productoPage = productoRepository.findByNombreContainingIgnoreCase(palabraClave, pageable);
        } else {
            // Listado normal con paginación
            productoPage = productoRepository.findAll(pageable);
        }

        model.addAttribute("productos", productoPage); // Pasamos la página completa
        model.addAttribute("palabraClave", palabraClave);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productoPage.getTotalPages());

        return "productos/lista";
    }
    //2. Crear: Mostrar el formulario para agregar
    @GetMapping("/productos/nuevo")
    public String mostrarFormularioNuevo(Model model){
        model.addAttribute("producto", new Producto());
        return "productos/formulario";
    }
    //3. Guardar: Recibir los datos de formulario y guardar en la bd
    @PostMapping("/productos/guardar")
    public String guardarProducto(Producto producto){
    productoRepository.save(producto);
    return "redirect:/productos";
    }
    //4. Editar: Mostar el formulario con datos de un producto existente
    @GetMapping("/productos/editar/{id}")
    private String mostarFormularioEditar(@PathVariable Integer id, Model model){
        Producto producto = productoRepository.findById(id).orElseThrow(() ->
        new IllegalArgumentException("Id invalido: "+ id));
        model.addAttribute("producto", producto);
        return "productos/formulario";
    }
    //5. Eliminar: Borrar un producto
    @GetMapping("/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id){
        productoRepository.deleteById(id);
        return "redirect:/productos";
    }

}
