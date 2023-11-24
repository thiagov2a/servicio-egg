// JavaScript para mostrar/ocultar el menú desplegable al hacer clic en el botón en el navbar
document.addEventListener('DOMContentLoaded', function () {
  var dropdownBtn = document.querySelector('.nav_btn');
  var dropdownContent = document.querySelector('.nav_dropdown-content');

  dropdownBtn.addEventListener('click', function () {
    dropdownContent.style.display = dropdownContent.style.display === 'block' ? 'none' : 'block';
  });
});
