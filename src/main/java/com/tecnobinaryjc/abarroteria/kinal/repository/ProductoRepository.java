package main.java.com.tecnobinaryjc.abarroteria.kinal.repository;

import javafx.collections.ObservableList;
import main.java.com.tecnobinaryjc.abarroteria.kinal.config.DataBaseConnection;
import main.java.com.tecnobinaryjc.abarroteria.kinal.model.Producto;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
// Acceso a datos para productos.
public class ProductoRepository {

    // Trae todos los productos registrados.
    public ObservableList<Producto> findAll(){
        String sql = "select * from productos";
        ObservableList<Producto> lista = FXCollections.observableArrayList();
        try(PreparedStatement pstm = DataBaseConnection.getDataBaseConnection().prepareStatement(sql);
            ResultSet rs = pstm.executeQuery()) {
            while(rs.next()) {
                lista.add(new Producto(
                    rs.getString("id_producto"),
                    rs.getString("nombre_producto"),
                    rs.getInt("stock"),
                    rs.getDouble("precio")
                ));
            }
        } catch(SQLException e) {
            System.out.println("Error al listar los productos: " + e.getMessage());
        }
        return lista;
    }

    // Elimina un producto por su id.
    public boolean deleteProducto(String idProducto) {
        String sql = "DELETE FROM productos WHERE id_producto = ?";

        try (PreparedStatement pstm = DataBaseConnection.getDataBaseConnection().prepareStatement(sql)) {

            pstm.setString(1, idProducto);
            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
            return false;
        }
    }

}
