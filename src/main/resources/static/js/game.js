// =============================
// 🎮 GAME CONSTANTS - BREAKOUT
// =============================

// Canvas and context
const canvas = document.getElementById('miCanvas');
const ctx = canvas.getContext('2d');
const gameContainer = document.querySelector('.game-container');
const containerSize = Math.min(gameContainer.clientWidth, gameContainer.clientHeight);
canvas.width = containerSize;
canvas.height = containerSize;

// Brick grid ranges
const minCols = 12;
const maxCols = 23;
const minRows = 8;
const maxRows = 19;

// Points per brick type
const pointsNormal = 100;
const pointsMetal = 200;
const pointsLife = 1000;

// Speeds and timeouts
const ballInitialVelocity = 3;
const drawInterval = 10; // ms between frames
const powerTimeout = 8000; // ms power duration

// Ball properties
let ballX = containerSize / 2;
let ballY = containerSize / 1.5;
let ballVelocityX = 0;
let ballVelocityY = ballInitialVelocity;
let ballRadius = containerSize / 100;

// Paddle properties
let paddleWidth = containerSize / 6;
let paddleHeight = containerSize / 80;
let paddleX = (containerSize - paddleWidth) / 2;
let paddleY = containerSize - 20;
let paddleSpeed = 5;
let rightPressed = false;
let leftPressed = false;

// Bricks
let bricks = [];

// Game state
let lives = 3;
let score = 0;
let bricksDestroyed = 0;
let totalBricksDestroyed = 0;
let paused = true;
let powerOn = false;
let showingRanking = false;
let gameLoop = null;

// Colors and powers
const ballColor = '#A52A2A';
const paddleColor = '#A52A2A';
const brickColors = {
    normalBlue: 'blue',
    normalGreen: 'green',
    explosive: 'red',
    metal: 'gray',
    metalHit: 'yellow',
    life: 'pink',
    powerExplosive: 'orange',
    powerExpand: 'olive'
};

let cols, rows, brickWidth, brickHeight;

const powerTypes = {
    none: 'none',
    explosive: 'explosive',
    expand: 'expand'
};

let powerTxt;
const powerTxts = document.querySelectorAll("#powerTxt");
if (window.innerWidth < 992) {
    powerTxt = powerTxts[1];
} else {
    powerTxt = powerTxts[0];
}

const mobileLeft = document.getElementById("mobileLeft");
const mobileRight = document.getElementById("mobileRight");
const mobileContainer = document.getElementById("mobileContainer");

// =============================
// 🎮 GAME LOGIC - BREAKOUT
// =============================

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

function loadBricks() {
    cols = Math.floor(Math.random() * (maxCols - minCols + 1)) + minCols;
    rows = Math.floor(Math.random() * (maxRows - minRows + 1)) + minRows;
    brickWidth = canvas.width / cols;
    brickHeight = (canvas.height / 2) / rows;
    
    bricks = [];
    for (let col = 0; col < cols; col++) {
        bricks[col] = [];
        for (let row = 0; row < rows; row++) {
            let attributeChance = Math.random() * 100;
            let attribute, color;

            if (attributeChance < 5) {
                attribute = powerTypes.explosive;
                color = brickColors.explosive;
            } else if (attributeChance < 44) {
                attribute = "normal";
                color = brickColors.normalBlue;
            } else if (attributeChance < 88) {
                attribute = "normal";
                color = brickColors.normalGreen;
            } else if (attributeChance < 99.9) {
                attribute = "metal";
                color = brickColors.metal;
            } else {
                attribute = "life";
                color = brickColors.life;
            }

            let powerChance = Math.random() * 100;
            let power;

            if (powerChance < 2) {
                power = powerTypes.explosive;
                color = brickColors.powerExplosive;
            } else if (powerChance < 4) {
                power = powerTypes.expand;
                color = brickColors.powerExpand;
            } else {
                power = powerTypes.none;
            }

            bricks[col][row] = {
                destroyed: false,
                color: color,
                attribute: attribute,
                power: power
            };
        }
    }
}

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

function brickCollision() {
    for (let col = 0; col < cols; col++) {
        for (let row = 0; row < rows; row++) {
            const brick = bricks[col][row];
            if (!brick.destroyed &&
                ballX + ballRadius > brick.x && ballX - ballRadius < brick.x + brickWidth &&
                ballY + ballRadius > brick.y && ballY - ballRadius < brick.y + brickHeight) {

                if (ballY >= brick.y && ballY <= brick.y + brickHeight) ballVelocityX = -ballVelocityX;
                if (ballX >= brick.x && ballX <= brick.x + brickWidth) ballVelocityY = -ballVelocityY;

                if (powerOn && powerTxt.classList.contains('power-explosive')) {
                    brick.attribute = powerTypes.explosive;
                }

                if (brick.attribute === "normal") {
                    score += pointsNormal;
                    brick.destroyed = true;
                } else if (brick.attribute === powerTypes.explosive) {
                    explode(col, row);
                } else if (brick.attribute === "life") {
                    lives++;
                    brick.destroyed = true;
                    score += pointsLife;
                } else if (brick.attribute === "metal") {
                    brick.color = brickColors.metalHit;
                    brick.attribute = "normal";
                    score += pointsMetal;
                }

                bricksDestroyed++;
                totalBricksDestroyed++;
                setPower(brick);
            }
        }
    }
}

