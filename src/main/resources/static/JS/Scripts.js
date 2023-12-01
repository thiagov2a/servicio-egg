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
  botonProveedor.style.backgroundColor = 'rgb(200, 200, 200)';

  var botonResidente = document.querySelector('.soy-residente');
  botonResidente.style.backgroundColor = 'white';

  // Actualiza el valor del campo oculto
  document.getElementById('esProveedor').value = 'true';
}

function mostrarResidente() {
  var divProveedor = document.getElementById('campo-proveedor');
  var divResidente = document.getElementById('campo-residente');

  divResidente.style.display = 'block';
  divProveedor.style.display = 'none';

  var botonProveedor = document.querySelector('.soy-proveedor');
  botonProveedor.style.backgroundColor = 'white';

  var botonResidente = document.querySelector('.soy-residente');
  botonResidente.style.backgroundColor = 'rgb(200, 200, 200)';

  // Actualiza el valor del campo oculto
  document.getElementById('esProveedor').value = 'false';
}
