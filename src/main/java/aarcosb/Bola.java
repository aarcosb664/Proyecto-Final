package aarcosb;

public class Bola {
    private double bX; 
    private double bY; 
    private double radio;
    private double vX;
    private double vY;

    public Bola() {
        bX = 500;
        bY = 450;
        radio = 7;
        vX = 0;
        vY = 3;
    }

    public double getbX() {
        return bX;
    }

    public double getbY() {
        return bY;
    }

    public double getRadio() {
        return radio;
    }

    public double getvX() {
        return vX;
    }

    public double getvY() {
        return vY;
    }
}