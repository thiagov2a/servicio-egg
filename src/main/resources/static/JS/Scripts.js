// JavaScript para mostrar/ocultar el menú desplegable al hacer clic en el botón en el navbar
document.addEventListener('DOMContentLoaded', function () {
  var dropdownBtn = document.querySelector('.nav_btn');
  var dropdownContent = document.querySelector('.nav_dropdown-content');

  dropdownBtn.addEventListener('click', function () {
    dropdownContent.style.display = dropdownContent.style.display === 'block' ? 'none' : 'block';
  });
});

// registro.html
function cambiarTipoUsuario(esProveedor) {
  var divProveedor = document.getElementById('campo-proveedor');
  var divResidente = document.getElementById('campo-residente');

  var botonProveedor = document.querySelector('.soy-proveedor');
  var botonResidente = document.querySelector('.soy-residente');

  var tipoActual = esProveedor ? divProveedor : divResidente;
  var tipoOtro = esProveedor ? divResidente : divProveedor;
  var botonActual = esProveedor ? botonProveedor : botonResidente;
  var botonOtro = esProveedor ? botonResidente : botonProveedor;

  tipoActual.style.display = 'block';
  tipoOtro.style.display = 'none';

  actualizarEstilosBoton(botonActual, true);
  actualizarEstilosBoton(botonOtro, false);

  // Actualiza el valor del campo oculto
  document.getElementById('esProveedor').value = esProveedor.toString();

  // Si es proveedor, selecciona aleatoriamente un barrio
  if (esProveedor) {
    seleccionarBarrioAleatorio();
  }
}

function actualizarEstilosBoton(boton, activo) {
  var estiloActivo = activo ? 'white' : 'rgb(220, 220, 220)';
  var estiloInactivo = activo ? 'black' : 'rgb(160, 160, 160)';
  var borde = activo ? '1px solid black' : 'none';
  var fontWeight = activo ? '600' : '400';
 

  boton.style.backgroundColor = estiloActivo;
  boton.style.border = borde;
  boton.style.color = estiloInactivo;
  boton.style.fontWeight = fontWeight;

}

function seleccionarBarrioAleatorio() {
  var barriosSelect = document.getElementsByName('barrio')[0];
  var opcionesBarrios = barriosSelect.options;
  var barrioAleatorio = opcionesBarrios[Math.floor(Math.random() * opcionesBarrios.length)].value;
  barriosSelect.value = barrioAleatorio;
}
//
