package com.equipo15.servicio.enumeraciones;

public enum Barrio {

   A("A"), B("B"), C("C");

   private String descripcion;

   Barrio(String descripcion) {
      this.descripcion = descripcion;
   }

   public String getDescripcion() {
      return descripcion;
   }

}
