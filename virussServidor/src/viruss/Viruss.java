/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viruss;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import viruss.model.MainServidor;
import viruss.util.FlowController;

/**
 *
 * @author Bosco
 */
public class Viruss extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        MainServidor.main(args);
       
    }
    
}
