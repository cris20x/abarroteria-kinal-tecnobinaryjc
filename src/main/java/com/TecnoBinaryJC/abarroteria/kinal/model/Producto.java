/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.TecnoBinaryJC.abarroteria.kinal.model;

/**
 *
 * @author informatica
 */
public class Producto {
   String id_producto;
   String nombre_producto;
   int stock;
   double precio;

    public Producto(String id_producto, String nombre_producto, int stock, double precio) {
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.stock = stock;
        this.precio = precio;
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
}
