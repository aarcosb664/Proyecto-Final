class RankingManager {
    constructor(game) {
        this.game = game;
    }
    
    showRankingScreen() {
        this.game.pause();
        document.getElementById('puntuacionFinal').innerHTML = this.game.getPoints();
        document.getElementById('bloquesDestruidosFinal').innerHTML = this.game.getTotalDestroyedBricks();
        document.getElementById('fecha').innerHTML = new Date().toLocaleString();
        document.getElementById('ranking').style.display = 'block';
    }
    
    saveRanking() {
        document.getElementById('inputPuntuacionFinal').value = this.game.getPoints();
        document.getElementById('inputBloquesDestruidosFinal').value = this.game.getTotalDestroyedBricks();
        document.getElementById('inputFechaFinal').value = new Date().toLocaleString();
        
        document.getElementById('rankingForm').submit();
    }
}
