function validaFormUser() {
    var clave = document.getElementById("clave");
    var clave2 = document.getElementById("clave2");
    var valido = true;
    
    if(clave.value !== "" || clave2.value !== ""){
        if(clave.value === clave2.value) {
            document.getElementById("claveValida").innerHTML = "<span class='ok'>OK</span>";
        }
        else {
            document.getElementById("claveValida").innerHTML = "<span class='error'>Las claves no coinciden</span>";
            valido = false;
        }
    }
    
    if(!valido)
        alert("No se puede realizar la petición, hay campos erróneos.");
    
    return valido;
}
