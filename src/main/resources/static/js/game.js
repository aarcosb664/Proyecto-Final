// -----------------
// CONSTANTES
// -----------------

// Canvas y contexto del juego: se obtiene el canvas y su contexto 2D, y se ajusta el tamaño al contenedor principal
const canvas = document.getElementById('miCanvas');
const ctx = canvas.getContext('2d');
const gameContainer = document.querySelector('.game-container');
const containerSize = Math.min(gameContainer.clientWidth, gameContainer.clientHeight);
canvas.width = containerSize;
canvas.height = containerSize;

// Rango de columnas y filas de los ladrillos (para la generación aleatoria de niveles)
const minCols = 12;
const maxCols = 23;
const minRows = 8;
const maxRows = 19;

// Puntos otorgados por cada tipo de ladrillo
const pointsNormal = 100;
const pointsMetal = 200;
const pointsLife = 1000;

// Velocidades y tiempos de animación y poderes
const ballInitialVelocity = 3;
const drawInterval = 10;
const powerTimeout = 8000;

// Propiedades de la pelota (posición, velocidad, radio)
let ballX = containerSize / 2;
let ballY = containerSize / 1.5;
let ballVelocityX = 0;
let ballVelocityY = ballInitialVelocity;
let ballRadius = containerSize / 100;

// Propiedades de la paleta (tamaño, posición, velocidad, controles)
let paddleWidth = containerSize / 6;
let paddleHeight = containerSize / 80;
let paddleX = (containerSize - paddleWidth) / 2;
let paddleY = containerSize - 20;
let paddleSpeed = 5;
let rightPressed = false;
let leftPressed = false;

// Matriz de ladrillos
let bricks = [];

// Estado del juego (número de filas/columnas, vidas, puntuación, etc.)
let cols, rows, brickWidth, brickHeight;
let lives = 3;
let score = 0;
let bricksDestroyed = 0;
let totalBricksDestroyed = 0;
let paused = true;
let powerOn = false;
let gameLoop = null;

// Colores y poderes (color de la pelota, color de la paleta)
const ballColor = '#A52A2A';
const paddleColor = '#A52A2A';

// Elemento de texto para mostrar el poder activo
let powerTxt;
const powerTxts = document.querySelectorAll("#powerTxt");
if (window.innerWidth < 992) {
    powerTxt = powerTxts[1];
} else {
    powerTxt = powerTxts[0];
}

// Elementos de control para móviles
const mobileLeft = document.getElementById("mobileLeft");
const mobileRight = document.getElementById("mobileRight");


// -----------------
// LÓGICA DEL JUEGO
// -----------------

// Dibuja la pelota en el canvas
function drawBall() {
    ctx.beginPath();
    ctx.arc(ballX, ballY, ballRadius, 0, Math.PI * 2);
    ctx.fillStyle = ballColor;
    ctx.fill();
    ctx.strokeStyle = "black";
    ctx.lineWidth = 2;
    ctx.stroke();
    ctx.closePath();
}

// Dibuja la paleta en el canvas
function drawPaddle() {
    ctx.beginPath();
    ctx.rect(paddleX, paddleY, paddleWidth, paddleHeight);
    ctx.fillStyle = paddleColor;
    ctx.fill();
    ctx.strokeStyle = "black";
    ctx.lineWidth = 2;
    ctx.stroke();
    ctx.closePath();
}

// Determina el tipo y color de un ladrillo según una probabilidad
function getBrickAttributeAndColor(attributeChance) {
    if        (attributeChance < 5)    { return { attribute: "explosive", color: "red"   };
    } else if (attributeChance < 44)   { return { attribute: "normal",    color: "blue"  };
    } else if (attributeChance < 88)   { return { attribute: "normal",    color: "green" };
    } else if (attributeChance < 99.9) { return { attribute: "metal",     color: "gray"  };
    } else                             { return { attribute: "life",      color: "pink"  };
    }
}

// Determina el poder y color de un ladrillo según una probabilidad
function getBrickPowerAndColor(powerChance, currentColor) {
    if        (powerChance < 2) { return { power: "explosive", color: "orange"     };
    } else if (powerChance < 4) { return { power: "expand",    color: "olive"      };
    } else                      { return { power: "none",      color: currentColor };
    }
}

