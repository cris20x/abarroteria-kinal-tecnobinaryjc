package main.java.com.tecnobinaryjc.abarroteria.kinal.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.java.com.tecnobinaryjc.abarroteria.kinal.model.Producto;
import main.java.com.tecnobinaryjc.abarroteria.kinal.service.dashboard.DashboardService;
import main.java.com.tecnobinaryjc.abarroteria.kinal.util.SceneManager;

public class DashboardController implements Initializable {

    private DashboardService dashboardService;
    private SceneManager sceneManager;

    @FXML
    private TableView<Producto> tableProducto;

    @FXML
    private TableColumn<Producto, String> tableColumnIdProducto;

    @FXML
    private TableColumn<Producto, String> tableColumnNombreProducto;

    @FXML
    private TableColumn<Producto, Integer> tableColumnStock;

    @FXML
    private TableColumn<Producto, Double> tableColumnPrecio;


    public DashboardController(DashboardService dashboardService, SceneManager sceneManager) {
        this.dashboardService = dashboardService;
        this.sceneManager = sceneManager;
    }


    public DashboardController() {
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarProductos();
    }

    private void configurarColumnas() {
        tableColumnIdProducto.setCellValueFactory(
                new PropertyValueFactory<>("id_producto")
        );
        tableColumnNombreProducto.setCellValueFactory(
                new PropertyValueFactory<>("nombre_producto")
        );
        tableColumnStock.setCellValueFactory(
                new PropertyValueFactory<>("stock")
        );
        tableColumnPrecio.setCellValueFactory(
                new PropertyValueFactory<>("precio")
        );
    }

    private void cargarProductos() {

        if (dashboardService == null) {
            return;
        }
        try {
            ObservableList<Producto> productos = dashboardService.findProducto();
            tableProducto.setItems(productos);
        } catch (RuntimeException e) {
            e.printStackTrace();
            if (sceneManager != null) {
                sceneManager.showAlertInfo(
                        "Error al cargar productos",
                        "No se pudieron obtener los productos",
                        e.getMessage(),
                        javafx.scene.control.Alert.AlertType.ERROR
                );
            }
        }
    }


    public void handleLoadDataView() {
        cargarProductos();
    }


    @FXML
    public void deleteProducto(ActionEvent event) {
        if (dashboardService == null) {

            return;
        }

        Producto productoSeleccionado =
                tableProducto.getSelectionModel().getSelectedItem();

        if (productoSeleccionado == null) {
            System.out.println("Selecciona un producto primero.");
            if (sceneManager != null) {
                sceneManager.showAlertInfo(
                        "Sin selección",
                        "Selecciona un producto primero",
                        "Debes seleccionar un producto de la tabla antes de eliminarlo.",
                        javafx.scene.control.Alert.AlertType.INFORMATION
                );
            }
            return;
        }

        boolean eliminado = dashboardService.deleteProducto(
                productoSeleccionado.getId_producto()
        );

        if (eliminado) {
            tableProducto.getItems().remove(productoSeleccionado);
            System.out.println("Producto eliminado correctamente.");
        } else {
            if (sceneManager != null) {
                sceneManager.showAlertInfo(
                        "Error al eliminar",
                        "No se pudo eliminar el producto",
                        "Revisa la consola para más detalles.",
                        javafx.scene.control.Alert.AlertType.ERROR
                );
            }
        }
    }
}
