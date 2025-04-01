// Constante que toma el canvas del html.
const canvas = document.getElementById('miCanvas');     
// Constante que usaremos para dibujar en 2d dentro del canvas
const ctx = canvas.getContext("2d");
// Variables que definen la velocidad de la paleta y si las teclas están presionadas o no
let velocidadPaleta = 5;
let derechaPresionada = false;
let izquierdaPresionada = false;
// Variables que definen los ladrillos
let columnas = Math.floor(Math.random() * 12) + 12;
let filas = Math.floor(Math.random() * 12) + 8;
let ladrillos = [];
let lW = canvas.width / columnas;
let lH = (canvas.height / 2) / filas;
// Otras variables
let vidas = 3;
let puntos = 0;
let bloquesDestruidos = 0;
let totalBloquesDestruidos = 0;
let pausado = true;
let poderActivo = false;
let mostrandoRanking = false;

//* Función que dibuja la bola
function bola() {
    ctx.beginPath();
    // El ctx.arc dibuja un arco en el canvas, siendo las coordenadas las posiciones en x y y, el radio del arco, el inicio y el final del arco
    ctx.arc(bX, bY, radio, 0, Math.PI * 2);
    // Se colorea el interior de la figura con el color rojo
    ctx.fillStyle = "#A52A2A";
    ctx.fill();
    ctx.strokeStyle = "black";
    ctx.lineWidth = 2; 
    ctx.stroke(); 
    ctx.closePath();
}

//* Función que dibuja la paleta
function paleta() {
    ctx.beginPath();
    // El ctx.rect dibuja un rectángulo en el canvas, siendo las coordenadas las posiciones en x y y, el ancho y el alto del rectángulo
    ctx.rect(pX, canvas.height - pY, pW, pH);
    // Se colorea el interior de la figura con el color rojo
    ctx.fillStyle = "#A52A2A";
    ctx.fill();
    ctx.strokeStyle = "black";
    ctx.lineWidth = 2; 
    ctx.stroke(); 
    ctx.closePath();
}

//* Función para cargar los ladrillos al iniciar el juego
function cargarLadrillos() {
    for (let c = 0; c < columnas; c++) {
        ladrillos[c] = [];
        for (let f = 0; f < filas; f++) {
            // Generar atributos
            let atributo = Math.floor(Math.random() * 100) + 1;
            if (atributo < 5) {
                color = "red";
                atributo = "explosivo";
            } else if (atributo < 44) {
                color = "blue";
                atributo = "normal";
            } else if (atributo < 88) {
                color = "green";
                atributo = "normal";
            } else if (atributo < 99.9) {
                color = "gray";
                atributo = "metal";
            } else {
                color = "pink";
                atributo = "vida";
            }

            // Generar poderes
            let poder = Math.floor(Math.random() * 100) + 1;
            if (poder < 2) {
                color = "orange";
                poder = "explosivo";
            } else if (poder < 4) {
                color = "olive";
                poder = "agrandar";
            } else {
                poder = "nada";
            }

            // Se añade el ladrillo junto a sus atributos a la matriz
            ladrillos[c][f] = { destruido: false, color: color, atributo: atributo, poder: poder };
        }
    }
}

//* Función que dibuja ladrillos
function ladrillo() {
    for (let c = 0; c < columnas; c++) {
        for (let f = 0; f < filas; f++) {
            const ladrillo = ladrillos[c][f];
            if (!ladrillo.destruido) {
                let lX = c * lW;
                let lY = f * lH;
                ladrillo.x = lX;
                ladrillo.y = lY;
                let color = ladrillo.color;
                ctx.beginPath();
                ctx.rect(lX, lY, lW, lH);
                ctx.fillStyle = color;
                ctx.fill();
                ctx.strokeStyle = "black";
                ctx.lineWidth = 2; 
                ctx.stroke(); 
                ctx.closePath();
            }
        }
    }
}

//* Función que calcula si la bola ha colisionado con los ladrillos
function colisionLadrillos() {
    for (let c = 0; c < columnas; c++) {
        for (let f = 0; f < filas; f++) {
            const ladrillo = ladrillos[c][f];
            if (!ladrillo.destruido) {
                //* Calcula si la bola está colisionando con el ladrillo 
                if (bX + radio > ladrillo.x && bX - radio < ladrillo.x + lW && bY + radio > ladrillo.y && bY - radio < ladrillo.y + lH) {
                    // Verifica si la colisión es horizontal 
                    if (bY >= ladrillo.y && bY <= ladrillo.y + lH) {
                        vX = -vX; 
                    }
                    // Verifica si la colisión es vertical 
                    if (bX >= ladrillo.x && bX <= ladrillo.x + lW) {
                        vY = -vY;
                    }

                    if (poderActivo && document.getElementById("poderTxt").innerHTML === "Explosivo") {
                        ladrillo.atributo = "explosivo";
                    }

                    //* Atributos
                    if (ladrillo.atributo === "normal") {
                        puntos += 100;
                        ladrillo.destruido = true;
                        bloquesDestruidos++;
                        totalBloquesDestruidos++;
                    }

                    // Destruye los bloques adyacentes
                    if (ladrillo.atributo === "explosivo") {
                        explosivo(c, f);
                    }
                
                    // Aumenta la vida en 1
                    if (ladrillo.atributo === "vida") {
                        vidas++;
                        ladrillo.destruido = true;
                        puntos += 1000;
                        bloquesDestruidos++;
                        totalBloquesDestruidos++;
                    }
                
                    // Necesita dos golpes para ser destruido
                    if (ladrillo.atributo === "metal") {
                        ladrillo.destruido = false;
                        ladrillo.color = "yellow";
                        ladrillo.atributo = "normal";
                        puntos += 200;
                    }

                    poder(ladrillo);
                }
            }
        }
    }
}

