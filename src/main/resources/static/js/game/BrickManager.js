class BrickManager {
    constructor(canvas, game) {
        this.canvas = canvas;
        this.ctx = canvas.getContext('2d');
        this.game = game;
        this.bricks = [];
        this.columns = Math.floor(Math.random() * 12) + 12;
        this.rows = Math.floor(Math.random() * 12) + 8;
        this.brickWidth = canvas.width / this.columns;
        this.brickHeight = (canvas.height / 2) / this.rows;
        this.destroyedBricks = 0;
        this.totalDestroyedBricks = 0;
        
        this.initBricks();
    }
    
    initBricks() {
        this.bricks = [];
        
        for (let c = 0; c < this.columns; c++) {
            this.bricks[c] = [];
            for (let r = 0; r < this.rows; r++) {
                const brickType = this.getRandomBrickType();
                const powerType = this.getRandomPowerType();
                
                // Override color if it has a power
                let finalType = brickType;
                let finalColor = null;
                
                if (powerType === GAME_CONSTANTS.POWER_TYPES.EXPLOSIVE) {
                    finalColor = GAME_CONSTANTS.BRICK_COLORS.POWER_EXPLOSIVE;
                } else if (powerType === GAME_CONSTANTS.POWER_TYPES.EXPAND) {
                    finalColor = GAME_CONSTANTS.BRICK_COLORS.POWER_EXPAND;
                }
                
                const brick = new Brick(
                    c * this.brickWidth,
                    r * this.brickHeight,
                    this.brickWidth,
                    this.brickHeight,
                    finalType,
                    powerType
                );
                
                if (finalColor) {
                    brick.color = finalColor;
                }
                
                this.bricks[c][r] = brick;
            }
        }
    }
    
    getRandomBrickType() {
        const rand = Math.floor(Math.random() * 100) + 1;
        const p = GAME_CONSTANTS.PROBABILITIES;
        
        if (rand < p.EXPLOSIVE_BRICK) {
            return GAME_CONSTANTS.BRICK_TYPES.EXPLOSIVE;
        } else if (rand < p.EXPLOSIVE_BRICK + p.NORMAL_BLUE_BRICK) {
            return GAME_CONSTANTS.BRICK_TYPES.NORMAL;
        } else if (rand < p.EXPLOSIVE_BRICK + p.NORMAL_BLUE_BRICK + p.NORMAL_GREEN_BRICK) {
            return GAME_CONSTANTS.BRICK_TYPES.NORMAL;
        } else if (rand < p.EXPLOSIVE_BRICK + p.NORMAL_BLUE_BRICK + p.NORMAL_GREEN_BRICK + p.METAL_BRICK) {
            return GAME_CONSTANTS.BRICK_TYPES.METAL;
        } else {
            return GAME_CONSTANTS.BRICK_TYPES.LIFE;
        }
    }
    
    getRandomPowerType() {
        const rand = Math.floor(Math.random() * 100) + 1;
        const p = GAME_CONSTANTS.PROBABILITIES;
        
        if (rand < p.POWER_EXPLOSIVE) {
            return GAME_CONSTANTS.POWER_TYPES.EXPLOSIVE;
        } else if (rand < p.POWER_EXPLOSIVE + p.POWER_EXPAND) {
            return GAME_CONSTANTS.POWER_TYPES.EXPAND;
        } else {
            return GAME_CONSTANTS.POWER_TYPES.NONE;
        }
    }
    
    draw() {
        for (let c = 0; c < this.columns; c++) {
            for (let r = 0; r < this.rows; r++) {
                const brick = this.bricks[c][r];
                brick.draw(this.ctx);
            }
        }
    }
    
    checkCollision(ball) {
        for (let c = 0; c < this.columns; c++) {
            for (let r = 0; r < this.rows; r++) {
                const brick = this.bricks[c][r];
                
                if (!brick.destroyed) {
                    if (this.isBallCollidingWithBrick(ball, brick)) {
                        this.handleCollision(ball, brick, c, r);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    isBallCollidingWithBrick(ball, brick) {
        return (
            ball.x + ball.radius > brick.x && 
            ball.x - ball.radius < brick.x + brick.width && 
            ball.y + ball.radius > brick.y && 
            ball.y - ball.radius < brick.y + brick.height
        );
    }
    
    handleCollision(ball, brick, c, r) {
        // Determine collision direction
        if (ball.y >= brick.y && ball.y <= brick.y + brick.height) {
            ball.velocityX = -ball.velocityX;
        }
        if (ball.x >= brick.x && ball.x <= brick.x + brick.width) {
            ball.velocityY = -ball.velocityY;
        }
        
        // Apply explosive power if active
        if (this.game.powerManager.isPowerActive() && 
            this.game.powerManager.getActivePower() === GAME_CONSTANTS.POWER_TYPES.EXPLOSIVE) {
            brick.type = GAME_CONSTANTS.BRICK_TYPES.EXPLOSIVE;
        }
        
        // Handle brick type specific behavior
        switch(brick.type) {
            case GAME_CONSTANTS.BRICK_TYPES.NORMAL:
                this.game.addPoints(brick.getPoints());
                brick.destroyed = true;
                this.destroyedBricks++;
                this.totalDestroyedBricks++;
                break;
                
            case GAME_CONSTANTS.BRICK_TYPES.EXPLOSIVE:
                this.explodeBrick(c, r);
                break;
                
            case GAME_CONSTANTS.BRICK_TYPES.LIFE:
                this.game.addLife();
                this.game.addPoints(brick.getPoints());
                brick.destroyed = true;
                this.destroyedBricks++;
                this.totalDestroyedBricks++;
                break;
                
            case GAME_CONSTANTS.BRICK_TYPES.METAL:
                brick.convertToNormal();
                this.game.addPoints(brick.getPoints());
                break;
        }
        
        // Activate power if brick has one
        if (brick.power !== GAME_CONSTANTS.POWER_TYPES.NONE) {
            this.game.powerManager.activatePower(brick.power);
        }
    }
    
    // Destruye un ladrillo explosivo y sus adyacentes
    explodeBrick(c, r) {
        const brick = this.bricks[c][r];
        
        if (brick.destroyed) {
            return; // Evitar procesar ladrillos ya destruidos
        }
        
        // Destruir el ladrillo actual
        this.destroyBrick(brick);
        
        // Procesar ladrillos adyacentes
        this.processAdjacentBricks(c, r);
    }
    
    // Destruye un ladrillo individual y actualiza contadores
    destroyBrick(brick) {
        brick.destroyed = true;
        this.game.addPoints(brick.getPoints());
        this.destroyedBricks++;
        this.totalDestroyedBricks++;
    }
    
    // Procesa todos los ladrillos adyacentes a una posición
    processAdjacentBricks(c, r) {
        for (let dc = -1; dc <= 1; dc++) {
            for (let dr = -1; dr <= 1; dr++) {
                // Evitar procesar el ladrillo central nuevamente
                if (dc === 0 && dr === 0) continue;
                
                this.processAdjacentBrick(c + dc, r + dr);
            }
        }
    }
    
    // Procesa un ladrillo adyacente individual
    processAdjacentBrick(c, r) {
        try {
            const adjacentBrick = this.bricks[c][r];
            
            if (!adjacentBrick || adjacentBrick.destroyed) {
                return; // Ignorar si no existe o ya está destruido
            }
            
            this.handleAdjacentBrickByType(adjacentBrick, c, r);
            this.activateBrickPower(adjacentBrick);
            
        } catch (error) {
            console.error("Error al procesar ladrillo adyacente:", error);
        }
    }
    
    // Maneja un ladrillo adyacente según su tipo
    handleAdjacentBrickByType(brick, c, r) {
        switch(brick.type) {
            case GAME_CONSTANTS.BRICK_TYPES.EXPLOSIVE:
                this.explodeBrick(c, r);
                break;
                
            case GAME_CONSTANTS.BRICK_TYPES.METAL:
                brick.convertToNormal();
                this.game.addPoints(brick.getPoints());
                break;
                
            default: // Normal o vida
                this.destroyBrick(brick);
                break;
        }
    }
    
    // Activa el poder de un ladrillo si tiene
    activateBrickPower(brick) {
        if (brick.power !== GAME_CONSTANTS.POWER_TYPES.NONE) {
            this.game.powerManager.activatePower(brick.power);
        }
    }
    
    reset() {
        this.destroyedBricks = 0;
        this.initBricks();
    }
    
    areAllBricksDestroyed() {
        return this.destroyedBricks === this.columns * this.rows;
    }
}
