package com.ejemplo.inventario.controller;

import com.ejemplo.inventario.model.Producto;
import com.ejemplo.inventario.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProductoWebController {

    private final ProductoService service;
    public ProductoWebController(ProductoService service) { this.service = service; }

    @GetMapping("/")
    public String home() { return "redirect:/productos"; }

    @GetMapping("/productos")
    public String lista(Model model) {
        model.addAttribute("productos", service.listar());
        return "lista";
    }

    @GetMapping("/productos/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("titulo","Nuevo producto");
        return "form"; 

    }

    @PostMapping("/productos/guardar")
    public String guardar(@ModelAttribute Producto p, RedirectAttributes ra) {
        service.crear(p);
        ra.addFlashAttribute("ok","Producto guardado");
        return "redirect:/productos";
        
    }
    
    @GetMapping("/productos/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes ra) {
        return service.obtener(id)
            .map(p -> {
                model.addAttribute("producto", p);
                model.addAttribute("titulo", "Editar producto");
                return "form"; // si tus vistas están en templates/ y NO en templates/productos/
            })
            .orElseGet(() -> {
                ra.addFlashAttribute("error","Producto no encontrado");
                return "redirect:/productos";
            });
    }

    @GetMapping("/productos/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        if (service.eliminar(id)) ra.addFlashAttribute("ok", "Producto eliminado");
        else ra.addFlashAttribute("error", "Producto no encontrado");
        return "redirect:/productos";
    }

    

    
}

