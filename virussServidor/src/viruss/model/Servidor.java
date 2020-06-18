/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viruss.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import static java.lang.Boolean.FALSE;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;

public class Servidor extends Conexion {

    public static boolean semaforo = true;
    private List<Carta> lista = new ArrayList();
    private List<Carta> listaRandom = new ArrayList();
    boolean cerrojo = true;
    Thread hilo;
    Timer timer = new Timer();
    int tic = 0;
    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            if (!MainServidor.juegoMain.conexion.equals("l")) {
                tic++;
                System.out.println(tic);
                if (tic == 10 && MainServidor.juegoMain.jugadores.size() < 1) {
                    MainServidor.band = FALSE;
                    System.out.println("ServidorCerrado");
                    MainServidor.juegoMain.conexion = "o";
                    for (Jugador object : MainServidor.juegoMain.jugadores) {
                        try {
                            Conexion.HOST = object.ip;
                            System.out.println("Iniciando cliente\n");
                            Cliente cli = new Cliente(); //Se crea el cliente
                            cli.startClient();
                        } catch (IOException ex) {
                            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    task.cancel();
                } else if (MainServidor.juegoMain.jugadores.size() >= 2 && tic == 10 && MainServidor.juegoMain.jugadores.size() != 6) {
                    repartirCartas();
                    MainServidor.juegoMain.conexion = "l";
                    for (Jugador object : MainServidor.juegoMain.jugadores) {
                        try {
                            Conexion.HOST = object.ip;
                            System.out.println("Iniciando cliente\n");
                            Cliente cli = new Cliente(); //Se crea el cliente
                            cli.startClient();
                        } catch (IOException ex) {
                            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    task.cancel();
                }
            }

        }
    };

    public Servidor() throws IOException {
        super("servidor");
    } //Se usa el constructor para servidor de Conexion

    public void startServer()//Método para iniciar el servidor
    {
        asignarCartas();
        try {
            System.out.println("Esperando..."); //Esperando conexión
            cs = ss.accept(); //Accept comienza el socket y espera una conexión desde un cliente
            System.out.println("Cliente en línea");

            ObjectInputStream inObjeto = new ObjectInputStream(cs.getInputStream());
            Juego lc = (Juego) inObjeto.readObject();

            System.out.println(cs.getInetAddress().toString());
            String ip = cs.getInetAddress().toString();
            String replaceAll = ip.replaceAll("/", "");

            String name = "";
            if (lc.conexion.equals("GL")) {
                MainServidor.juegoMain = lc;
                System.out.println("GuanteLatex");
                System.out.println(MainServidor.juegoMain.turno);
                for (Jugador jugadore : MainServidor.juegoMain.jugadores) {
                    System.out.println(jugadore.nickname);
                    System.out.println(jugadore.mazo2);
                    if (jugadore.ip.equals(replaceAll)) {
                        name = jugadore.getNickname();
                    }
                }
                System.out.println(MainServidor.juegoMain.turno);
                Runnable nuevoCliente = new HiloCliente(name,MainServidor.juegoMain);
                hilo = new Thread(nuevoCliente);
                hilo.start();
            } else if (lc.conexion.equals("l")) {
                System.out.println("Normal");
                MainServidor.juegoMain = lc;
                if (MainServidor.juegoMain.turno == MainServidor.juegoMain.jugadores.size() - 1) {
                    MainServidor.juegoMain.turno = 0;

                } else {
                    MainServidor.juegoMain.turno++;
                }

                System.out.println(MainServidor.juegoMain.turno);
                for (Jugador jugadore : MainServidor.juegoMain.jugadores) {
                    System.out.println(jugadore.nickname);
                    System.out.println(jugadore.mazo2);
                    if (jugadore.ip.equals(replaceAll)) {
                        name = jugadore.getNickname();
                    }
                }
                Runnable nuevoCliente = new HiloCliente(name,MainServidor.juegoMain);
                hilo = new Thread(nuevoCliente);
                hilo.start();
            } else {
                if (MainServidor.juegoMain.jugadores.isEmpty()) {
                    lc.jugadores.get(0).ip = replaceAll;
                    MainServidor.juegoMain = lc;
                    timer.schedule(task, 10, 1000);
                } else {
                    lc.jugadores.get(0).ip = replaceAll;
                    MainServidor.juegoMain.jugadores.add(lc.jugadores.get(0));
                }
                if (MainServidor.juegoMain.jugadores.size() == 6 && !MainServidor.juegoMain.conexion.equals("w")) {
                    repartirCartas();
                    MainServidor.juegoMain.conexion = "l";
                    Runnable nuevoCliente = new HiloCliente(name,MainServidor.juegoMain);
                    hilo = new Thread(nuevoCliente);
                    hilo.start();
                } else {
                    Runnable nuevoCliente = new HiloCliente(name,MainServidor.juegoMain);
                    hilo = new Thread(nuevoCliente);
                    hilo.start();
                }
            }

            System.out.println("Fin de la conexión");
            ss.close();//Se finaliza la conexión con el cliente
            cs.close();
            inObjeto.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void asignarCartas() {
        crearCartas();
        Random o = new Random();
        int aux;
        int resta = 67;

        while (lista != null && resta > 0) {
            Carta a;
            aux = o.nextInt(resta);
            a = lista.get(aux);
            listaRandom.add(a);

            lista.remove(a);
            resta--;
        }
        MainServidor.juegoMain.mazo = listaRandom;

    }

    public void repartirCartas() {
        for (Jugador jugadore : MainServidor.juegoMain.jugadores) {
            for (int i = 0; i < 3; i++) {
                jugadore.mazo1.add(MainServidor.juegoMain.mazo.get(i));
                MainServidor.juegoMain.mazo.remove(MainServidor.juegoMain.mazo.get(i));
            }
        }
    }

    public void crearCartas() {
        for (int i = 0; i < 68; i++) {//organos
            if (i < 21) {
                if (i < 5) {
                    lista.add(new Carta(i, "Organos", 1, 195, 130));//corazon
                }
                if (i >= 5 && i < 10) {
                    lista.add(new Carta(i, "Organos", 2, 195, 130));//estomago
                }
                if (i >= 10 && i < 15) {
                    lista.add(new Carta(i, "Organos", 3, 195, 130));//cerebor
                }
                if (i >= 15 && i < 20) {
                    lista.add(new Carta(i, "Organos", 4, 195, 130));//hueso
                }
                if (i >= 20 && i < 21) {
                    lista.add(new Carta(i, "Organos", 5, 195, 130));//comodin
                }
            }
            if (i >= 21 && i < 38)//virus
            {
                if (i < 25) {
                    lista.add(new Carta(i, "Virus", 1, 195, 130));//ROJIVIRUS
                }
                if (i >= 25 && i < 29) {
                    lista.add(new Carta(i, "Virus", 2, 195, 130));//VERDON EL VIRUS
                }
                if (i >= 29 && i < 33) {
                    lista.add(new Carta(i, "Virus", 3, 195, 130));//VIRUBLU
                }
                if (i >= 33 && i < 37) {
                    lista.add(new Carta(i, "Virus", 4, 195, 130));//YELLVIRUS
                }
                if (i >= 37 && i < 38) {
                    lista.add(new Carta(i, "Virus", 5, 195, 130));//COVID-19
                }
            }
            if (i >= 38 && i < 58)//medicinas
            {
                if (i < 42) {
                    lista.add(new Carta(i, "Medicinas", 1, 195, 130));//BOTIQUIN
                }
                if (i >= 42 && i < 46) {
                    lista.add(new Carta(i, "Medicinas", 2, 195, 130));//VACUNA
                }
                if (i >= 46 && i < 50) {
                    lista.add(new Carta(i, "Medicinas", 3, 195, 130));//ANTISUERO
                }
                if (i >= 50 && i < 54) {
                    lista.add(new Carta(i, "Medicinas", 4, 195, 130));//CURITAS
                }
                if (i >= 54 && i < 58) {
                    lista.add(new Carta(i, "Medicinas", 5, 195, 130));//HOSPITAL
                }
            }
            if (i >= 58 && i < 69)//tratamientos
            {
                if (i < 60) {
                    lista.add(new Carta(i, "Tratamientos", 1, 195, 130));//Transplante
                }
                if (i >= 60 && i < 63) {
                    lista.add(new Carta(i, "Tratamientos", 2, 195, 130));//Ladrón de órganos
                }
                if (i >= 63 && i < 66) {
                    lista.add(new Carta(i, "Tratamientos", 3, 195, 130));//Contagio
                }
                if (i >= 66 && i < 67) {
                lista.add(new Carta(i, "Tratamientos", 4, 195, 130));//Guante de látex
                }
                if (i >= 67 && i < 68) {
                    lista.add(new Carta(i, "Tratamientos", 5, 195, 130));//Error médico
                }
            }
        }
    }
}