// Genera la matriz de ladrillos con atributos y poderes aleatorios
function loadBricks() {
    cols = Math.floor(Math.random() * (maxCols - minCols + 1)) + minCols;
    rows = Math.floor(Math.random() * (maxRows - minRows + 1)) + minRows;
    brickWidth = canvas.width / cols;
    brickHeight = (canvas.height / 2) / rows;
    
    bricks = Array.from({ length: cols }, () => []);

    // Se generan los ladrillos
    for (let col = 0; col < cols; col++) {
        for (let row = 0; row < rows; row++) {
            // Se genera un número aleatorio entre 0 y 100
            let attributeChance = Math.random() * 100;
            // Se obtiene el atributo y el color del ladrillo
            let { attribute, color } = getBrickAttributeAndColor(attributeChance);

            // Se genera un número aleatorio entre 0 y 100
            let powerChance = Math.random() * 100;
            // Se obtiene el poder y el color del ladrillo
            let powerResult = getBrickPowerAndColor(powerChance, color);
            // Se establece el poder y el color del ladrillo
            let power = powerResult.power;
            color = powerResult.color;

            // Se crea el ladrillo
            bricks[col][row] = {
                destroyed: false,
                color: color,
                attribute: attribute,
                power: power
            };
        }
    }
}

// Dibuja todos los ladrillos en el canvas
function drawBricks() {
    for (let col = 0; col < cols; col++) {
        for (let row = 0; row < rows; row++) {
            const brick = bricks[col][row];
            if (!brick.destroyed) {
                brick.x = col * brickWidth;
                brick.y = row * brickHeight;
                ctx.beginPath();
                ctx.rect(brick.x, brick.y, brickWidth, brickHeight);
                ctx.fillStyle = brick.color;
                ctx.fill();
                ctx.strokeStyle = "black";
                ctx.lineWidth = 2;
                ctx.stroke();
                ctx.closePath();
            }
        }
    }
}

// Lógica de colisión con ladrillos y aplicación de efectos según el tipo
function handleBrickCollision(brick, col, row) {
    // Si el poder está activo y el texto de poder contiene la clase 'power-explosive', se establece el atributo del ladrillo como 'explosivo'
    if (powerOn && powerTxt.classList.contains('power-explosive')) {
        brick.attribute = "explosive";
    }

    // Si el atributo del ladrillo es 'normal', se suma la puntuación normal y se destruye el ladrillo
    if (brick.attribute === "normal") {
        score += pointsNormal;
        brick.destroyed = true;
    // Si el atributo del ladrillo es 'explosivo', se explota el ladrillo
    } else if (brick.attribute === "explosive") {
        explode(col, row);
    // Si el atributo del ladrillo es 'life', se suma la puntuación de vida y se destruye el ladrillo
    } else if (brick.attribute === "life") {
        lives++;
        brick.destroyed = true;
        score += pointsLife;
    // Si el atributo del ladrillo es 'metal', se cambia el color del ladrillo a amarillo y se establece el atributo como 'normal'
    } else if (brick.attribute === "metal") {
        brick.color = "yellow";
        brick.attribute = "normal";
        score += pointsMetal;
    }

    bricksDestroyed++;
    totalBricksDestroyed++;
    setPower(brick);
}

// Aplica el efecto de explosión a los ladrillos adyacentes
function handleAdjacentBrick(col, row) {
    const adjacent = (bricks[col][row]);
    if (!adjacent) {
        return;
    }

    // Si el atributo del ladrillo adyacente es 'explosivo' y no está destruido, se explota el ladrillo
    if (adjacent.attribute === "explosive" && !adjacent.destroyed) {
        explode(col, row);
    // Si el atributo del ladrillo adyacente es 'metal' y no está destruido, se cambia el color del ladrillo a amarillo y se establece el atributo como 'normal'
    } else if (adjacent.attribute === "metal" && !adjacent.destroyed) {
        adjacent.color = "yellow";
        adjacent.attribute = "normal";
        score += pointsMetal;
    // Si el ladrillo adyacente no está destruido, se destruye y se suma la puntuación normal
    } else if (!adjacent.destroyed) {
        adjacent.destroyed = true;
        score += pointsNormal;
        bricksDestroyed++;
        totalBricksDestroyed++;
    }

    // Si el poder del ladrillo adyacente no es 'none', se activa el poder
    if (adjacent.power !== "none") {
        setPower(adjacent);
    }
}

// Lógica de explosión: destruye el ladrillo y afecta a los adyacentes
function explode(col, row) {
    const brick = bricks[col][row];
    // Si el ladrillo no existe o está destruido, se retorna
    if (!brick || brick.destroyed) {
        return;
    }

    // Se destruye el ladrillo y se suma la puntuación de metal
    brick.destroyed = true;
    score += pointsMetal;
    bricksDestroyed++;
    totalBricksDestroyed++;

    // Se explota el ladrillo y se aplican los efectos a los ladrillos adyacentes
    for (let dCol = -1; dCol <= 1; dCol++) {
        for (let dRow = -1; dRow <= 1; dRow++) {
            handleAdjacentBrick(col + dCol, row + dRow);
        }
    }
}

