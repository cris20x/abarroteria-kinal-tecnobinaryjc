package main.java.com.tecnobinaryjc.abarroteria.kinal.controller;

import java.io.InputStream;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import main.java.com.tecnobinaryjc.abarroteria.kinal.model.Producto;
import main.java.com.tecnobinaryjc.abarroteria.kinal.repository.ProductoRepository;
import main.java.com.tecnobinaryjc.abarroteria.kinal.service.dashboard.DashboardService;
import main.java.com.tecnobinaryjc.abarroteria.kinal.util.SceneManager;

public class DashboardController {

    @FXML
    private TableView<Producto> tableProducto;

    @FXML
    private TableColumn<Producto, String> tableColumnImagen;

    @FXML
    private TableColumn<Producto, String> tableColumnIdProducto;

    @FXML
    private TableColumn<Producto, String> tableColumnNombreProducto;

    @FXML
    private TableColumn<Producto, String> tableColumnStock;

    @FXML
    private TableColumn<Producto, String> tableColumnPrecio;

    private final DashboardService dashboardService;
    private final SceneManager sceneManager;

    private final ProductoRepository productoRepository;

    private final Image imagenProducto;

    public DashboardController(
            DashboardService dashboardService,
            SceneManager sceneManager) {

        this.dashboardService = dashboardService;
        this.sceneManager = sceneManager;
        this.productoRepository = new ProductoRepository();

        this.imagenProducto = cargarImagenLocal();
    }

    @FXML
    public void initialize() {

        configurarColumnas();
        cargarProductos();
    }

