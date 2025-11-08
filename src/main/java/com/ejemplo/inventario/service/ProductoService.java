package com.ejemplo.inventario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ejemplo.inventario.model.Producto;
import com.ejemplo.inventario.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository repo;

    // Constructor que inyecta el repositorio
    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    // Listar todos los productos
    public List<Producto> listar() {
        return repo.findAll();
    }

    // Buscar producto por ID
    public Optional<Producto> obtener(Long id) {
        return repo.findById(id);
    }

    // Crear o actualizar producto
    public Producto crear(Producto producto) {
        return repo.save(producto);
    }

    // Eliminar producto por ID
    public boolean eliminar(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return true;
        }
        return false;
    }
}
