package com.equipo15.servicio.enumeraciones;

public enum Estado {

    CANCELADO("Cancelado"), PENDIENTE("Pendiente"), ACEPTADO("Aceptado"), FINALIZADO("Finalizado");

    
    private String descripcion;

    Estado(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

}
