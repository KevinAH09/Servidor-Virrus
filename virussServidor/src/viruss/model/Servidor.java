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
import javafx.concurrent.Task;

public class Servidor extends Conexion 
{
    
    Timer timer = new Timer();
    int tic=0;
    TimerTask task = new TimerTask() {
       

        @Override
        public void run()
        {

            tic++;
            System.out.println(tic);
            if(tic>=60){
                MainServidor.band = FALSE;
                System.out.println("ServidorCerrado");
                
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
                    timer.schedule(task, 10, 1000);
                    MainServidor.juegoMain = lc;
                    MainServidor.juegoMain.jugadores.get(0).setIp(replaceAll);
                }else{
                    lc.jugadores.get(0).ip=replaceAll;
                    MainServidor.juegoMain.jugadores.add(lc.jugadores.get(0));                    
                }
                for(int i=0;i<lc.mazo.size();i++){
                    System.out.println(lc.mazo.get(i).color);
                }
                
                
//                 Empezamos dentro de 10ms y luego lanzamos la tarea cada 1000ms
                
                Runnable nuevoCliente = new HiloCliente();
                Thread hilo = new Thread(nuevoCliente);
                hilo.start();
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
