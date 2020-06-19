/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viruss.model;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author colo7
 */
public class HiloCliente implements Runnable {

    
    public String JugPas;
    public Juego juego ;
    public HiloCliente(String name,Juego j) {
        this.JugPas = name;
        this.juego = j;
    }

    @Override
    public void run() {
        if (Servidor.semaforo) {
            Servidor.semaforo = false;
            for (Jugador object : juego.jugadores) {
                try {
                    if (!object.nickname.equals(JugPas)) {
                        Conexion.HOST = object.ip;
                        Cliente cli = new Cliente(); //Se crea el cliente
                        cli.startClient();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);

                }
            }
            Servidor.semaforo = true;
        }

    }

}
