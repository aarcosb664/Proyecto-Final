class Ball {
    constructor(canvas) {
        this.canvas = canvas;
        this.ctx = canvas.getContext('2d');
        this.reset();
    }
    
    reset() {
        this.x = this.canvas.width / 2;
        this.y = this.canvas.height / 1.8;
        this.velocityX = GAME_CONSTANTS.INITIAL_BALL_VELOCITY_X;
        this.velocityY = GAME_CONSTANTS.INITIAL_BALL_VELOCITY_Y;
        this.radius = GAME_CONSTANTS.BALL_RADIUS;
    }
    
    expand() {
        this.radius = GAME_CONSTANTS.BALL_EXPANDED_RADIUS;
    }
    
    normalize() {
        this.radius = GAME_CONSTANTS.BALL_RADIUS;
    }
    
    update() {
        this.x += this.velocityX;
        this.y += this.velocityY;
        
        // Wall collisions
        if (this.x + this.velocityX > this.canvas.width - this.radius || 
            this.x + this.velocityX < this.radius) {
            this.velocityX = -this.velocityX;
        }
        
        // Ceiling collision
        if (this.y + this.velocityY < this.radius) {
            this.velocityY = -this.velocityY;
        }
    }
    
    draw() {
        this.ctx.beginPath();
        this.ctx.arc(this.x, this.y, this.radius, 0, Math.PI * 2);
        this.ctx.fillStyle = GAME_CONSTANTS.BALL_COLOR;
        this.ctx.fill();
        this.ctx.strokeStyle = GAME_CONSTANTS.BALL_STROKE;
        this.ctx.lineWidth = GAME_CONSTANTS.BALL_STROKE_WIDTH;
        this.ctx.stroke();
        this.ctx.closePath();
    }
    
    collidesWith(paddle) {
        if (this.y + this.velocityY > this.canvas.height - paddle.yOffset - this.radius && 
            this.x > paddle.x && 
            this.x < paddle.x + paddle.width) {
            
            this.velocityY = -this.velocityY;
            
            // Change horizontal direction based on where the ball hit the paddle
            this.velocityX = (this.x - (paddle.x + paddle.width / 2)) / (paddle.width / 2) * 4;
            
            return true;
        }
        return false;
    }
    
    isOutOfBounds() {
        return this.y + this.velocityY > this.canvas.height - this.radius;
    }
}