//* Función que destruye los ladrillos adyacentes (solo para ladrillos explosivos o poder explosivo)
function explosivo(c, f) {
    const ladrillo = ladrillos[c][f];
    if (!ladrillo.destruido) {
        ladrillo.destruido = true;
        puntos += 200;
        bloquesDestruidos++;
        totalBloquesDestruidos++;

        // Destruir bloques adyacentes
        for (let lc = -1; lc <= 1; lc++) {
            for (let lf = -1; lf <= 1; lf++) {
                try {
                    const ladrilloOtro = ladrillos[c + lc][f + lf];
                    if (ladrilloOtro.atributo === "explosivo" && !ladrilloOtro.destruido) {
                        explosivo(c + lc, f + lf);
                    } else if (ladrilloOtro.atributo === "metal" && !ladrilloOtro.destruido) {
                        ladrilloOtro.destruido = false;
                        ladrilloOtro.color = "yellow";
                        ladrilloOtro.atributo = "normal";
                        puntos += 200;
                    } else if (!ladrilloOtro.destruido) {
                        ladrilloOtro.destruido = true;
                        puntos += 100;
                        bloquesDestruidos++;
                        totalBloquesDestruidos++;
                    }

                    if (ladrilloOtro.poder !== "nada") {
                        poder(ladrilloOtro);
                    }
                } catch (error) {
                    console.error("Error al destruir los bloques adyacentes: ", error);
                }
            }
        }
    }
}

//* Función que le asigan un poder a un ladrillo
function poder(ladrillo) {
    if (ladrillo.poder === "explosivo" && !poderActivo) {
        document.getElementById("poderTxt").innerHTML = "Explosivo";
        document.getElementById("poderTxt").style.color = "darkred";
    }

    if (ladrillo.poder === "agrandar" && !poderActivo) {
        document.getElementById("poderTxt").innerHTML = "Agrandar";
        document.getElementById("poderTxt").style.color = "darkgreen";
    }
}

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

//* Función que reinicia la posición de la bola y la paleta
function reiniciar() {
    poderActivo = false;
    bX = canvas.width / 2;
    bY = canvas.height / 1.8;
    vX = 0;
    vY = 3;
    pX = canvas.width / 2 - pW / 2;
}

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

//* Función que se ejecuta cuando el juego termina
function gameOver() {
    mostrarPantallaRanking();
}

//* Función que se ejecuta cuando se usa el poder
function usarPoder() {
    poderActivo = true;
    let tiempo;
    
    if (poderActivo && document.getElementById("poderTxt").innerHTML === "Agrandar") {
        radio = 14;
        tiempo = 5000;
    } else {
        tiempo = 3000; 
    }

    setTimeout(function() {  
        document.getElementById("poderTxt").innerHTML = "Nada";
        document.getElementById("poderTxt").style.color = "black";
        poderActivo = false;
        radio = 7;
    }, tiempo);
}

//* Opciones
function reanudar() {
    if (pausado) {
        document.getElementById("tutorial").style.display = "none";
        pausado = false;
        // Intervalo que llamará constantemente a la función dibujar para que se actualicen las coordenadas
        intervalo = setInterval(dibujar, 10);
    }
}

function pausar() {
    if (!pausado) {
        pausado = true;
        clearInterval(intervalo);
    }
}

function verRanking() {
    document.getElementById("rankingLink").click();
}

//* Función que muestra la pantalla de ranking
function mostrarPantallaRanking() {
    clearInterval(intervalo);
    document.getElementById("puntuacionFinal").innerHTML += puntos;
    document.getElementById("bloquesDestruidosFinal").innerHTML += totalBloquesDestruidos;
    document.getElementById("fecha").innerHTML += new Date().toLocaleString();
    mostrandoRanking = true;
    document.getElementById("ranking").style.display = "block";
}

//* Función que introduce en los inputs ocultos información
function guardarRanking() {
    document.getElementById("inputPuntuacionFinal").value = puntos;
    document.getElementById("inputBloquesDestruidosFinal").value = totalBloquesDestruidos;
    document.getElementById("inputFechaFinal").value = new Date().toLocaleString();

    document.getElementById("rankingForm").submit();
}

//* Función que recarga la página
function recargar() {
    window.location.reload();
}

//* Cargar el juego al iniciar la página
cargarLadrillos();
document.getElementById("poderTxt").innerHTML = "Nada";
document.getElementById("poderTxt").style.color = "black";
dibujar();