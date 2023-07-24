/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LZ77_GraphCompress;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Bender
 */
public class Nodo implements Serializable {

    private byte[] bloque;
    private LinkedList<Enlace> enlace;

    public Nodo(byte[] bloque) {
        this.bloque = bloque;
        enlace = new LinkedList<>();

    }
    public int tamañoReducido(){
        int tam=0;
        for(Enlace e : enlace)
            tam+=e.getLongitud();
        return tam;
    }

    public byte[] getBloque() {
        return bloque;
    }

    public void setBloque(byte[] bloque) {
        this.bloque = bloque;
    }

    public LinkedList<Enlace> getEnlace() {
        return enlace;
    }

    public void addEnlace(Enlace enlace) {
        this.enlace.add(enlace);
    }

}
