//* Función que se ejecuta cuando se presiona una tecla
document.addEventListener("keydown", function(tecla) {
    if (tecla.key === "ArrowRight") {
        derechaPresionada = true;
    } else if (tecla.key === "ArrowLeft") {
        izquierdaPresionada = true;
    } else if (tecla.key === "Enter" && !mostrandoRanking && pausado) {
        reanudar();
    } else if (tecla.key === "Enter" && !mostrandoRanking && !pausado) {
        pausar();
    } else if (tecla.key === "r") {
        verRanking();
    } else if (tecla.key === " ") {
        usarPoder();
    }
});

//* Función que se ejecuta cuando se suelta una tecla
document.addEventListener("keyup", function(tecla) {
    if (tecla.key === "ArrowRight") {
        derechaPresionada = false;
    } else if (tecla.key === "ArrowLeft") {
        izquierdaPresionada = false;
    }
});

//* Función que es llamada constantemente para dibujar el juego
function dibujar() {
    if (bloquesDestruidos === columnas * filas) {
        clearInterval(intervalo);
        alert("¡Felicidades, has ganado!");
        reiniciar();
        bloquesDestruidos = 0;
        cargarLadrillos();
        dibujar();
        pausar();
    }
    // Se limpia el canvas para que las figuras no dejen un trayecto al moverse
    // Se limpiará desde las coordenadas 0, 0 hasta el ancho y alto máximo del canvas
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // Se llama a la función que dibuja la bola y la paleta
    bola();
    paleta();
    ladrillo();
    colisionLadrillos();

    // Actualizar la información
    document.getElementById("vidas").innerHTML = vidas;
    document.getElementById("puntos").innerHTML = puntos;
    document.getElementById("bloquesDestruidos").innerHTML = bloquesDestruidos;
    document.getElementById("filas").innerHTML = filas;
    document.getElementById("columnas").innerHTML = columnas;

    // Actualizar la posición de la paleta
    if (derechaPresionada && pX + pW < canvas.width) {
        pX += velocidadPaleta;
    } 
    if (izquierdaPresionada && pX > 0) {
        pX -= velocidadPaleta;
    }

    //! ----- Condiciones para que la bola rebote -----
    // Si la suma de la posición actual de la bola más su próxima posición son mayores que el ancho del canvas o menores que su radio, 
    // el valor de la nueva posición será negativa para que vaya en la dirección contraria y no se salga
    // Esto será así hasta que la bola se vuelva a chocar con alguna pared
    if (bX + vX > canvas.width - radio || bX + vX < radio) {  
        vX = -vX; 
    }

    // Condición igual a la anterior, solo que esta toma en cuenta el techo
    if (bY + vY < radio) {
        vY = -vY;
    }

    // Se toma en cuenta la paleta y donde rebota la bola
    if (bY + vY > canvas.height - pY - radio && bX > pX && bX < pX + pW) { 
        vY = -vY;  
        // Cambiar la dirección horizontal de la bola basada en el punto de contacto con la paleta
        vX = (bX - (pX + pW / 2)) / (pW / 2) * 4;
    }  

    // Game over si se tienen 0 vidas
    if (vidas <= 0) {
        gameOver();
    }

    // Si toca el suelo se pierde una vida
    if (bY + vY > canvas.height - radio) {
        vidas--;
        reiniciar();
        dibujar();
        pausar();
    }  

    // Se actualizan las coordenadas de la bola para que esta se mueva
    bX += vX;  
    bY += vY;  
}

//* Cargar el juego al iniciar la página
cargarLadrillos();
document.getElementById("poderTxt").innerHTML = "Nada";
document.getElementById("poderTxt").style.color = "black";
dibujar();