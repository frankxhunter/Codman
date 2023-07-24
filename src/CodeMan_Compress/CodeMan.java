package CodeMan_Compress;

import HuffmanCompress.HuffmanBytes;
import LZ77_GraphCompress.Compresor_FF;
import LZ77_GraphCompress.Grafo;
import cu.edu.cujae.ceis.tree.binary.BinaryTree;
import ourTools.Convert;
import ourTools.Validation;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

public class CodeMan {
    public static File compress(File file, String address,String nameFinal, int tamBloque, int ventanaDesplazamiento, int longMinima) throws IOException {

        //Lanzar exception si el fichero no existe
        Validation.existFile(file);

        //Crear el randomAccessFile para leer el fichero
        RandomAccessFile raf = new RandomAccessFile(file, "rw");

        //Nombre del archivo
        String name= file.getName();
        
        address=address.length()==0? address:address+"\\" ;

        //Esta sera la direccion del fichero de salida con su respectivo nombre
        String newName= address+ nameFinal+".cod";

        //Creo el fichero nuevo donde va a estar la nueva informacion, se crea aqui arriba para si hay problema con la direccion
        RandomAccessFile rafout= new RandomAccessFile(newName,"rw");

        //Leer todos los bytes del archivo y guardarlos en array
        int fileSize= (int)raf.length();

        if(fileSize==0){
            throw new IOException("El fichero que intenta comprimir esta vacio");
        }

        //Obtengo el grafo utlizando el algoritmo LZ77 por grafos
        Grafo graph = Compresor_FF.separar_en_bloques_convertir_en_grafos(raf, fileSize, tamBloque,
                ventanaDesplazamiento, longMinima);

        //Serializo el grafo diviendo en info de los nodo (0) y informacion reducida (1)
        LinkedList<byte[]> serializarGrafo= Convert.SerializarGrafo(graph);

        //Aplico Huffman en el array de informacion reducida
        LinkedList<byte[]> huffmanCompress= HuffmanBytes.HuffmanBytesCompress(serializarGrafo.remove(1));

        //Serializo el nombre del fichero
        byte[] arrayName= Convert.toBytes(name);

        //Escribo en el fichero toda la informacion, la estructura del fichero es la siguiente
        // int||nombre del fichero|| bytes del arbol de hufman || int || info codificada|| int || bytes del grafo || int
        rafout.writeInt(arrayName.length);
        rafout.write(arrayName);
        //Info de huffman con el codigo
        rafout.writeInt(huffmanCompress.getFirst().length);
        rafout.write(huffmanCompress.removeFirst());
        rafout.writeInt(huffmanCompress.getFirst().length);
        rafout.write(huffmanCompress.removeFirst());

        //Info del grafo
        rafout.writeInt(serializarGrafo.getFirst().length);
        rafout.write(serializarGrafo.removeFirst());

        //Cierro los RandomAccesFile
        rafout.close();
        raf.close();

        //Creo el fichero con el nombre q voy a retornar
        File out= new File(newName);

        return out;
    }

    public static File descompress(File file, String address) throws IOException, ClassNotFoundException {
// int||nombre del fichero|| int || bytes del grafo || int || bytes del arbol de hufman || int || info codificada
        //Lanzar exception si el fichero no existe
        Validation.existFile(file);

        //Abro el fichero que voy a descomprimir
        RandomAccessFile raf= new RandomAccessFile(file, "rw");

        //Obtengo el nombre de fichero
        byte[] arrayByte= new byte[raf.readInt()];
        raf.read(arrayByte);
        String name= (String) Convert.toObject(arrayByte);

        //La nueva direccion donde se va a guardar el fichero
        String newname = address+name;

        //Creo el fichero donde se va a guardar la informacion, se crea aqui en caso de que haya problema con la direccion
        RandomAccessFile rafoux= new RandomAccessFile(newname, "rw");

        //Obtengo el arbol de Huffman
        LinkedList<byte []> huffmanInfo= new LinkedList<>();
        huffmanInfo.add(new byte[raf.readInt()]);
        raf.read(huffmanInfo.get(0));

        //Obtengo el arbol de Huffman
        huffmanInfo.add(new byte[raf.readInt()]);
        raf.read(huffmanInfo.get(1));

        //Esta es la info que acompaña al grafo
        LinkedList<byte[]> listaGrafo = new LinkedList<>();
        listaGrafo.add(HuffmanBytes.HuffmanBytesDescompress(huffmanInfo));

        listaGrafo.addFirst( new byte[raf.readInt()]);
        raf.read(listaGrafo.getFirst());

        //escribo la informacion

        rafoux.write(Compresor_FF.graphToInfo(Convert.obtainGraphToSerialization(listaGrafo)));

        raf.close();
        rafoux.close();

        return new File(newname);

    }
}
