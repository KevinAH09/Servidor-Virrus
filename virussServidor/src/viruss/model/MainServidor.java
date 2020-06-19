/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viruss.model;
import java.io.IOException;
import static java.lang.Boolean.TRUE;
import java.util.Scanner;
import viruss.model.Conexion;
import viruss.util.Mensaje;


//Clase principal que hará uso del servidor
public class MainServidor
{
   public static Boolean band = TRUE;
   public static Juego juegoMain;
   public static void main(String[] args) throws IOException
    {
        
        juegoMain = new Juego();
        while(band){
             
            Servidor serv = new Servidor(); //Se crea el servidor

           
            serv.startServer(); //Se inicia el servidor
        }
    }
}
