var xhr;

function init_ajax(){
    if (window.XMLHttpRequest) {
        xhr = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        xhr = new ActiveXObject("Microsoft.XMLHTTP");
    }   
    
}

function filtrar() {
    init_ajax();
    
    var url = "/TFC_DAW/Art/filtro";
    xhr.open("POST", url, true);
    xhr.onreadystatechange = articulosFiltrados;

    var cat = document.getElementById("cat");
    var cod = document.getElementById("cod");
    var pmenor = document.getElementById("pmenor");
    var pmayor = document.getElementById("pmayor");

    var datos = "cat=" + encodeURIComponent(cat.value) +
            "&cod=" + encodeURIComponent(cod.value) +
            "&pmenor=" + encodeURIComponent(pmenor.value) +
            "&pmayor=" + encodeURIComponent(pmayor.value);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(datos);

}

function articulosFiltrados() {
    if (xhr.readyState === 4) {
        if (xhr.status === 200) {
            document.getElementById("respuesta").innerHTML = xhr.responseText;
        }
    }
}

function filtrarFav() {
    init_ajax();
    
    var url = "/TFC_DAW/Art/filtroFav";
    xhr.open("POST", url, true);
    xhr.onreadystatechange = articulosFiltrados;

    var cat = document.getElementById("cat");
    var cod = document.getElementById("cod");
    var pmenor = document.getElementById("pmenor");
    var pmayor = document.getElementById("pmayor");

    var datos = "cat=" + encodeURIComponent(cat.value) +
            "&cod=" + encodeURIComponent(cod.value) +
            "&pmenor=" + encodeURIComponent(pmenor.value) +
            "&pmayor=" + encodeURIComponent(pmayor.value);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(datos);

}

function validarEmailDB() {

    init_ajax();
    
    var url = "/TFC_DAW/User/emailValido";
    xhr.open("POST", url, true);
    xhr.onreadystatechange = emailValido;

    var email = document.getElementById("email");

    var datos = "email=" + encodeURIComponent(email.value);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(datos);

}

function emailValido() {
    if(xhr.readyState === 4) {
        if(xhr.status === 200) {
            document.getElementById("emailValido").innerHTML = xhr.responseText;
        }
    }
}

function insertarComentario() {
    init_ajax();
    
    var url = "/TFC_DAW/Coment/add";
    xhr.open("POST", url, true);
    xhr.onreadystatechange = listaComentarios;

    var texto = document.getElementById("texto");
    var priv = document.getElementById("priv");
    var user = document.getElementById("id_user_c");
    var art = document.getElementById("id_art_c");

    var datos = "texto=" + encodeURIComponent(texto.value) +
            "&priv=" + encodeURIComponent(priv.value) +
            "&user=" + encodeURIComponent(user.value) +
            "&art=" + encodeURIComponent(art.value);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(datos);
}

function listaComentarios() {
    if (xhr.readyState === 4) {
        if (xhr.status === 200) {
            document.getElementById("lista_coment").innerHTML = xhr.responseText;
        }
    }
}

function botonFav() {
    init_ajax();
    
    var url = "/TFC_DAW/Art/botonFav";
    xhr.open("POST", url, true);
    xhr.onreadystatechange = textoFav;

    var id = document.getElementById("id_art");

    var datos = "id=" + encodeURIComponent(id.value);
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.send(datos);
}

function textoFav() {
    if (xhr.readyState === 4) {
        if (xhr.status === 200) {
            document.getElementById("btn_fav").innerHTML = xhr.responseText;
        }
    }
}
