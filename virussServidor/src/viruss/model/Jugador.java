/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viruss.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cfugu
 */
public class Jugador implements Serializable{
    private static final long serialVersionUID = 6529685098267757691L;
    String nickname;
    String ip;
    public List<Carta> mazo1 = new ArrayList<>();;
    public List<Carta> mazo2 = new ArrayList<>();;

    public Jugador() {
    }

    
    public Jugador(String nick , String ip) {
        this.nickname = nick;
        this.ip = ip; 
        
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public List getMazo1() {
        return mazo1;
    }

    public void setMazo1(List mazo1) {
        this.mazo1 = mazo1;
    }

    public List getMazo2() {
        return mazo2;
    }

    public void setMazo2(List mazo2) {
        this.mazo2 = mazo2;
    }
    
}


