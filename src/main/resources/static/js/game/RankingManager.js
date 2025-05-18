class RankingManager {
    constructor(game) {
        this.game = game;
    }
    
    showRankingScreen() {
        this.game.pause();
        document.getElementById('puntuacionFinal').innerHTML = "Final Score: " + this.game.getPoints();
        document.getElementById('bloquesDestruidosFinal').innerHTML = "Total Destroyed Blocks: " + this.game.getTotalDestroyedBricks();
        document.getElementById('fecha').innerHTML = "Date: " + new Date().toLocaleString();
        document.getElementById('ranking').classList.remove('d-none');
    }
    
    saveRanking() {
        document.getElementById('inputPuntuacionFinal').value = this.game.getPoints();
        document.getElementById('inputBloquesDestruidosFinal').value = this.game.getTotalDestroyedBricks();
        document.getElementById('inputFechaFinal').value = new Date().toLocaleString();
        
        // Submit the form
        document.getElementById('ranking').submit();
    }
}
