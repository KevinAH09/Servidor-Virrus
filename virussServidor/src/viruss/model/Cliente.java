/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viruss.model;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Cliente extends Conexion
{
    public Cliente() throws IOException{super("cliente");} //Se usa el constructor para cliente de Conexion

    public void startClient() //Método para iniciar el cliente
    {
        try
        {                  
            
            ObjectOutputStream carta1 = new ObjectOutputStream( cs.getOutputStream());
            System.out.println("viruss.model.Cliente.startClient()");
            // Se envía el objeto
            carta1.writeObject(MainServidor.juegoMain);           
            carta1.close();
            cs.close();//Fin de la conexión
            System.out.println("viruss.model.Cliente.startClient()22");
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}
