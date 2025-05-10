class Paddle {
    constructor(canvas) {
        this.canvas = canvas;
        this.ctx = canvas.getContext('2d');
        this.width = GAME_CONSTANTS.PADDLE_WIDTH;
        this.height = GAME_CONSTANTS.PADDLE_HEIGHT;
        this.yOffset = GAME_CONSTANTS.PADDLE_Y_OFFSET;
        this.speed = GAME_CONSTANTS.PADDLE_SPEED;
        this.reset();
    }
    
    reset() {
        this.x = this.canvas.width / 2 - this.width / 2;
    }
    
    moveLeft() {
        if (this.x > 0) {
            this.x -= this.speed;
        }
    }
    
    moveRight() {
        if (this.x + this.width < this.canvas.width) {
            this.x += this.speed;
        }
    }
    
    draw() {
        this.ctx.beginPath();
        this.ctx.rect(this.x, this.canvas.height - this.yOffset, this.width, this.height);
        this.ctx.fillStyle = GAME_CONSTANTS.PADDLE_COLOR;
        this.ctx.fill();
        this.ctx.strokeStyle = GAME_CONSTANTS.BALL_STROKE;
        this.ctx.lineWidth = GAME_CONSTANTS.BALL_STROKE_WIDTH;
        this.ctx.stroke();
        this.ctx.closePath();
    }
}