// Detecta colisiones entre la pelota y los ladrillos
function brickCollision() {
    for (let col = 0; col < cols; col++) {
        for (let row = 0; row < rows; row++) {
            const brick = bricks[col][row];
            // Si el ladrillo no existe o está destruido, se continúa
            if (!brick || brick.destroyed) {
                continue;
            }

            // Si la pelota colisiona con el ladrillo, se ajusta la velocidad de la pelota y se aplica el efecto del ladrillo
            if (
                ballX + ballRadius > brick.x && ballX - ballRadius < brick.x + brickWidth &&
                ballY + ballRadius > brick.y && ballY - ballRadius < brick.y + brickHeight
            ) {
                if (ballY >= brick.y && ballY <= brick.y + brickHeight) ballVelocityX = -ballVelocityX;
                if (ballX >= brick.x && ballX <= brick.x + brickWidth) ballVelocityY = -ballVelocityY;
                handleBrickCollision(brick, col, row);
            }
        }
    }
}

// Activa el poder del ladrillo si corresponde y actualiza el texto de poder
function setPower(brick) {
    if (brick.power === "explosive" && !powerOn) {
        powerTxt.innerHTML = 'Explosive';
        powerTxt.setAttribute("class", "power-explosive header-neon-red-sm");
    }

    if (brick.power === "expand" && !powerOn) {
        powerTxt.innerHTML = 'Expand';
        powerTxt.setAttribute("class", "power-expand header-neon-green-sm");
    }
}

// Configura los listeners de teclado y táctiles para controlar la paleta y el juego
function setupKeyListeners() {
    document.addEventListener("keydown", (e) => {
        switch (e.key) {
            case "ArrowRight":
                rightPressed = true;
                break;
            case "ArrowLeft":
                leftPressed = true;
                break;
            case "Enter":
                paused ? resume() : pause();
                break;
            case "r":
                showRanking();
                break;
            case " ":
                usePower();
                break;
        }
    });

    document.addEventListener("keyup", (e) => {
        if (e.key === "ArrowRight") { rightPressed = false; }
        if (e.key === "ArrowLeft")  { leftPressed = false;  }
    });

    document.addEventListener("touchstart", (e) => {
        if (e.target.id === "mobileLeft") { 
            leftPressed = true;
            mobileLeft.classList.add("bi-caret-left-fill");
            mobileLeft.classList.remove("bi-caret-left");
        }

        if (e.target.id === "mobileRight") { 
            rightPressed = true;
            mobileRight.classList.add("bi-caret-right-fill");
            mobileRight.classList.remove("bi-caret-right");
        }

        if (e.target.id === "powerTxt") {
            usePower();
        }

        if (e.target.id === "mobileContainer") {
            paused ? resume() : pause();
        }
    });

    document.addEventListener("touchend", (e) => {
        if (e.target.id === "mobileLeft") { 
            leftPressed = false;
            mobileLeft.classList.remove("bi-caret-left-fill");
            mobileLeft.classList.add("bi-caret-left");
        }

        if (e.target.id === "mobileRight") { 
            rightPressed = false;
            mobileRight.classList.remove("bi-caret-right-fill");
            mobileRight.classList.add("bi-caret-right");
        }
    });
}

// Reinicia la posición de la pelota y la paleta, y desactiva poderes
function reset() {
    powerOn = false;
    ballX = canvas.width / 2;
    ballY = canvas.height / 1.8;
    ballVelocityX = 0;
    ballVelocityY = ballInitialVelocity;
    paddleX = (canvas.width - paddleWidth) / 2;
}

// Actualiza las estadísticas del juego en la interfaz
function updateStatsUI() {
    document.getElementById("lives").textContent = lives;
    document.getElementById("score").textContent = score;
    document.getElementById("destroyedBlocks").textContent = bricksDestroyed;
    document.getElementById("rows").textContent = rows;
    document.getElementById("columns").textContent = cols;
    document.getElementById("livesMobile").textContent = lives;
    document.getElementById("scoreMobile").textContent = score;
    document.getElementById("destroyedBlocksMobile").textContent = bricksDestroyed;
    document.getElementById("rowsMobile").textContent = rows;
    document.getElementById("columnsMobile").textContent = cols;
}

// Mueve la paleta según los controles activos
function movePaddle() {
    // Si la tecla derecha está presionada y la paleta no está en el borde derecho, se mueve la paleta a la derecha
    if (rightPressed && paddleX + paddleWidth < containerSize) {
        paddleX += paddleSpeed;
    }

    // Si la tecla izquierda está presionada y la paleta no está en el borde izquierdo, se mueve la paleta a la izquierda
    if (leftPressed && paddleX > 0) {
        paddleX -= paddleSpeed;
    }
}

