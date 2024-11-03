package com.cookbook;

public class Recetas {
    private String nombre;
    private String ingredientes;
    private String instrucciones;

    public Recetas(String nombre, String ingredientes, String instrucciones) {
        this.nombre = nombre;
        this.ingredientes = ingredientes;
        this.instrucciones = instrucciones;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public String getInstrucciones() {
        return instrucciones;
    }
}
