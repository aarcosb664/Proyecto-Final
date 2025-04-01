package aarcosb;

public class Jugador {
    private String nombre;
    private int puntos;
    private int bloquesDestruidosTotales;
    private String fecha;

    public Jugador(String nombre, int puntos, int bloquesDestruidosTotales, String fecha) {
        this.nombre = nombre;
        this.puntos = puntos;
        this.bloquesDestruidosTotales = bloquesDestruidosTotales;
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getBloquesDestruidosTotales() {
        return bloquesDestruidosTotales;
    }

    public String getFecha() {
        return fecha;
    }
}