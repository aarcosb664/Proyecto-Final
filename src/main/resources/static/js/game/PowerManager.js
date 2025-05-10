class PowerManager {
    constructor(game) {
        this.game = game;
        this.activePower = GAME_CONSTANTS.POWER_TYPES.NONE;
        this.powerActive = false;
        this.powerTimer = null;
    }
    
    activatePower(powerType) {
        if (this.powerActive) return;
        
        this.activePower = powerType;
        this.powerActive = true;
        this.updatePowerDisplay();
        
        let duration = GAME_CONSTANTS.POWER_DURATIONS.EXPLOSIVE;
        
        if (powerType === GAME_CONSTANTS.POWER_TYPES.EXPAND) {
            this.game.ball.expand();
            duration = GAME_CONSTANTS.POWER_DURATIONS.EXPAND;
        }
        
        // Clear any existing timer
        if (this.powerTimer) {
            clearTimeout(this.powerTimer);
        }
        
        // Set a new timer
        this.powerTimer = setTimeout(() => {
            this.deactivatePower();
        }, duration);
    }
    
    deactivatePower() {
        this.activePower = GAME_CONSTANTS.POWER_TYPES.NONE;
        this.powerActive = false;
        this.game.ball.normalize();
        this.updatePowerDisplay();
    }
    
    updatePowerDisplay() {
        const powerTxt = document.getElementById('poderTxt');
        
        switch(this.activePower) {
            case GAME_CONSTANTS.POWER_TYPES.EXPLOSIVE:
                powerTxt.innerHTML = 'Explosivo';
                powerTxt.style.color = 'darkred';
                break;
            case GAME_CONSTANTS.POWER_TYPES.EXPAND:
                powerTxt.innerHTML = 'Agrandar';
                powerTxt.style.color = 'darkgreen';
                break;
            default:
                powerTxt.innerHTML = 'Nada';
                powerTxt.style.color = 'black';
        }
    }
    
    isPowerActive() {
        return this.powerActive;
    }
    
    getActivePower() {
        return this.activePower;
    }
}
