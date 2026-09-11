package main.java.com.tecnobinaryjc.abarroteria.kinal.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import main.java.com.tecnobinaryjc.abarroteria.kinal.config.DataBaseConnection;
import main.java.com.tecnobinaryjc.abarroteria.kinal.model.Producto;

public class ProductoRepository {

    public ObservableList<Producto> findAll() {

        String sql =
                "SELECT id_producto, nombre_producto, stock, precio, imagen_url "
                + "FROM productos";

        ObservableList<Producto> lista =
                FXCollections.observableArrayList();

        try (
                PreparedStatement pstm =
                        DataBaseConnection
                                .getDataBaseConnection()
                                .prepareStatement(sql);

                ResultSet rs =
                        pstm.executeQuery()
        ) {

            while (rs.next()) {

                lista.add(
                        new Producto(
                                rs.getString("id_producto"),
                                rs.getString("nombre_producto"),
                                rs.getInt("stock"),
                                rs.getDouble("precio"),
                                rs.getString("imagen_url")
                        )
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar los productos: "
                    + e.getMessage()
            );
        }

        return lista;
    }

    public boolean deleteProducto(String idProducto) {

        String sql =
                "DELETE FROM productos WHERE id_producto = ?";

        try (
                PreparedStatement pstm =
                        DataBaseConnection
                                .getDataBaseConnection()
                                .prepareStatement(sql)
        ) {

            pstm.setString(1, idProducto);

            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar el producto: "
                    + e.getMessage()
            );

            return false;
        }
    }

    public boolean existsById(String idProducto) {

        String sql =
                "SELECT 1 FROM productos WHERE id_producto = ?";

        try (
                PreparedStatement pstm =
                        DataBaseConnection
                                .getDataBaseConnection()
                                .prepareStatement(sql)
        ) {

            pstm.setString(1, idProducto);

            try (ResultSet rs = pstm.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al verificar el producto: "
                    + e.getMessage()
            );

            return false;
        }
    }

    public boolean insertProducto(Producto producto) {

        String sql =
                "INSERT INTO productos "
                + "(id_producto, nombre_producto, stock, precio, imagen_url) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
                PreparedStatement pstm =
                        DataBaseConnection
                                .getDataBaseConnection()
                                .prepareStatement(sql)
        ) {

            pstm.setString(
                    1,
                    producto.getId_producto()
            );

            pstm.setString(
                    2,
                    producto.getNombre_producto()
            );

            pstm.setInt(
                    3,
                    producto.getStock()
            );

            pstm.setDouble(
                    4,
                    producto.getPrecio()
            );

            pstm.setString(
                    5,
                    producto.getImagen_url()
            );

            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al agregar el producto: "
                    + e.getMessage()
            );

            return false;
        }
    }

    public boolean updateProducto(Producto producto) {

        String sql =
                "UPDATE productos SET "
                + "nombre_producto = ?, "
                + "stock = ?, "
                + "precio = ?, "
                + "imagen_url = ? "
                + "WHERE id_producto = ?";

        try (
                PreparedStatement pstm =
                        DataBaseConnection
                                .getDataBaseConnection()
                                .prepareStatement(sql)
        ) {

            pstm.setString(
                    1,
                    producto.getNombre_producto()
            );

            pstm.setInt(
                    2,
                    producto.getStock()
            );

            pstm.setDouble(
                    3,
                    producto.getPrecio()
            );

            pstm.setString(
                    4,
                    producto.getImagen_url()
            );

            pstm.setString(
                    5,
                    producto.getId_producto()
            );

            return pstm.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar el producto: "
                    + e.getMessage()
            );

            return false;
        }
    }
}