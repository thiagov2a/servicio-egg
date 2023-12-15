package com.equipo15.servicio.enumeraciones;

public enum Barrio {

   LAS_ACACIAS("Las Acacias"), PUNTA_LARREA("Punta Larrea"), RINCON_DE_CORIA("Rincón de Coria");

   private String descripcion;

   Barrio(String descripcion) {
      this.descripcion = descripcion;
   }

   public String getDescripcion() {
      return descripcion;
   }
}
