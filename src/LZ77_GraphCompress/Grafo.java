package LZ77_GraphCompress;

import java.io.Serializable;
import java.util.ArrayList;

public class Grafo implements Serializable {
    private ArrayList<Nodo> listaVertices;
    private int tamañoInfoOriginal;

    public Grafo(ArrayList<Nodo> listaVertices, int tamañoInfoOriginal) {
        this.listaVertices = listaVertices;
        this.tamañoInfoOriginal = tamañoInfoOriginal;
    }
    public Grafo(int cantVertices){
        this.listaVertices = new ArrayList<>(cantVertices);
        tamañoInfoOriginal = 0;
    }
    public void addNodo(Nodo nodo){
        this.listaVertices.add(nodo);
        tamañoInfoOriginal+=nodo.getBloque().length + nodo.tamañoReducido();
    }

    public ArrayList<Nodo> getListaVertices() {
        return listaVertices;
    }

    public void setListaVertices(ArrayList<Nodo> listaVertices) {
        this.listaVertices = listaVertices;
    }

    public int getTamañoInfoOriginal() {
        return tamañoInfoOriginal;
    }

    public void setTamañoInfoOriginal(int tamañoInfoOriginal) {
        this.tamañoInfoOriginal = tamañoInfoOriginal;
    }
}
