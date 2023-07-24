/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package LZ77_GraphCompress;

import HuffmanCompress.HuffmanBytes;
import ourTools.Convert;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Bender
 */
public class Test {

    public static void cantidadCoincidencias(ArrayList<Nodo> grafo){
        int x= 0;
        int pos=0;
        int numeroAristas=0;
        int longitudreducida=0;
        int nuevalong=0;
        for (Nodo s : grafo) {
            nuevalong+=s.getBloque().length;
            if(s.getEnlace().size()>0) {

                for (Enlace z : s.getEnlace()){
                    numeroAristas++;
                   longitudreducida+=z.getLongitud();
            }

            }
        pos++;
        }
        System.out.println("Longitud total: "+nuevalong);
        System.out.println("numero de aristas: "+numeroAristas);
        System.out.println("Coincidencias long: "+longitudreducida);
    }
    /*public static void main(String[] args) {
//        File a = new File("adelanto.docx");
//        File a = new File("prueba.txt");
          File a = new File("CP3.doc");
//         TODO code application logic here
        ArrayList<Nodo> grafo =  Compresor_FF.separar_en_bloques_convertir_en_grafos(a, 500, 100, 10);
        try (RandomAccessFile raf = new RandomAccessFile(a, "rw")){
            int pesoOriginal = (int)raf.length();

            Grafo graph = new Grafo(grafo,pesoOriginal );
            System.out.println("Lo que pesa el grafo: "+ Convert.toBytes(graph).length);
            LinkedList<byte[]> listaGraph= Convert.SerializarGrafo(graph);
            System.out.println("Peso del grafo serializado:"+(listaGraph.get(0).length+listaGraph.get(1).length));

            Grafo grafo1= Convert.obtainGraphToSerialization(listaGraph, graph);
            System.out.println("El grafo deserilizado: "+Convert.toBytes(grafo1).length);

            System.out.println("Peso de la info original convertida del grafo: "+Compresor_FF.graphToInfo(graph).length);

            System.out.println("\n\n\nCambio de tema");
            LinkedList<byte[]> lista = HuffmanBytes.HuffmanBytesCompress(listaGraph.get(1));
            System.out.println("Con huffman despues de LZ77: "+(lista.get(0).length+lista.get(1).length));
            System.out.println("Lo que pesa el archivo original: "+graph.getTamañoInfoOriginal());
            ArrayList list = new ArrayList<Nodo>();
            list.add(new Nodo(new byte[0]));
            System.out.println("pesa: "+Convert.toBytes(list).length);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
//    }

    }


