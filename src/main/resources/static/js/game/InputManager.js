class InputManager {
    constructor(game) {
        this.game = game;
        this.rightPressed = false;
        this.leftPressed = false;
        
        this.setupEventListeners();
    }
    
    setupEventListeners() {
        document.addEventListener('keydown', this.handleKeyDown.bind(this));
        document.addEventListener('keyup', this.handleKeyUp.bind(this));
    }
    
    handleKeyDown(event) {
        switch(event.key) {
            case KEY_CODES.RIGHT:
                this.rightPressed = true;
                break;
            case KEY_CODES.LEFT:
                this.leftPressed = true;
                break;
            case KEY_CODES.ENTER:
                if (this.game.isShowingRanking()) {
                    break;
                }
                
                if (this.game.isPaused()) {
                    this.game.resume();
                } else {
                    this.game.pause();
                }
                break;
            case KEY_CODES.R:
                this.game.showRanking();
                break;
            case KEY_CODES.SPACE:
                this.game.usePower();
                break;
        }
    }
    
    handleKeyUp(event) {
        switch(event.key) {
            case KEY_CODES.RIGHT:
                this.rightPressed = false;
                break;
            case KEY_CODES.LEFT:
                this.leftPressed = false;
                break;
        }
    }
    
    isRightPressed() {
        return this.rightPressed;
    }
    
    isLeftPressed() {
        return this.leftPressed;
    }
}
