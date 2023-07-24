
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LZ77_GraphCompress;

import java.io.Serializable;

/**
 *
 * @author Bender
 */
public class Enlace implements Serializable {

    private int proximo_nodo;
    private int posicion;
    private int longitud;

    public Enlace() {
    }

    public Enlace(int proximo_nodo, int posicion, int longitud) {
        this.proximo_nodo = proximo_nodo;
        this.posicion = posicion;
        this.longitud = longitud;
    }

    public int getLongitud() {
        return longitud;
    }

    public int getProximo_nodo() {
        return proximo_nodo;
    }

    public int getPosicion() {
        return posicion;
    }

}
