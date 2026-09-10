package main.java.com.tecnobinaryjc.abarroteria.kinal.service.dashboard;

import javafx.collections.ObservableList;
import main.java.com.tecnobinaryjc.abarroteria.kinal.model.Producto;
import main.java.com.tecnobinaryjc.abarroteria.kinal.repository.ProductoRepository;

public class DashboardService {

    private final ProductoRepository productoRepository;

    public DashboardService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public ObservableList<Producto> findProducto() {
        ObservableList<Producto> lista = productoRepository.findAll();

        if (lista == null) {
            throw new RuntimeException("Sin productos.");
        }

        return lista;
    }

    public boolean deleteProducto(String idProducto) {
        if (idProducto == null || idProducto.isBlank()) {
            throw new RuntimeException("Debe seleccionar un producto.");
        }

        return productoRepository.deleteProducto(idProducto);
    }

    public boolean insertProducto(Producto producto) {
        validarProducto(producto);

        if (productoRepository.existsById(producto.getId_producto())) {
            throw new RuntimeException(
                    "Ya existe un producto registrado con ese ID."
            );
        }

        return productoRepository.insertProducto(producto);
    }

    public boolean updateProducto(Producto producto) {
        validarProducto(producto);
        return productoRepository.updateProducto(producto);
    }

    public boolean existeProducto(String idProducto) {
        if (idProducto == null || idProducto.isBlank()) {
            return false;
        }

        return productoRepository.existsById(idProducto);
    }

    private void validarProducto(Producto producto) {
        if (producto == null) {
            throw new RuntimeException(
                    "No hay datos de producto para procesar."
            );
        }

        if (producto.getId_producto() == null
                || producto.getId_producto().isBlank()) {
            throw new RuntimeException(
                    "El ID del producto es obligatorio."
            );
        }

        if (producto.getNombre_producto() == null
                || producto.getNombre_producto().isBlank()) {
            throw new RuntimeException(
                    "El nombre del producto es obligatorio."
            );
        }

        if (producto.getStock() < 0) {
            throw new RuntimeException(
                    "El stock no puede ser negativo."
            );
        }

        if (producto.getPrecio() < 0) {
            throw new RuntimeException(
                    "El precio no puede ser negativo."
            );
        }
    }
}