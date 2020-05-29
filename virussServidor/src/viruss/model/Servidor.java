/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viruss.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import static java.lang.Boolean.FALSE;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;

public class Servidor extends Conexion 
{
    boolean cerrojo = true;
    Thread hilo;
    Timer timer = new Timer();
    int tic=0;
    TimerTask task = new TimerTask() {
       

        @Override
        public void run()
        {

            tic++;
            System.out.println(tic);
            if(tic == 60 && MainServidor.juegoMain.jugadores.size() < 3  ){
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
            }else if(MainServidor.juegoMain.jugadores.size() == 6  && tic <= 60){
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
            }else if(MainServidor.juegoMain.jugadores.size() >= 3  && tic == 60 ){
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
    };
    public Servidor() throws IOException{
        super("servidor");
    
    } //Se usa el constructor para servidor de Conexion
    
    public void startServer()//Método para iniciar el servidor
    {
        try
        {
                System.out.println("Esperando..."); //Esperando conexión
                cs = ss.accept(); //Accept comienza el socket y espera una conexión desde un cliente
                System.out.println("Cliente en línea");
                
                ObjectInputStream inObjeto = new ObjectInputStream( cs.getInputStream());
                Juego lc =(Juego) inObjeto.readObject();          
                System.out.println(cs.getInetAddress().toString());
                String ip = cs.getInetAddress().toString();
                String replaceAll = ip.replaceAll("/", "");
                
                if(MainServidor.juegoMain.jugadores.isEmpty()){
                    
                    MainServidor.juegoMain = lc;
                    MainServidor.juegoMain.jugadores.get(0).setIp(replaceAll);
                    MainServidor.juegoMain.jugadores.get(1).setIp(replaceAll);
                    MainServidor.juegoMain.jugadores.get(2).setIp(replaceAll);
                    timer.schedule(task, 10, 1000);
                    
                }else{
                    lc.jugadores.get(0).ip=replaceAll;
                    lc.jugadores.get(1).ip=replaceAll;
                    lc.jugadores.get(2).ip=replaceAll;
                    MainServidor.juegoMain.jugadores.add(lc.jugadores.get(0));
                    MainServidor.juegoMain.jugadores.add(lc.jugadores.get(1)); 
                    MainServidor.juegoMain.jugadores.add(lc.jugadores.get(2)); 
                }
//                for(int i=0;i<lc.mazo.size();i++){
//                    System.out.println(lc.mazo.get(i).color);
//                }
                
                
//                 Empezamos dentro de 10ms y luego lanzamos la tarea cada 1000ms
                if(MainServidor.juegoMain.jugadores.size() == 6 && !MainServidor.juegoMain.conexion.equals("w")){
                    Runnable nuevoCliente = new HiloCliente();
                    hilo = new Thread(nuevoCliente);
                    hilo.start(); 
                }
                    
                System.out.println("Fin de la conexión");
                ss.close();//Se finaliza la conexión con el cliente
                cs.close();
                inObjeto.close();
               
                        
               
                
            
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
