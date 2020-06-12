/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viruss.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import viruss.model.Carta;
import viruss.model.Cliente;
import viruss.model.Juego;
import viruss.model.Jugador;
import viruss.model.MainServidor;

/**
 * FXML Controller class
 *
 * @author colo7
 */
public class InicioController extends Controller implements Initializable {

    @FXML
    private  HBox hboxMasoJug;
    @FXML
    private HBox hboxMesaJug1;
    @FXML
    private HBox hboxMesaJug2;
    @FXML
    private HBox hboxMesaJug3;
    @FXML
    private HBox hboxMesaJug4;
    @FXML
    private HBox hboxMesaJug5;
    @FXML
    private HBox hboxMesaJug6;
    @FXML
    private HBox hboxmaso;
    
    public static HBox masoStatico;
    public static  HBox miMesa;
    //public static HBox mesaJugador;
            
    private List <Carta> lista = new ArrayList();   
    static private List <Carta> listaRandom =  new ArrayList();
    Carta p = new Carta();
    @FXML
    private ImageView imgMaso;
    @FXML
    private ScrollPane scrollpane;
    public static List <Carta> listaMasoJugador =  new ArrayList();
    public static List <Carta> listaCementerio =  new ArrayList();
    public static Carta cartaSelec=null;
    
    @FXML
    private ImageView cemento;
    public static  List <Carta> ListaMesaJugador = new ArrayList();  
    @FXML
    private HBox HboxBasura;
    public static HBox basura;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        masoStatico = hboxMasoJug;
        miMesa = hboxMesaJug2;
        basura = HboxBasura;
        
        Random o = new Random();
        int aux;
        int resta=67;
        
        while(lista!=null&&resta>0)
        {
            Carta a;
            aux=o.nextInt(resta);
            a=lista.get(aux);
            listaRandom.add(a);
            
            lista.remove(a);
            resta--;
        }
        
            
//            Cliente cli;
//            try {
//                cli = new Cliente(); //Se crea el cliente
//                System.out.println("Iniciando cliente\n");
//                cli.startClient(); //Se inicia el cliente
//            } catch (IOException ex) {
//                Logger.getLogger(InicioController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            MainServidor.juegoMain.jugadores.get(0).mazo1 = listaMasoJugador;
//            MainServidor.juegoMain.jugadores.get(0).mazo2 = ListaMesaJugador;
//        
            int cont=0;
            while(cont !=18)
            {
                if (cont < 19) {
//                    if (cont < 3) {
//                        hboxMesaJug1.getChildren().add(MainServidor.juegoMain.mazo.get(MainServidor.juegoMain.mazo.size() - 1));
//                        MainServidor.juegoMain.mazo.remove(MainServidor.juegoMain.mazo.get(MainServidor.juegoMain.mazo.size() - 1));
//
//                    }
//                    if (cont >= 3 && cont < 6) {
//                        hboxMesaJug2.getChildren().add(MainServidor.juegoMain.mazo.get(MainServidor.juegoMain.mazo.size() - 1));
//                        MainServidor.juegoMain.mazo.remove(MainServidor.juegoMain.mazo.get(MainServidor.juegoMain.mazo.size() - 1));
//                    }
//                    if (cont >= 6 && cont < 9) {
//                        hboxMesaJug3.getChildren().add(MainServidor.juegoMain.mazo.get(MainServidor.juegoMain.mazo.size() - 1));
//                        MainServidor.juegoMain.mazo.remove(MainServidor.juegoMain.mazo.get(MainServidor.juegoMain.mazo.size() - 1));
//                    }
                    if (cont >= 9 && cont < 12) {
                        hboxMesaJug4.getChildren().add(MainServidor.juegoMain.mazo.get(MainServidor.juegoMain.mazo.size() - 1));
                        MainServidor.juegoMain.mazo.remove(MainServidor.juegoMain.mazo.get(MainServidor.juegoMain.mazo.size() - 1));
                    }
                    if (cont >= 12 && cont < 15) {
                        p= MainServidor.juegoMain.mazo.get(MainServidor.juegoMain.mazo.size()-1);
                        p.setRotate(180);
                        hboxMesaJug5.getChildren().add(p);
                        MainServidor.juegoMain.mazo.remove(MainServidor.juegoMain.mazo.get(MainServidor.juegoMain.mazo.size() - 1));
                    }
                    if(cont>=15 && cont<18)
                    {
                        
                        p= MainServidor.juegoMain.mazo.get(MainServidor.juegoMain.mazo.size()-1);
                        p.setRotate(180);
                        hboxMesaJug6.getChildren().add(p);
                        MainServidor.juegoMain.mazo.remove(MainServidor.juegoMain.mazo.get(MainServidor.juegoMain.mazo.size()-1));
                    }
                    cont++;
                }
            }
        
    }   

    @Override
    public void initialize() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    @FXML
    private void actionMasoClick(MouseEvent event) {
        if(MainServidor.juegoMain.mazo.isEmpty()!=true)
        {
            hboxMasoJug.getChildren().add(MainServidor.juegoMain.mazo.get(MainServidor.juegoMain.mazo.size()-1));
            MainServidor.juegoMain.jugadores.get(0).mazo1.add(MainServidor.juegoMain.mazo.get(MainServidor.juegoMain.mazo.size() - 1));
            MainServidor.juegoMain.mazo.remove(MainServidor.juegoMain.mazo.get(MainServidor.juegoMain.mazo.size() - 1));
        }
        else
        {
            
            MainServidor.juegoMain.mazo.addAll(MainServidor.juegoMain.cementerio);
            MainServidor.juegoMain.cementerio.clear();
        }
    }

    @FXML
    private void actionHboxJug(MouseEvent event) {
        if(cartaSelec!=null){
            boolean band=true;
            for (Carta carta : MainServidor.juegoMain.jugadores.get(0).mazo2) {
                if (carta.color==cartaSelec.color) {
                    band=false;
                }
            }
            if(band)
            {
                MainServidor.juegoMain.jugadores.get(0).mazo2.add(InicioController.cartaSelec);
                hboxMesaJug2.getChildren().add(InicioController.cartaSelec);
                MainServidor.juegoMain.jugadores.get(0).mazo1.remove(InicioController.cartaSelec);
            }
           Carta.cont=0;
           cartaSelec=null;
        
    }
     
    }
    
}
