// JavaScript para mostrar/ocultar el menú desplegable al hacer clic en el botón en el navbar
document.addEventListener('DOMContentLoaded', function () {
  var dropdownBtn = document.querySelector('.nav_btn');
  var dropdownContent = document.querySelector('.nav_dropdown-content');

  dropdownBtn.addEventListener('click', function () {
    dropdownContent.style.display = dropdownContent.style.display === 'block' ? 'none' : 'block';
  });
});

function mostrarProveedor() {
  var divProveedor = document.getElementById('campo-proveedor');
  var divResidente = document.getElementById('campo-residente');

  divProveedor.style.display = 'block';
  divResidente.style.display = 'none';

  var botonProveedor = document.querySelector('.soy-proveedor');
  botonProveedor.style.backgroundColor = 'white';
  botonProveedor.style.border = '1px solid black';
  botonProveedor.style.color = 'black';
  botonProveedor.style.fontWeight = '600';

  var botonResidente = document.querySelector('.soy-residente');
  botonResidente.style.backgroundColor = 'rgb(220, 220, 220)';
  botonResidente.style.border = 'none';
  botonResidente.style.color = 'rgb(160, 160, 160)';
  botonResidente.style.fontWeight = '500';

  // Actualiza el valor del campo oculto
  document.getElementById('esProveedor').value = 'true';
}

function mostrarResidente() {
  var divProveedor = document.getElementById('campo-proveedor');
  var divResidente = document.getElementById('campo-residente');

  divResidente.style.display = 'block';
  divProveedor.style.display = 'none';

  var botonProveedor = document.querySelector('.soy-proveedor');
  botonProveedor.style.backgroundColor = 'rgb(220, 220, 220)';
  botonProveedor.style.border = 'none';
  botonProveedor.style.color = 'rgb(160, 160, 160)';
  botonProveedor.style.fontWeight = '500';

  var botonResidente = document.querySelector('.soy-residente');
  botonResidente.style.backgroundColor = 'white';
  botonResidente.style.border = '1px solid black';
  botonResidente.style.color = 'black';
  botonResidente.style.fontWeight = '600';

  // Actualiza el valor del campo oculto
  document.getElementById('esProveedor').value = 'false';
}
