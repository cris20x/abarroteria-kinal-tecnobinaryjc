package main.java.com.tecnobinaryjc.abarroteria.kinal.service.dashboard;

import javafx.collections.ObservableList;
import main.java.com.tecnobinaryjc.abarroteria.kinal.model.Producto;
import main.java.com.tecnobinaryjc.abarroteria.kinal.repository.ProductoRepository;

// Lógica de negocio para el dashboard de productos.
public class DashboardService {
    private final ProductoRepository productoRepository;
    
    public DashboardService(ProductoRepository productoRepository){
        this.productoRepository = productoRepository;
    }
    
    public ObservableList<Producto> findProducto(){
     ObservableList<Producto> lista = productoRepository.findAll();
     if(lista == null){
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
}
