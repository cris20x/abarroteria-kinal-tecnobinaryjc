package main.java.com.tecnobinaryjc.abarroteria.kinal.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
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
                        Alert.AlertType.ERROR
                );
            }
        }
    }

    @FXML
    public void handleAnadirProducto(ActionEvent event) {
        if (dashboardService == null) {
            return;
        }

        boolean confirmar = sceneManager == null || sceneManager.showConfirmacion(
                "Añadir producto",
                "Confirmar acción",
                "¿Deseas agregar un nuevo producto al inventario?"
        );

        if (!confirmar) {
            return;
        }

        String idGenerado = generarIdProducto();

        Optional<Producto> resultado =
                mostrarFormularioProducto("Nuevo producto", null, idGenerado);

        resultado.ifPresent(nuevoProducto -> {
            try {
                boolean agregado =
                        dashboardService.insertProducto(nuevoProducto);

                if (agregado) {
                    cargarProductos();

                    if (sceneManager != null) {
                        sceneManager.showAlertInfo(
                                "Producto agregado",
                                "Operación exitosa",
                                "El producto se guardó correctamente en la base de datos.",
                                Alert.AlertType.INFORMATION
                        );
                    }

                } else if (sceneManager != null) {
                    sceneManager.showAlertInfo(
                            "Error al agregar",
                            "No se pudo guardar el producto",
                            "Revisa la consola para más detalles.",
                            Alert.AlertType.ERROR
                    );
                }

            } catch (RuntimeException e) {
                if (sceneManager != null) {
                    sceneManager.showAlertInfo(
                            "Error al agregar",
                            "No se pudo guardar el producto",
                            e.getMessage(),
                            Alert.AlertType.ERROR
                    );
                }
            }
        });
    }

    @FXML
    public void handleActualizarProducto(ActionEvent event) {
        if (dashboardService == null) {
            return;
        }

        Producto seleccionado =
                tableProducto.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            if (sceneManager != null) {
                sceneManager.showAlertInfo(
                        "Sin selección",
                        "Selecciona un producto primero",
                        "Debes seleccionar un producto de la tabla antes de actualizarlo.",
                        Alert.AlertType.INFORMATION
                );
            }
            return;
        }

        boolean confirmar = sceneManager == null || sceneManager.showConfirmacion(
                "Actualizar producto",
                "Confirmar acción",
                "¿Deseas actualizar el producto \""
                        + seleccionado.getNombre_producto() + "\"?"
        );

        if (!confirmar) {
            return;
        }

        Optional<Producto> resultado =
                mostrarFormularioProducto(
                        "Actualizar producto",
                        seleccionado,
                        null
                );

        resultado.ifPresent(productoActualizado -> {
            try {
                boolean actualizado =
                        dashboardService.updateProducto(productoActualizado);

                if (actualizado) {
                    cargarProductos();

                    if (sceneManager != null) {
                        sceneManager.showAlertInfo(
                                "Producto actualizado",
                                "Operación exitosa",
                                "Los cambios se guardaron correctamente.",
                                Alert.AlertType.INFORMATION
                        );
                    }

                } else if (sceneManager != null) {
                    sceneManager.showAlertInfo(
                            "Error al actualizar",
                            "No se pudo actualizar el producto",
                            "Revisa la consola para más detalles.",
                            Alert.AlertType.ERROR
                    );
                }

            } catch (RuntimeException e) {
                if (sceneManager != null) {
                    sceneManager.showAlertInfo(
                            "Error al actualizar",
                            "No se pudo actualizar el producto",
                            e.getMessage(),
                            Alert.AlertType.ERROR
                    );
                }
            }
        });
    }

    @FXML
    public void handleEliminarProducto(ActionEvent event) {
        if (dashboardService == null) {
            return;
        }

        Producto productoSeleccionado =
                tableProducto.getSelectionModel().getSelectedItem();

        if (productoSeleccionado == null) {
            if (sceneManager != null) {
                sceneManager.showAlertInfo(
                        "Sin selección",
                        "Selecciona un producto primero",
                        "Debes seleccionar un producto de la tabla antes de eliminarlo.",
                        Alert.AlertType.INFORMATION
                );
            }
            return;
        }

        boolean confirmar = sceneManager == null || sceneManager.showConfirmacion(
                "Eliminar producto",
                "Confirmar eliminación",
                "¿Estás seguro de eliminar el producto \""
                        + productoSeleccionado.getNombre_producto()
                        + "\"? Esta acción no se puede deshacer."
        );

        if (!confirmar) {
            return;
        }

        boolean eliminado =
                dashboardService.deleteProducto(
                        productoSeleccionado.getId_producto()
                );

        if (eliminado) {
            tableProducto.getItems().remove(productoSeleccionado);

            if (sceneManager != null) {
                sceneManager.showAlertInfo(
                        "Producto eliminado",
                        "Operación exitosa",
                        "El producto se eliminó correctamente.",
                        Alert.AlertType.INFORMATION
                );
            }

        } else if (sceneManager != null) {
            sceneManager.showAlertInfo(
                    "Error al eliminar",
                    "No se pudo eliminar el producto",
                    "Revisa la consola para más detalles.",
                    Alert.AlertType.ERROR
            );
        }
    }

    @FXML
    public void handleCerrarSesion(ActionEvent event) throws Exception {
        if (sceneManager == null) {
            return;
        }

        boolean confirmar = sceneManager.showConfirmacion(
                "Cerrar sesión",
                "Confirmar acción",
                "¿Estás seguro de que deseas cerrar la sesión?"
        );

        if (confirmar) {
            sceneManager.showLoginView();
        }
    }

    private Optional<Producto> mostrarFormularioProducto(
            String tituloVentana,
            Producto productoBase,
            String idParaNuevoProducto) {

        boolean esEdicion = productoBase != null;

        String idMostrado = esEdicion
                ? productoBase.getId_producto()
                : idParaNuevoProducto;

        Dialog<Producto> dialog = new Dialog<>();

        dialog.setTitle(tituloVentana);

        if (tableProducto.getScene() != null) {
            dialog.initOwner(
                    tableProducto.getScene().getWindow()
            );
        }

        dialog.setHeaderText(
                esEdicion
                        ? "Modifica los datos del producto"
                        : "Ingresa los datos del nuevo producto (el ID se genera automáticamente)"
        );

        ButtonType botonGuardar = new ButtonType(
                esEdicion ? "Actualizar" : "Agregar",
                ButtonBar.ButtonData.OK_DONE
        );

        dialog.getDialogPane()
                .getButtonTypes()
                .addAll(botonGuardar, ButtonType.CANCEL);

        GridPane grid = new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(
                new Insets(20, 20, 10, 20)
        );

        TextField txtId =
                new TextField(idMostrado);

        txtId.setDisable(true);

        TextField txtNombre =
                new TextField(
                        esEdicion
                                ? productoBase.getNombre_producto()
                                : ""
                );

        txtNombre.setPromptText(
                "Nombre del producto"
        );

        TextField txtStock =
                new TextField(
                        esEdicion
                                ? String.valueOf(productoBase.getStock())
                                : ""
                );

        txtStock.setPromptText(
                "Cantidad en stock"
        );

        TextField txtPrecio =
                new TextField(
                        esEdicion
                                ? String.valueOf(productoBase.getPrecio())
                                : ""
                );

        txtPrecio.setPromptText(
                "Precio unitario"
        );

        grid.add(
                new Label("ID producto:"),
                0,
                0
        );

        grid.add(
                txtId,
                1,
                0
        );

        grid.add(
                new Label("Nombre:"),
                0,
                1
        );

        grid.add(
                txtNombre,
                1,
                1
        );

        grid.add(
                new Label("Stock:"),
                0,
                2
        );

        grid.add(
                txtStock,
                1,
                2
        );

        grid.add(
                new Label("Precio:"),
                0,
                3
        );

        grid.add(
                txtPrecio,
                1,
                3
        );

        dialog.getDialogPane()
                .setContent(grid);

        Node botonGuardarNode =
                dialog.getDialogPane()
                        .lookupButton(botonGuardar);

        botonGuardarNode.addEventFilter(
                ActionEvent.ACTION,
                filtroEvent -> {

                    String mensajeError =
                            validarCamposFormulario(
                                    idMostrado,
                                    txtNombre.getText(),
                                    txtStock.getText(),
                                    txtPrecio.getText()
                            );

                    if (mensajeError != null) {
                        filtroEvent.consume();

                        if (sceneManager != null) {
                            sceneManager.showAlertInfo(
                                    "Datos inválidos",
                                    "Revisa el formulario",
                                    mensajeError,
                                    Alert.AlertType.WARNING
                            );
                        }
                    }
                }
        );

        dialog.setResultConverter(boton -> {

            if (boton == botonGuardar) {

                String nombre =
                        txtNombre.getText().trim();

                int stock =
                        Integer.parseInt(
                                txtStock.getText().trim()
                        );

                double precio =
                        Double.parseDouble(
                                txtPrecio.getText().trim()
                        );

                return new Producto(
                        idMostrado,
                        nombre,
                        stock,
                        precio
                );
            }

            return null;
        });

        return dialog.showAndWait();
    }

    private String generarIdProducto() {

        final String caracteres =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        java.util.Random random =
                new java.util.Random();

        String idGenerado;

        int intentos = 0;

        do {

            StringBuilder sb =
                    new StringBuilder(5);

            for (int i = 0; i < 5; i++) {

                sb.append(
                        caracteres.charAt(
                                random.nextInt(
                                        caracteres.length()
                                )
                        )
                );
            }

            idGenerado = sb.toString();

            intentos++;

        } while (
                dashboardService != null
                && dashboardService.existeProducto(idGenerado)
                && intentos < 20
        );

        return idGenerado;
    }

    private String validarCamposFormulario(
            String id,
            String nombre,
            String stock,
            String precio) {

        if (id == null || id.isBlank()) {
            return "El ID del producto es obligatorio.";
        }

        if (nombre == null || nombre.isBlank()) {
            return "El nombre del producto es obligatorio.";
        }

        try {

            int stockValor =
                    Integer.parseInt(
                            stock.trim()
                    );

            if (stockValor < 0) {
                return "El stock no puede ser negativo.";
            }

        } catch (NumberFormatException e) {

            return "El stock debe ser un número entero válido.";
        }

        try {

            double precioValor =
                    Double.parseDouble(
                            precio.trim()
                    );

            if (precioValor < 0) {
                return "El precio no puede ser negativo.";
            }

        } catch (NumberFormatException e) {

            return "El precio debe ser un número válido.";
        }

        return null;
    }
}