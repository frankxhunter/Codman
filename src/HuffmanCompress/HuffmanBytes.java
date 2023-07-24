package HuffmanCompress;

import cu.edu.cujae.ceis.tree.binary.BinaryTree;
import cu.edu.cujae.ceis.tree.binary.BinaryTreeNode;
import cu.edu.cujae.ceis.tree.iterators.binary.PosOrderIterator;
import ourTools.Convert;
import ourTools.Validation;

import java.io.*;
import java.util.*;


public class HuffmanBytes{

    public static void printTree(BinaryTree tree){
        System.out.println("Reanding nodes to the tree");
        PosOrderIterator<String>  it = tree.posOrderIterator();
        while(it.hasNext())
            System.out.println(it.next());
        System.out.println();
    }


    public static HashMap<Byte, Letrax> contarFrecuenciaCaracteres(byte[] input) {

        HashMap<Byte, Letrax> frecuencias = new HashMap<>();

        for (byte caracter : input)
            if (frecuencias.containsKey(caracter))
                frecuencias.get(caracter).aumentaFrecuencia();
            else
                frecuencias.put(caracter, new Letrax(caracter,1));


        return frecuencias;
    }

    public static void mergeSort(ArrayList<Letrax> letras, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(letras, left, middle);
            mergeSort(letras, middle + 1, right);
            merge(letras, left, middle, right);
        }
    }

    private static void merge(ArrayList<Letrax> letras, int left, int middle, int right) {
        ArrayList<Letrax> temp = new ArrayList<>(letras.subList(left, right + 1));

        int i = 0;
        int j = middle - left + 1;
        int k = left;

        while (i <= middle - left && j <= right - left) {
            if (temp.get(i).compareTo(temp.get(j)) <= 0) {
                letras.set(k, temp.get(i));
                i++;
            } else {
                letras.set(k, temp.get(j));
                j++;
            }
            k++;
        }

        while (i <= middle - left) {
            letras.set(k, temp.get(i));
            k++;
            i++;
        }
    }


    //Metodos para crear arbol
    public static BinaryTree<Byte> getTree(ArrayList<Letrax> letras, Hashtable<Byte,boolean[]> abecedario) {
        BinaryTreeNode<Byte> root = new BinaryTreeNode<>(null);
        BinaryTree<Byte> tree = new BinaryTree<>(root);
        ArrayList<Conjuntox> conjuntos = convert_to_Set(letras);
        joinSets(conjuntos);
        BinaryTreeNode<Byte> left = new BinaryTreeNode<>(null);
        BinaryTreeNode<Byte> right = new BinaryTreeNode<>(null);
        root.setLeft(left);
        root.setRight(right);
        ArrayList<Boolean> list = new ArrayList<>();
        list.add(false);
        ArrayList<Boolean> list2 = new ArrayList<>();
        list2.add(true);
        createTree(left, abecedario, list, conjuntos.get(0));
        createTree(right, abecedario, list2, conjuntos.get(1));
        return tree;
    }

    private static void createTree(BinaryTreeNode<Byte> node,Hashtable<Byte,boolean[]> abecedario,ArrayList<Boolean> codigo, Conjuntox conj) {
        if (conj.letra == null) {
            BinaryTreeNode<Byte> left = new BinaryTreeNode<>(null);
            BinaryTreeNode<Byte> right = new BinaryTreeNode<>(null);
            node.setLeft(left);
            node.setRight(right);
            ArrayList<Boolean> list =(ArrayList<Boolean>) codigo.clone();
            list.add(false);
            ArrayList<Boolean> list2 =(ArrayList<Boolean>) codigo.clone();
            list2.add(true);
            createTree(left, abecedario, list, conj.conj1);
            createTree(right, abecedario,list2, conj.conj2);
        } else {
            Byte letra=conj.letra.getLetrax();
            node.setInfo(letra);
            abecedario.put(letra, Convert.arrayListToByteArray(codigo));
        }
    }
   //Convierte a conjutos
    private static ArrayList<Conjuntox> convert_to_Set(ArrayList<Letrax> letras) {
        ArrayList<Conjuntox> salida = new ArrayList<Conjuntox>();
        for (Letrax x : letras)
            salida.add(new Conjuntox(x));

        return salida;
    }
    //Creo q une los conjuntos
    private static void joinSets(ArrayList<Conjuntox> conj) {
        while (conj.size() > 2) {
            Conjuntox c = new Conjuntox(conj.remove(0), conj.remove(0));
            ListIterator<Conjuntox> it = conj.listIterator(conj.size());
            boolean encontrado = false;
            while (it.hasPrevious() && !encontrado)
                if (it.previous().compareTo(c) != 1)
                    encontrado = true;
            if(conj.size()>0)
            it.next();
            it.add(c);
        }
    }
    //Metodo para codificar
    public static byte[] encodeWord(byte[] word,Hashtable<Byte,boolean[]> dictionary){
        LinkedList<boolean[]> list= new LinkedList<>();
        int size=0;
        for(int i=0; i<word.length;i++) {
            boolean[] aux= dictionary.get(word[i]);
            size+=aux.length;
            list.add(aux);
        }
        boolean[] out = new boolean[size];
        Iterator<boolean[]> it = list.iterator();
        int pos=0;
        while(it.hasNext()) {
            boolean[] array= it.next();
            System.arraycopy(array, 0, out, pos, array.length);
            pos+=array.length;
        }
        return Convert.bitsToBytes(out);
    }
    //Metodo para decodificar
    public static byte[] decode(BinaryTree<Byte> tree,boolean[] codigo) {
        //Creo el arrayList donde voy a guardar de forma temporal todos los bytes
        LinkedList<Byte> list= new LinkedList<>();
        int i = 0, tamano = codigo.length;

        BinaryTreeNode<Byte> aux;
        BinaryTreeNode<Byte> aux_root = (BinaryTreeNode<Byte>) tree.getRoot();
        while (i < tamano) {
            aux = aux_root;
            if (!codigo[i]) {
                if (tree.nodeIsLeaf(aux.getLeft())) {
                    list.add(aux.getLeft().getInfo());
                    aux_root = (BinaryTreeNode<Byte>) tree.getRoot();
                } else {
                    aux_root = aux.getLeft();
                }
            } else {
                if (tree.nodeIsLeaf(aux.getRight())) {
                   list.add(aux.getRight().getInfo());
                    aux_root = (BinaryTreeNode<Byte>) tree.getRoot();
                } else {
                    aux_root = aux.getRight();
                }
            }
            i++;
        }
        //if (aux_root == null)
          //  result = result + ('-');
        byte[] arrayByte= new byte[list.size()];
        int j=0;
        for(Byte b: list) {
            arrayByte[j++]=b.byteValue();
        }
        return arrayByte;
    }

    public static File HuffmanBytesCompress(File file, String address) throws IOException {

        //Lanzar exception si el fichero no existe
        Validation.existFile(file);

        //Crear el randomAccessFile para leer el fichero
        RandomAccessFile raf = new RandomAccessFile(file, "rw");

        //Nombre del archivo
        String name= file.getName();

        //Esta sera la direccion del fichero de salida con su respectivo nombre
        String newName= address+ Convert.removeExtension(name)+".cod";

        //Creo el fichero nuevo donde va a estar la nueva informacion, se crea aqui arriba para si hay problema con la direccion
        RandomAccessFile rafout= new RandomAccessFile(newName,"rw");

        //Leer todos los bytes del archivo y guardarlos en array
        long fileSize= raf.length();
        byte[] array= new byte [(int)fileSize];
        raf.readFully(array);

        if(array.length==0){
            throw new IOException("El fichero que intenta comprimir esta vacio");
        }
        //Devuelvo un hashMap donde a cada Byte le corresponde la clase Letrax que tiene la frecuencia con la q se
        //repitio el byte en el archivo
        HashMap<Byte, Letrax> map= HuffmanBytes.contarFrecuenciaCaracteres(array);

        //Guardo eso en un arrayList
        ArrayList<Letrax> listLetras= new ArrayList<>(map.values());

        //Ordeno el arrayList de menor repeticion a mayor repeticion
        HuffmanBytes.mergeSort(listLetras, 0,listLetras.size()-1);

        //Creo el arbol que se utiliza para decodifica y la hashTable q se usa para codificar
        Hashtable<Byte, boolean[]> alpha= new Hashtable<>();
        BinaryTree<Byte> tree= getTree(listLetras, alpha);

        //Codifica el array de informacion a un array de booleanos y luego lo convierte a un array de byte
        byte[] code= encodeWord(array, alpha);



        //Algoritmo de Serializacion del arbol de huffman en bits
        byte [] bytesTree= Convert.save_Huffman_Tree_As_Bits(tree);

        //Serializo el nombre del fichero
        byte[] arrayName= Convert.toBytes(name);

        //Escribo en el fichero toda la informacion, la estructura del fichero es la siguiente
        // int||nombre del fichero|| int || arrayList del arbol || int || informacion codificada
        rafout.writeInt(arrayName.length);
        rafout.write(arrayName);
        rafout.writeInt(bytesTree.length);
        rafout.write(bytesTree);
        rafout.writeInt(code.length);
        rafout.write(code);

        //Cierro los RandomAccesFile
        rafout.close();
        raf.close();

        //Creo el fichero con el nombre q voy a retornar
        File out= new File(newName);

        return out;
    }

    public static LinkedList<byte[]> HuffmanBytesCompress(byte[] info) throws IOException {

        if(info.length==0){
            throw new IOException("El fichero que intenta comprimir esta vacio");
        }
        //Devuelvo un hashMap donde a cada Byte le corresponde la clase Letrax que tiene la frecuencia con la q se
        //repitio el byte en el archivo
        HashMap<Byte, Letrax> map= HuffmanBytes.contarFrecuenciaCaracteres(info);

        //Guardo eso en un arrayList
        ArrayList<Letrax> listLetras= new ArrayList<>(map.values());

        //Ordeno el arrayList de menor repeticion a mayor repeticion
        HuffmanBytes.mergeSort(listLetras, 0,listLetras.size()-1);

        //Creo el arbol que se utiliza para decodifica y la hashTable q se usa para codificar
        Hashtable<Byte, boolean[]> alpha= new Hashtable<>();
        BinaryTree<Byte> tree= getTree(listLetras, alpha);

        //Codifica el array de informacion a un array de booleanos y luego lo convierte a un array de byte
        byte[] code= encodeWord(info, alpha);

        //Algoritmo de Serializacion del arbol de huffman en bits
        byte [] bytesTree= Convert.save_Huffman_Tree_As_Bits(tree);

        LinkedList<byte[]> salida = new LinkedList<byte[]>();
        salida.add(bytesTree);
        salida.add(code);
        return salida;
    }

    public static File HuffmanBytesDescompress(File file, String address) throws IOException, ClassNotFoundException {

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

        //Obtengo el arbol a partir de la serializacion del arbol
        arrayByte= new byte[raf.readInt()];
        raf.read(arrayByte);
        BinaryTree tree= Convert.leerArbolHuffmanDesdeBits(arrayByte);

        //Obtengo toda la informacion comprimida en forma de array de byte, la convierto a array de boolean
        // para descomprimirla
        arrayByte= new byte[raf.readInt()];
        raf.read(arrayByte);
        boolean[] arrayboolean= Convert.bytesToBits(arrayByte);

        //Descomprimo la informacion y la guardo en el array de byte
        byte[] arraybyteInformation =decode(tree,arrayboolean);

        //escribo la informacion

        rafoux.write(arraybyteInformation);

        raf.close();
        rafoux.close();

        return new File(newname);

    }
    public static byte[] HuffmanBytesDescompress(LinkedList<byte[]> infoCode) throws IOException, ClassNotFoundException {

        //Obtengo el arbol a partir de la serializacion del arbol
        BinaryTree tree= Convert.leerArbolHuffmanDesdeBits(infoCode.removeFirst());

        //Convierto la info a array de boolean
        boolean[] arrayboolean= Convert.bytesToBits(infoCode.removeFirst());

        //Descomprimo la informacion y la guardo en el array de byte
        byte[] arraybyteInformation =decode(tree,arrayboolean);

        //Devuelvo la informacion
        return arraybyteInformation;

    }



    public static void main(String[] args) {
        //Prueba Real del metodo
        try {
            File entrada=HuffmanBytesCompress(new File("CP3.doc"), "");
            File salida= HuffmanBytesDescompress(new File("01x00 Unaired Pilot (DUAL).cod"), "C:\\Frank\\Proyectos\\HuffmanEncode\\");



        }
        catch (StreamCorruptedException e) {
            System.out.println("Archivo corrupto o dañado");
        }
        catch (IOException  e) {
            System.out.println(e.getMessage());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class Letrax implements Comparable<Letrax> {
    private byte letra;
    private int frecuencia;

    public Letrax(byte letra, int frecuencia) {
        this.setLetrax(letra);
        this.setFrecuencia(frecuencia);
    }
    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public byte getLetrax() {
        return letra;
    }

    public void setLetrax(byte letra) {
        this.letra = letra;
    }
    public void aumentaFrecuencia() {
        frecuencia++;
    }

    @Override
    public int compareTo(Letrax o) {
        return Integer.compare(this.frecuencia, o.getFrecuencia());
    }
}


class Conjuntox implements Comparable<Conjuntox> {
    Conjuntox conj1;
    Conjuntox conj2;
    Letrax letra;
    int suma;

    public Conjuntox(Conjuntox conj1, Conjuntox conj2) {
        this.conj1 = conj1;
        this.conj2 = conj2;
        letra = null;
        suma = conj1.suma + conj2.suma;
    }

    public Conjuntox(Letrax l) {
        this.letra = l;
        suma = letra.getFrecuencia();
        conj1 = null;
        conj2 = null;
    }

    public int compareTo(Conjuntox otraConjuntox) {
        return Integer.compare(this.suma, otraConjuntox.suma);
    }

}

