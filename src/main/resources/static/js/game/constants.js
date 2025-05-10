// Game constants
const GAME_CONSTANTS = {
    // Canvas
    CANVAS_ID: 'miCanvas',
    
    // Ball
    BALL_RADIUS: 7,
    BALL_EXPANDED_RADIUS: 14,
    BALL_COLOR: '#A52A2A',
    BALL_STROKE: 'black',
    BALL_STROKE_WIDTH: 2,
    INITIAL_BALL_VELOCITY_X: 0,
    INITIAL_BALL_VELOCITY_Y: 3,
    
    // Paddle
    PADDLE_HEIGHT: 10,
    PADDLE_WIDTH: 100,
    PADDLE_COLOR: '#A52A2A',
    PADDLE_STROKE: 'black',
    PADDLE_STROKE_WIDTH: 2,
    PADDLE_SPEED: 5,
    PADDLE_Y_OFFSET: 20,
    
    // Brick types
    BRICK_TYPES: {
        NORMAL: 'normal',
        EXPLOSIVE: 'explosivo',
        METAL: 'metal',
        LIFE: 'vida'
    },
    
    // Brick colors
    BRICK_COLORS: {
        NORMAL_BLUE: 'blue',
        NORMAL_GREEN: 'green',
        EXPLOSIVE: 'red',
        METAL: 'gray',
        METAL_HIT: 'yellow',
        LIFE: 'pink',
        POWER_EXPLOSIVE: 'orange',
        POWER_EXPAND: 'olive'
    },
    
    // Power types
    POWER_TYPES: {
        NONE: 'nada',
        EXPLOSIVE: 'explosivo',
        EXPAND: 'agrandar'
    },
    
    // Power durations (ms)
    POWER_DURATIONS: {
        EXPAND: 5000,
        EXPLOSIVE: 3000
    },
    
    // Points
    POINTS: {
        NORMAL: 100,
        EXPLOSIVE: 200,
        METAL: 200,
        LIFE: 1000
    },
    
    // Game settings
    INITIAL_LIVES: 3,
    FRAME_RATE: 10, // ms between frames
    
    // Probabilities (%)
    PROBABILITIES: {
        EXPLOSIVE_BRICK: 5,
        NORMAL_BLUE_BRICK: 39,
        NORMAL_GREEN_BRICK: 44,
        METAL_BRICK: 11.9,
        LIFE_BRICK: 0.1,
        POWER_EXPLOSIVE: 2,
        POWER_EXPAND: 2
    }
};

// Game states
const GAME_STATES = {
    PAUSED: 'paused',
    PLAYING: 'playing',
    GAME_OVER: 'gameOver',
    SHOWING_RANKING: 'showingRanking',
    WIN: 'win'
};

// Key codes
const KEY_CODES = {
    RIGHT: 'ArrowRight',
    LEFT: 'ArrowLeft',
    ENTER: 'Enter',
    SPACE: ' ',
    R: 'r'
};
