function validaFormVisita() {
    var nombre = document.getElementById("nombre");
    var correo = document.getElementById("correo");
    var valido = true;
    
    if(nombre.value == "" || correo.value == ""){
        alert("Debe rellenar los campos obligatorios. Están indicados con un *.");
        valido = false;
    }
    
    return valido;
}