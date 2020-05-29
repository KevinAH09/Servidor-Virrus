/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viruss.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import javax.print.DocFlavor;

public class Servidor extends Conexion 
{
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
//                System.out.println(cs.getInetAddress().toString());
                String ip = cs.getInetAddress().toString();
                String replaceAll = ip.replaceAll("/", "");
                if(MainServidor.juegoMain.jugadores.isEmpty()){
                    MainServidor.juegoMain = lc;
                    MainServidor.juegoMain.jugadores.get(0).setIp(replaceAll);
                }else{
                    lc.jugadores.get(0).ip=replaceAll;
                    MainServidor.juegoMain.jugadores.add(lc.jugadores.get(0));                    
                }
//                for(int i=0;i<lc.mazo.size();i++){
//                    System.out.println(lc.mazo.get(i).color);
//                }
                
                System.out.println("Fin de la conexión");
                ss.close();//Se finaliza la conexión con el cliente
                cs.close();
                inObjeto.close();
                for (Jugador object : MainServidor.juegoMain.jugadores) {
                    Conexion.HOST = object.ip;
                    System.out.println("Iniciando cliente\n");
                    Cliente cli = new Cliente(); //Se crea el cliente
                    cli.startClient();
                }
                
               
                
            
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
