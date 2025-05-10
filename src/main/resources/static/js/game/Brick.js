class Brick {
    constructor(x, y, width, height, type, power) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.power = power;
        this.destroyed = false;
        this.color = this.getColorByType();
    }
    
    getColorByType() {
        switch(this.type) {
            case GAME_CONSTANTS.BRICK_TYPES.EXPLOSIVE:
                return GAME_CONSTANTS.BRICK_COLORS.EXPLOSIVE;
            case GAME_CONSTANTS.BRICK_TYPES.METAL:
                return GAME_CONSTANTS.BRICK_COLORS.METAL;
            case GAME_CONSTANTS.BRICK_TYPES.LIFE:
                return GAME_CONSTANTS.BRICK_COLORS.LIFE;
            case GAME_CONSTANTS.BRICK_TYPES.NORMAL:
                return Math.random() < 0.5 ? 
                    GAME_CONSTANTS.BRICK_COLORS.NORMAL_BLUE : 
                    GAME_CONSTANTS.BRICK_COLORS.NORMAL_GREEN;
            default:
                return GAME_CONSTANTS.BRICK_COLORS.NORMAL_BLUE;
        }
    }
    
    convertToNormal() {
        this.type = GAME_CONSTANTS.BRICK_TYPES.NORMAL;
        this.color = GAME_CONSTANTS.BRICK_COLORS.METAL_HIT;
    }
    
    draw(ctx) {
        if (!this.destroyed) {
            ctx.beginPath();
            ctx.rect(this.x, this.y, this.width, this.height);
            ctx.fillStyle = this.color;
            ctx.fill();
            ctx.strokeStyle = 'black';
            ctx.lineWidth = 2;
            ctx.stroke();
            ctx.closePath();
        }
    }
    
    getPoints() {
        switch(this.type) {
            case GAME_CONSTANTS.BRICK_TYPES.EXPLOSIVE:
                return GAME_CONSTANTS.POINTS.EXPLOSIVE;
            case GAME_CONSTANTS.BRICK_TYPES.METAL:
                return GAME_CONSTANTS.POINTS.METAL;
            case GAME_CONSTANTS.BRICK_TYPES.LIFE:
                return GAME_CONSTANTS.POINTS.LIFE;
            default:
                return GAME_CONSTANTS.POINTS.NORMAL;
        }
    }
}