// Detecta colisiones de la pelota con las paredes
function handleBallWallCollision() {
    // Si la pelota colisiona con la pared derecha, se invierte la velocidad en X
    if (ballX + ballVelocityX > containerSize - ballRadius) {
        ballVelocityX = -ballVelocityX;
    }

    // Si la pelota colisiona con la pared izquierda, se invierte la velocidad en X
    if (ballY + ballVelocityY < ballRadius) {
        ballVelocityY = -ballVelocityY;
    }
}

// Detecta colisión de la pelota con la paleta y ajusta la dirección
function handleBallPaddleCollision() {
    // Si la pelota colisiona con la paleta, se invierte la velocidad en Y y se ajusta la velocidad en X
    if (
        ballY + ballVelocityY > paddleY - ballRadius &&
        ballX > paddleX &&
        ballX < paddleX + paddleWidth
    ) {
        ballVelocityY = -ballVelocityY;
        ballVelocityX = ((ballX - (paddleX + paddleWidth / 2)) / (paddleWidth / 2)) * 5;
    }
}

// Lógica para perder vida o terminar el juego si la pelota cae
function handleBallReset() {
    // Si el jugador no tiene vidas, se termina el juego
    if (lives <= 0) {
        gameOver();
        return true;
    }

    // Si la pelota colisiona con la pared inferior, se pierde una vida
    if (ballY + ballVelocityY > canvas.height - ballRadius) {
        lives--;
        reset();
        pause();
        return true;
    }

    return false;
}

// Bucle principal de dibujo y lógica del juego
function draw() {
    // Si se han destruido todos los ladrillos, se termina el juego
    if (bricksDestroyed === cols * rows) {
        clearInterval(gameLoop);
        alert("Congratulations, you won!");
        reset();
        bricksDestroyed = 0;
        loadBricks();
        draw();
        return;
    }

    // Se limpia el canvas
    ctx.clearRect(0, 0, containerSize, containerSize);

    // Se dibujan los elementos del juego
    drawBall();
    drawPaddle();
    drawBricks();

    // Se detectan colisiones
    brickCollision();

    // Se actualizan las estadísticas
    updateStatsUI();
    movePaddle();
    handleBallWallCollision();
    handleBallPaddleCollision();

    // Se verifica si la pelota ha perdido una vida
    if (handleBallReset()) {
        return;
    }

    // Se actualiza la posición de la pelota
    ballX += ballVelocityX;
    ballY += ballVelocityY;
}

// Activa el poder actual (explosivo o expandir) durante un tiempo limitado
function usePower() {
    powerOn = true;

    // Si el poder es 'expand', se aumenta el radio de la pelota
    if (powerTxt.classList.contains('power-expand')) {
        ballRadius = 25;
    }

    // Se desactiva el poder después de un tiempo
    setTimeout(() => {
        powerTxt.innerHTML = 'None';
        powerTxt.setAttribute("class", "power-none header-neon-white-sm");
        powerOn = false;
        ballRadius = containerSize / 100;
    }, powerTimeout);
}

// Reanuda el juego (quita el tutorial y comienza el bucle)
function resume() {
    // Si el juego está pausado, se quita el tutorial y se reanuda el juego
    if (paused) {
        document.getElementById("tutorial").classList.add("d-none");
        paused = false;
        gameLoop = setInterval(draw, drawInterval);
    }
}

// Pausa el juego (detiene el bucle principal)
function pause() {
    // Si el juego no está pausado, se pausa el juego
    if (!paused) {
        paused = true;
        clearInterval(gameLoop);
    }
}

// Muestra el ranking final y detiene el juego
function showRanking() {
    document.getElementById("finalScore").textContent = score;
    document.getElementById("finalDestroyedBlocks").textContent = totalBricksDestroyed;
    document.getElementById("finalDate").textContent = new Date().toLocaleDateString();

    document.getElementById("inputFinalScore").value = score;
    document.getElementById("inputFinalDestroyedBlocks").value = totalBricksDestroyed;
    document.getElementById("inputFinalDate").value = new Date().toISOString();

    document.getElementById("rankingForm").classList.remove("d-none");
    showingRanking = true;
    clearInterval(gameLoop);
}

// Termina el juego y muestra el ranking
function gameOver() {
    showRanking();
}

// Inicialización del juego: carga ladrillos, listeners y dibuja el primer frame
loadBricks();
setupKeyListeners();
powerTxt.innerHTML = 'None';
powerTxt.setAttribute("class", "power-none header-neon-white-sm");
draw();