    private void configurarColumnas() {

        tableColumnIdProducto.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getId_producto()
                )
        );

        tableColumnNombreProducto.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getNombre_producto()
                )
        );

        tableColumnStock.setCellValueFactory(data ->
                new SimpleStringProperty(
                        String.valueOf(
                                data.getValue().getStock()
                        )
                )
        );

        tableColumnPrecio.setCellValueFactory(data ->
                new SimpleStringProperty(
                        String.format(
                                "Q %.2f",
                                data.getValue().getPrecio()
                        )
                )
        );

        tableColumnImagen.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getImagen_url()
                )
        );

        tableColumnImagen.setCellFactory(column ->
                new TableCell<Producto, String>() {

                    private final ImageView imageView =
                            new ImageView();

                    private final StackPane container =
                            new StackPane();

                    {
                        imageView.setFitWidth(70);
                        imageView.setFitHeight(70);
                        imageView.setPreserveRatio(true);
                        imageView.setSmooth(true);

                        container.setAlignment(Pos.CENTER);
                        container.setPrefHeight(80);

                        container.getChildren().add(
                                imageView
                        );
                    }

                    @Override
                    protected void updateItem(
                            String imagen,
                            boolean empty) {

                        super.updateItem(imagen, empty);

                        imageView.setImage(null);
                        setGraphic(null);

                        if (empty) {
                            return;
                        }

                        if (imagenProducto != null) {

                            imageView.setImage(
                                    imagenProducto
                            );

                            setGraphic(container);
                        }
                    }
                }
        );
    }

    private Image cargarImagenLocal() {

        try {

            InputStream inputStream =
                    getClass().getResourceAsStream(
                            "/main/resources/img/producto-default.png"
                    );

            if (inputStream == null) {

                System.out.println(
                        "ERROR: No se encontró producto-default.png"
                );

                return null;
            }

            Image imagen =
                    new Image(
                            inputStream,
                            70,
                            70,
                            true,
                            true
                    );

            inputStream.close();

            if (imagen.isError()) {

                System.out.println(
                        "ERROR: No se pudo cargar producto-default.png"
                );

                return null;
            }

            System.out.println(
                    "IMAGEN LOCAL CARGADA CORRECTAMENTE"
            );

            return imagen;

        } catch (Exception e) {

            System.out.println(
                    "ERROR CARGANDO IMAGEN LOCAL:"
            );

            System.out.println(
                    e.getMessage()
            );

            return null;
        }
    }

    private void cargarProductos() {

        ObservableList<Producto> productos =
                productoRepository.findAll();

        tableProducto.setItems(productos);
    }

    @FXML
    private void handleAnadirProducto() {

        mostrarFormulario(null);
    }

    @FXML
    private void handleActualizarProducto() {

        Producto productoSeleccionado =
                tableProducto
                        .getSelectionModel()
                        .getSelectedItem();

        if (productoSeleccionado == null) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Actualizar producto",
                    "Selecciona un producto para actualizar."
            );

            return;
        }

        mostrarFormulario(productoSeleccionado);
    }

    @FXML
    private void handleEliminarProducto() {

        Producto productoSeleccionado =
                tableProducto
                        .getSelectionModel()
                        .getSelectedItem();

        if (productoSeleccionado == null) {

            mostrarAlerta(
                    Alert.AlertType.WARNING,
                    "Eliminar producto",
                    "Selecciona un producto para eliminar."
            );

            return;
        }

        Alert confirmacion =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        confirmacion.setTitle(
                "Eliminar producto"
        );

        confirmacion.setHeaderText(
                "¿Deseas eliminar este producto?"
        );

        confirmacion.setContentText(
                productoSeleccionado
                        .getNombre_producto()
        );

        if (confirmacion.showAndWait()
                .orElse(ButtonType.CANCEL)
                == ButtonType.OK) {

            boolean eliminado =
                    productoRepository.deleteProducto(
                            productoSeleccionado
                                    .getId_producto()
                    );

            if (eliminado) {

                cargarProductos();

                mostrarAlerta(
                        Alert.AlertType.INFORMATION,
                        "Producto eliminado",
                        "El producto fue eliminado correctamente."
                );

            } else {

                mostrarAlerta(
                        Alert.AlertType.ERROR,
                        "Error",
                        "No se pudo eliminar el producto."
                );
            }
        }
    }

    private void mostrarFormulario(
            Producto productoExistente) {

        Dialog<ButtonType> dialog =
                new Dialog<>();

        dialog.setTitle(
                productoExistente == null
                        ? "Añadir producto"
                        : "Actualizar producto"
        );

        ButtonType guardar =
                new ButtonType(
                        productoExistente == null
                                ? "Guardar"
                                : "Actualizar",
                        ButtonBar.ButtonData.OK_DONE
                );

        ButtonType cancelar =
                new ButtonType(
                        "Cancelar",
                        ButtonBar.ButtonData.CANCEL_CLOSE
                );

        dialog.getDialogPane()
                .getButtonTypes()
                .addAll(
                        guardar,
                        cancelar
                );

        GridPane grid =
                new GridPane();

        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtId =
                new TextField();

        TextField txtNombre =
                new TextField();

        TextField txtStock =
                new TextField();

        TextField txtPrecio =
                new TextField();

        TextField txtImagenUrl =
                new TextField();

        txtId.setPromptText(
                "ID del producto"
        );

        txtNombre.setPromptText(
                "Nombre"
        );

        txtStock.setPromptText(
                "Stock"
        );

        txtPrecio.setPromptText(
                "Precio"
        );

        txtImagenUrl.setPromptText(
                "producto-default.png"
        );

        if (productoExistente != null) {

            txtId.setText(
                    productoExistente
                            .getId_producto()
            );

            txtNombre.setText(
                    productoExistente
                            .getNombre_producto()
            );

            txtStock.setText(
                    String.valueOf(
                            productoExistente.getStock()
                    )
            );

            txtPrecio.setText(
                    String.valueOf(
                            productoExistente.getPrecio()
                    )
            );

            txtImagenUrl.setText(
                    "producto-default.png"
            );

            txtId.setDisable(true);

        } else {

            txtImagenUrl.setText(
                    "producto-default.png"
            );
        }

        grid.add(
                new Label("ID:"),
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

        grid.add(
                new Label("Imagen:"),
                0,
                4
        );

        grid.add(
                txtImagenUrl,
                1,
                4
        );

        dialog.getDialogPane()
                .setContent(grid);

        dialog.setResultConverter(
                button -> {

                    if (button != guardar) {
                        return null;
                    }

                    String id =
                            txtId.getText().trim();

                    String nombre =
                            txtNombre.getText().trim();

                    String stockTexto =
                            txtStock.getText().trim();

                    String precioTexto =
                            txtPrecio.getText().trim();

                    String imagenUrl =
                            "producto-default.png";

                    if (id.isEmpty()
                            || nombre.isEmpty()
                            || stockTexto.isEmpty()
                            || precioTexto.isEmpty()) {

                        mostrarAlerta(
                                Alert.AlertType.WARNING,
                                "Datos incompletos",
                                "Completa todos los campos obligatorios."
                        );

                        return null;
                    }

                    try {

                        int stock =
                                Integer.parseInt(
                                        stockTexto
                                );

                        double precio =
                                Double.parseDouble(
                                        precioTexto
                                );

                        Producto producto =
                                new Producto(
                                        id,
                                        nombre,
                                        stock,
                                        precio,
                                        imagenUrl
                                );

                        if (productoExistente == null) {

                            if (productoRepository
                                    .existsById(id)) {

                                mostrarAlerta(
                                        Alert.AlertType.WARNING,
                                        "Producto existente",
                                        "Ya existe un producto con ese ID."
                                );

                                return null;
                            }

                            boolean agregado =
                                    productoRepository
                                            .insertProducto(
                                                    producto
                                            );

                            if (agregado) {

                                cargarProductos();

                                return button;
                            }

                        } else {

                            boolean actualizado =
                                    productoRepository
                                            .updateProducto(
                                                    producto
                                            );

                            if (actualizado) {

                                cargarProductos();

                                return button;
                            }
                        }

                    } catch (NumberFormatException e) {

                        mostrarAlerta(
                                Alert.AlertType.WARNING,
                                "Datos inválidos",
                                "Stock y precio deben ser valores numéricos."
                        );
                    }

                    return null;
                }
        );

        dialog.showAndWait();
    }

    private void mostrarAlerta(
            Alert.AlertType tipo,
            String titulo,
            String mensaje) {

        Alert alert =
                new Alert(tipo);

        alert.setTitle(titulo);

        alert.setHeaderText(null);

        alert.setContentText(mensaje);

        alert.showAndWait();
    }

    @FXML
    private void handleCerrarSesion() {

        try {

            sceneManager.showLoginView();

        } catch (Exception e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Error",
                    "No se pudo cerrar la sesión."
            );
        }
    }
}