function explode(col, row) {
    const brick = bricks[col][row];
    if (!brick.destroyed) {
        brick.destroyed = true;
        score += pointsMetal;
        bricksDestroyed++;
        totalBricksDestroyed++;

        for (let dCol = -1; dCol <= 1; dCol++) {
            for (let dRow = -1; dRow <= 1; dRow++) {
                try {
                    const adjacent = bricks[col + dCol][row + dRow];

                    if (adjacent.attribute === powerTypes.explosive && !adjacent.destroyed) {
                        explode(col + dCol, row + dRow);
                    } else if (adjacent.attribute === "metal" && !adjacent.destroyed) {
                        adjacent.color = brickColors.metalHit;
                        adjacent.attribute = "normal";
                        score += pointsMetal;
                    } else if (!adjacent.destroyed) {
                        adjacent.destroyed = true;
                        score += pointsNormal;
                        bricksDestroyed++;
                        totalBricksDestroyed++;
                    }

                    if (adjacent.power !== powerTypes.none) {
                        setPower(adjacent);
                    }
                } catch (error) {
                    console.error("Error destroying adjacent bricks: ", error);
                }
            }
        }
    }
}

function setPower(brick) {
    if (brick.power === powerTypes.explosive && !powerOn) {
        powerTxt.innerHTML = 'Explosive';
        powerTxt.setAttribute("class", "power-explosive header-neon-red-sm");
    }

    if (brick.power === powerTypes.expand && !powerOn) {
        powerTxt.innerHTML = 'Expand';
        powerTxt.setAttribute("class", "power-expand header-neon-green-sm");
    }
}

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

function reset() {
    powerOn = false;
    ballX = canvas.width / 2;
    ballY = canvas.height / 1.8;
    ballVelocityX = 0;
    ballVelocityY = ballInitialVelocity;
    paddleX = (canvas.width - paddleWidth) / 2;
}

function draw() {
    if (bricksDestroyed === cols * rows) {
        clearInterval(gameLoop);
        alert("Congratulations, you won!");
        reset();
        bricksDestroyed = 0;
        loadBricks();
        draw();
        return;
    }

    ctx.clearRect(0, 0, containerSize, containerSize);

    drawBall();
    drawPaddle();
    drawBricks();
    brickCollision();

    document.getElementById("lives").innerHTML = lives;
    document.getElementById("score").innerHTML = score;
    document.getElementById("destroyedBlocks").innerHTML = bricksDestroyed;
    document.getElementById("rows").innerHTML = rows;
    document.getElementById("columns").innerHTML = cols;

    document.getElementById("livesMobile").innerHTML = lives;
    document.getElementById("scoreMobile").innerHTML = score;
    document.getElementById("destroyedBlocksMobile").innerHTML = bricksDestroyed;
    document.getElementById("rowsMobile").innerHTML = rows;
    document.getElementById("columnsMobile").innerHTML = cols;

    if (rightPressed && paddleX + paddleWidth < containerSize) { 
        paddleX += paddleSpeed; 
    }

    if (leftPressed && paddleX > 0) { 
        paddleX -= paddleSpeed; 
    }

    if (ballX + ballVelocityX > containerSize - ballRadius || ballX + ballVelocityX < ballRadius) {
        ballVelocityX = -ballVelocityX;
    }

    if (ballY + ballVelocityY < ballRadius) {
        ballVelocityY = -ballVelocityY;
    }

    if (
        ballY + ballVelocityY > paddleY - ballRadius &&
        ballX > paddleX &&
        ballX < paddleX + paddleWidth
    ) {
        ballVelocityY = -ballVelocityY;
        ballVelocityX = ((ballX - (paddleX + paddleWidth / 2)) / (paddleWidth / 2)) * 5;
    }

    if (lives <= 0) {
        gameOver();
        return;
    }

    if (ballY + ballVelocityY > canvas.height - ballRadius) {
        lives--;
        reset();
        pause();
        return;
    }

    ballX += ballVelocityX;
    ballY += ballVelocityY;
}

function usePower() {
    powerOn = true;

    if (powerTxt.classList.contains('power-expand')) {
        ballRadius = 25;
    }

    setTimeout(() => {
        powerTxt.innerHTML = 'None';
        powerTxt.setAttribute("class", "power-none header-neon-white-sm");
        powerOn = false;
        ballRadius = containerSize / 100;
    }, powerTimeout);
}

function resume() {
    if (paused) {
        document.getElementById("tutorial").classList.add("d-none");
        paused = false;
        gameLoop = setInterval(draw, drawInterval);
    }
}

function pause() {
    if (!paused) {
        paused = true;
        clearInterval(gameLoop);
    }
}

function showRanking() {
    document.getElementById("finalScore").innerHTML = score;
    document.getElementById("finalDestroyedBlocks").innerHTML = totalBricksDestroyed;
    document.getElementById("finalDate").innerHTML = new Date().toLocaleDateString();

    document.getElementById("inputFinalScore").value = score;
    document.getElementById("inputFinalDestroyedBlocks").value = totalBricksDestroyed;
    document.getElementById("inputFinalDate").value = new Date().toISOString();

    document.getElementById("rankingForm").classList.remove("d-none");
    showingRanking = true;
    clearInterval(gameLoop);
}

function gameOver() {
    showRanking();
}

function reload() {
    window.location.reload();
}

loadBricks();
setupKeyListeners();
powerTxt.innerHTML = 'None';
powerTxt.setAttribute("class", "power-none header-neon-white-sm");
draw();