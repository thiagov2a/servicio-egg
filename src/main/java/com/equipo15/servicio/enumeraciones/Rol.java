package com.equipo15.servicio.enumeraciones;

public enum Rol {

    USER("Usuario"), PROVEEDOR("Proveedor"), ADMIN("Admin");

    private String descripcion;

    Rol(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
