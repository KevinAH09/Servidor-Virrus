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
public class HiloCliente implements Runnable{
    public String JugPas;
    public HiloCliente(String name) {
        this.JugPas = name;
    }

    
    @Override
    public void run() {
        for (Jugador object : MainServidor.juegoMain.jugadores) {
            try {
                if(!object.nickname.equals(JugPas)){
                    System.out.println("HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                    Conexion.HOST = object.ip;
                    System.out.println("Iniciando cliente\n");
                    Cliente cli = new Cliente(); //Se crea el cliente
                    cli.startClient();
                }
            } catch (IOException ex) {
                Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
}
