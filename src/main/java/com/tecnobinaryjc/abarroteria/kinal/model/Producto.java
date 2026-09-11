package main.java.com.tecnobinaryjc.abarroteria.kinal.model;

public class Producto {

    private String id_producto;
    private String nombre_producto;
    private int stock;
    private double precio;
    private String imagen_url;

    public Producto(
            String id_producto,
            String nombre_producto,
            int stock,
            double precio,
            String imagen_url) {

        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.stock = stock;
        this.precio = precio;
        this.imagen_url = imagen_url;
    }

    public String getId_producto() {
        return id_producto;
    }

    public void setId_producto(String id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImagen_url() {
        return imagen_url;
    }

    public void setImagen_url(String imagen_url) {
        this.imagen_url = imagen_url;
    }
}