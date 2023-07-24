
package LZ77_GraphCompress;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * @author Bender
 */
public class Compresor_FF {

    public static Grafo separar_en_bloques_convertir_en_grafos(RandomAccessFile fil,int longFile, int tamano_de_bloque,
                                                                         int ventana_de_desplazamiento,
                                                                         int longitud_minima) throws IOException {
        ArrayList<Nodo> grafo = null;
            int tamano_arraylist = 0;
            int excedente = tamano_de_bloque;
            tamano_arraylist = longFile / tamano_de_bloque;
            if (longFile % tamano_de_bloque != 0) {
                tamano_arraylist++;
                excedente = longFile % tamano_de_bloque;
            }
//          Contador para el ultimo elemento
            int i= 0;
            grafo = new ArrayList<>(tamano_arraylist);
            boolean primero = true;
            int cont = 0;//para enviar a la funcion el indice del nodo

            byte[] bytes;
            int bandera = 0;
            while ((bandera != -1)) {
                if(i++<tamano_arraylist-1)
                bytes = new byte[tamano_de_bloque];
                else
                    bytes = new byte[excedente];
                bandera = fil.read(bytes);
                if (bandera != -1) {
                    if (primero) {
                        Nodo n = new Nodo(bytes);
                        grafo.add(n);
                        primero = false;
                        cont++;
                    } else {
                        Nodo x = new Nodo(bytes);
                        grafo.add(x);
                        boolean condicion = true;
                        while (x.getBloque().length > longitud_minima && condicion)
                            condicion = enlaza_la_coincidencia_mas_larga(cont, grafo, ventana_de_desplazamiento, longitud_minima);
                        cont++;
                    }
                }
            }
        return new Grafo(grafo, longFile);
    }

    /*
    funcion para inicializar el tamaño del arreglo, se debe analizar
    puesto que para archivos muy grandes dara un error de memoria
     */
    private static int tamano_de_array(File file, int tamano_de_bloque) {
        int tamano = 0;
        try (RandomAccessFile fil = new RandomAccessFile(file, "rw")) {
            int longFile = (int) fil.length();
            tamano = longFile / tamano_de_bloque;
            if (longFile % tamano_de_bloque != 0) {
                tamano++;

            }
            fil.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tamano;
    }


   public static boolean enlaza_la_coincidencia_mas_larga(int indice_del_nodo, ArrayList<Nodo> nodos,
                                                           int ventana_de_desplazamiento,
                                                           int longitud_minima) {
        //Variable para saber si se encontro una coincidencia q cumple los requisitos
        boolean founded = false;

        //Posicion a partir de la que se empieza a revisar
        int indexNode = indice_del_nodo - ventana_de_desplazamiento;
        indexNode = indexNode > -1 ? indexNode : 0;

        //Obtener el nodo con el que voy a realizar la comparacion
        Nodo node = nodos.get(indice_del_nodo);

        //Comenzar el iterador a partir del index node obtenido anteriormente
        ListIterator<Nodo> it = nodos.listIterator(indexNode);

        //Variables para guardar la mayor coincidencia encontrada
        int maxPosStart = 0;
        int distanceMax = 0;
        int maxNode = 0;

//        Iterar buscando la mayor coincidencia hasta encontrar el nodo en el que estoy trabajando
        for (Nodo currentNode = it.next(); currentNode != node; currentNode = it.next(), indexNode++) {

            int longitude = 0;
            int tamBloque = currentNode.getBloque().length;
            int i;
              /*Aqui se itera en el bloque realizando comparaciones siempre q sea posible encontrar una coincidencia mayor
                Para que haya una coincidencia mayor tiene q a ver disponibilidad de espacio superior al minimo requerido
                y susperior a la mayor longitud de coincidencia maxima encontrada hasta el momento*/
            for (i = 0; i + longitud_minima <= tamBloque && tamBloque - i > distanceMax; i++) {
                //Encuentra la mayor coincidencia
                longitude = Arrays.mismatch(node.getBloque(), 0, node.getBloque().length,
                        currentNode.getBloque(), i, tamBloque);

                //Si la coincidencia es -1 significa q coincide totalmente, este es el mejor de los casos
                if (longitude == -1)
                    longitude = node.getBloque().length;

                //Si cumple la condicion de coincidencia significa que al menos una coincidencia va a existir
                if (longitude > longitud_minima)
                    founded = true;

                //Si es mayor que la mayor coincidencia actual se sustituyen los datos
                if (longitude > distanceMax) {
                    distanceMax = longitude;
                    maxPosStart = i;
                    maxNode = indexNode;
                }
                //Se aumenta en la coincidencia
                i += longitude;
            }
        }
        //Ahora si se encontro coincidencia hay q crear la arista y eliminar los datos del vertice
        if (founded) {
            int longNewArray = node.getBloque().length - distanceMax;
            byte[] newArray = new byte[longNewArray];
            System.arraycopy(node.getBloque(), node.getBloque().length - longNewArray, newArray, 0, longNewArray);
            node.setBloque(newArray);
            node.addEnlace(new Enlace(maxNode, maxPosStart, distanceMax));
        }
        return founded;
    }
   
    public static byte[] graphToInfo(Grafo grafo) {
        int pos = 0;//para controlar desde que indice se debe copiar
        byte[] original_file = new byte[grafo.getTamañoInfoOriginal()];//creo el arreglo que se va aretornar
        Iterator<Nodo> iter = grafo.getListaVertices().iterator();//iterador sobre los vertices
        while (iter.hasNext()) {//recorro mientras haya vertices
            Nodo n = iter.next();
            Iterator<Enlace> iter_enlaces = n.getEnlace().iterator();//iterador sobre los enlaces
            while (iter_enlaces.hasNext()) {//itero mientras haya enlaces
                Enlace e = iter_enlaces.next();
                System.arraycopy(grafo.getListaVertices().get(e.getProximo_nodo()).getBloque(), e.getPosicion(),
                        original_file, pos, e.getLongitud());
                pos+=e.getLongitud();
            }//uso esta funcion para copiar de un arreglo hacia otro indicando el arreglo origen, la posicion
            //desde donde voy a copiar,el archivo destino, la posicion desde donde voy a copiar, y la longitud de la copia
            System.arraycopy(n.getBloque(), 0, original_file, pos, n.getBloque().length);
            pos+=n.getBloque().length;
        }//al terminar de copiar la info de todos sus enlaces copio la de sus aristas

        return original_file;
    }

}
