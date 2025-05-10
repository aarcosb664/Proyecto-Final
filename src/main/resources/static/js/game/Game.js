class Game {
    constructor() {
        this.canvas = document.getElementById(GAME_CONSTANTS.CANVAS_ID);
        this.ctx = this.canvas.getContext('2d');
        
        this.state = GAME_STATES.PAUSED;
        this.lives = GAME_CONSTANTS.INITIAL_LIVES;
        this.points = 0;
        this.gameInterval = null;
        
        // Initialize components
        this.ball = new Ball(this.canvas);
        this.paddle = new Paddle(this.canvas);
        this.brickManager = new BrickManager(this.canvas, this);
        this.powerManager = new PowerManager(this);
        this.inputManager = new InputManager(this);
        this.rankingManager = new RankingManager(this);
        
        // Initialize game
        this.updateUI();
        this.draw();
    }
    
    start() {
        if (this.state === GAME_STATES.PAUSED) {
            document.getElementById('tutorial').style.display = 'none';
            this.state = GAME_STATES.PLAYING;
            this.gameInterval = setInterval(this.update.bind(this), GAME_CONSTANTS.FRAME_RATE);
        }
    }
    
    pause() {
        if (this.state === GAME_STATES.PLAYING) {
            this.state = GAME_STATES.PAUSED;
            clearInterval(this.gameInterval);
        }
    }
    
    resume() {
        this.start();
    }
    
    reset() {
        this.ball.reset();
        this.paddle.reset();
    }
    
    update() {
        // Check for win condition
        if (this.brickManager.areAllBricksDestroyed()) {
            this.handleWin();
            return;
        }
        
        // Clear canvas
        this.ctx.clearRect(0, 0, this.canvas.width, this.canvas.height);
        
        // Update ball position
        this.ball.update();
        
        // Check for paddle collision
        this.ball.collidesWith(this.paddle);
        
        // Check for brick collisions
        this.brickManager.checkCollision(this.ball);
        
        // Update paddle position based on input
        if (this.inputManager.isRightPressed()) {
            this.paddle.moveRight();
        }
        if (this.inputManager.isLeftPressed()) {
            this.paddle.moveLeft();
        }
        
        // Check if ball is out of bounds
        if (this.ball.isOutOfBounds()) {
            this.handleBallLost();
            return;
        }
        
        // Draw everything
        this.draw();
        
        // Update UI
        this.updateUI();
    }
    
    draw() {
        this.ball.draw();
        this.paddle.draw();
        this.brickManager.draw();
    }
    
    updateUI() {
        document.getElementById('vidas').innerHTML = this.lives;
        document.getElementById('puntos').innerHTML = this.points;
        document.getElementById('bloquesDestruidos').innerHTML = this.brickManager.destroyedBricks;
        document.getElementById('filas').innerHTML = this.brickManager.rows;
        document.getElementById('columnas').innerHTML = this.brickManager.columns;
    }
    
    handleBallLost() {
        this.lives--;
        
        if (this.lives <= 0) {
            this.gameOver();
        } else {
            this.reset();
            this.draw();
            this.pause();
        }
    }
    
    handleWin() {
        clearInterval(this.gameInterval);
        alert('¡Felicidades, has ganado!');
        this.reset();
        this.brickManager.reset();
        this.draw();
        this.pause();
    }
    
    gameOver() {
        this.state = GAME_STATES.GAME_OVER;
        this.showRanking();
    }
    
    showRanking() {
        this.state = GAME_STATES.SHOWING_RANKING;
        this.rankingManager.showRankingScreen();
    }
    
    usePower() {
        if (this.powerManager.getActivePower() !== GAME_CONSTANTS.POWER_TYPES.NONE) {
            this.powerManager.activatePower(this.powerManager.getActivePower());
        }
    }
    
    // Utility methods
    isPaused() {
        return this.state === GAME_STATES.PAUSED;
    }
    
    isShowingRanking() {
        return this.state === GAME_STATES.SHOWING_RANKING;
    }
    
    addPoints(points) {
        this.points += points;
    }
    
    addLife() {
        this.lives++;
    }
    
    getPoints() {
        return this.points;
    }
    
    getTotalDestroyedBricks() {
        return this.brickManager.totalDestroyedBricks;
    }
}

// Initialize the game when the page loads
document.addEventListener('DOMContentLoaded', () => {
    const game = new Game();
    
    // Initialize power display
    document.getElementById('poderTxt').innerHTML = 'Nada';
    document.getElementById('poderTxt').style.color = 'black';
    
    // Expose the game object to the global scope for debugging
    window.game = game;
});
