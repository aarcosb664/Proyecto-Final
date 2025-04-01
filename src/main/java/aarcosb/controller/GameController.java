package aarcosb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import aarcosb.Jugador;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class GameController {

    private static final String RANKING_FILE = "ranking.txt";

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/ranking")
    public String ranking(Model model) {
        List<Jugador> jugadores = leerJugadoresDesdeArchivo();
        model.addAttribute("jugadores", jugadores);
        return "ranking";
    }
    
    @PostMapping("/ranking")
    public String guardarRanking(
            @RequestParam("nombreJugador") String nombre,
            @RequestParam("puntuacionFinal") int puntos,
            @RequestParam("bloquesDestruidosFinal") int bloquesDestruidos,
            @RequestParam("fechaFinal") String fecha,
            Model model) {
        
        // Guardar el jugador en el archivo
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RANKING_FILE, true))) {
            bw.write(nombre + ", " + puntos + ", " + bloquesDestruidos + ", " + fecha);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Leer todos los jugadores para mostrar el ranking
        List<Jugador> jugadores = leerJugadoresDesdeArchivo();
        model.addAttribute("jugadores", jugadores);
        
        return "ranking";
    }
    
    private List<Jugador> leerJugadoresDesdeArchivo() {
        List<Jugador> jugadores = new ArrayList<>();
        
        try {
            File file = new File(RANKING_FILE);
            if (!file.exists()) {
                file.createNewFile();
                return jugadores;
            }
            
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(", ");
                    if (datos.length >= 4) {
                        jugadores.add(new Jugador(
                                datos[0], 
                                Integer.parseInt(datos[1]), 
                                Integer.parseInt(datos[2]), 
                                datos[3]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Ordenar jugadores por puntuación (mayor a menor)
        jugadores.sort(Comparator.comparing(Jugador::getPuntos).reversed());
        
        return jugadores;
    }
} 