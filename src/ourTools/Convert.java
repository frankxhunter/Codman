package ourTools;

import LZ77_GraphCompress.Enlace;
import LZ77_GraphCompress.Grafo;
import LZ77_GraphCompress.Nodo;
import cu.edu.cujae.ceis.tree.binary.BinaryTree;
import cu.edu.cujae.ceis.tree.binary.BinaryTreeNode;

import java.io.*;
import java.util.*;

public class Convert {

	public static boolean[] arrayListToByteArray(ArrayList<Boolean> arrayList) {
		boolean[] byteArray = new boolean[arrayList.size()];
		for (int i = 0; i < arrayList.size(); i++) {
			byteArray[i] = arrayList.get(i);
		}
		return byteArray;
	}

	private static boolean[] byteToBits(byte b) {
		boolean[] bits = new boolean[8];
		for (int i = 0; i < 8; i++) {
			bits[i] = ((b >> (7 - i)) & 1) == 1;
		}
		return bits;
	}
	public static boolean[] bytesToBits(byte[] b){
		boolean[] bits= new boolean[b.length*8];
		int pos=0;
		for(int i=0; i<b.length;i++){
			boolean[] bitsaux=byteToBits(b[i]);
			for(boolean x: bitsaux)
				bits[pos++]=x;
		}
		return bits;
	}
	public static byte bitsToByte(boolean[] bits) {
		byte b = 0;
		for (int i = 0; i < 8; i++) {
			if (bits[i]) {
				b |= (1 << (7 - i));
			}
		}
		return b;
	}
	public static byte[] bitsToBytes(boolean[] bits) {
		int numBytes = (int) Math.ceil(bits.length / 8.0);
		byte[] bytes = new byte[numBytes];
		for (int i = 0; i < numBytes; i++) {
			boolean[] byteBits = new boolean[8];
			for (int j = 0; j < 8 && i * 8 + j < bits.length; j++) {
				byteBits[j] = bits[i * 8 + j];
			}
			bytes[i] = bitsToByte(byteBits);
		}
		return bytes;
	}
	public static void printBits(boolean[] bits) {
		for (boolean b : bits) {
			System.out.print(b ? "1" : "0");
		}
		System.out.println();
	}


	public static byte[] toBytes(Object object) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(object); 
		return baos.toByteArray(); 
	} 


	
	public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
		Object object = null;
		object = new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject();
		return object; 
		}




	//Esta funcion remueve la extencion de una cadena por ejemplo para hola.txt devuelve hola
	public static String removeExtension(String texto) {
		int indice = texto.indexOf(".");
		if (indice != -1) {
			texto =texto.substring(0, indice);
		}
		return texto;
	}
	//Permite obtener la extension de un archivo
	public static String obtenerExtension(String nombreArchivo) {
	    int puntoIndex = nombreArchivo.lastIndexOf('.');
	    if (puntoIndex > 0 && puntoIndex < nombreArchivo.length() - 1) {
	        return nombreArchivo.substring(puntoIndex + 1);
	    }
	    return "";
	}

	//Metodo para convertir en arbol de huffman en un arreglo de byte utilizando el algoritmo de serie de bits
	public static byte[] save_Huffman_Tree_As_Bits(BinaryTree<Byte> tree) {
		BinaryTreeNode<Byte> root = (BinaryTreeNode) tree.getRoot();
		StringBuilder bits = new StringBuilder();
		guardarArbolHuffmanComoBitsRec(root, bits);
		// Asegurarse de que la longitud de bits sea un múltiplo de 8
		while (bits.length() % 8 != 0) {
			bits.append('0');
		}
		// Convertir la cadena de bits a un arreglo de bytes
		byte[] bytes = convertStringBitsToBytes(bits.toString());
		return bytes;
	}
	//Convertir los bits en bytes para serializar
	private static byte[] convertStringBitsToBytes(String bits){
		byte[] bytes = new byte[bits.length()/8];
		for (int i= 0; i < bytes.length; i++){
			int byteValue = 0;
			for( int j=0 ; j<8 ; j++){
				if(bits.charAt(i * 8 + j)=='1')
					byteValue |=(1<< (7-j));
			}
			bytes[i]= (byte)byteValue;
		}
		return bytes;
	}
	//Convertir los bytes en bits para deSerializar
	private static String convertBytesToStringBits(byte[] bytes){
		StringBuilder bits = new StringBuilder();
		for (byte b : bytes) {
			bits.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
		}
		return bits.toString();
	}

	private static void guardarArbolHuffmanComoBitsRec(BinaryTreeNode<Byte> nodo, StringBuilder bits) {
		if (nodo.getLeft()==null) {
			bits.append('1');
			byte byteValue = nodo.getInfo();
			//Esto es para darle el formato correcto
			bits.append(String.format("%8s", Integer.toBinaryString(byteValue & 0xFF)).replace(' ', '0'));
		} else {
			bits.append('0');
			guardarArbolHuffmanComoBitsRec(nodo.getLeft(), bits);
			guardarArbolHuffmanComoBitsRec(nodo.getRight(), bits);
		}
	}
	//Metodo que convierte el array de bytes en un arbol
	public static BinaryTree leerArbolHuffmanDesdeBits(byte[] bytes) {
		String bits = convertBytesToStringBits(bytes);

		int[] index = new int[] {0};

		BinaryTreeNode root= leerArbolHuffmanDesdeBitsRec(bits, index);
		BinaryTree tree=  new BinaryTree();
		tree.setRoot(root);
		return tree;
	}

	private static BinaryTreeNode leerArbolHuffmanDesdeBitsRec(String bits, int[] index) {
		BinaryTreeNode root;
		if (index[0] >= bits.length()) {
			root= null;
		}
		char bit = bits.charAt(index[0]++);
		if (bit == '1') {
			byte byteValue = (byte) Integer.parseInt(bits.substring(index[0], index[0] + 8), 2);
			index[0] += 8;
			root=  new BinaryTreeNode(byteValue);
		} else {
			BinaryTreeNode izquierdo = leerArbolHuffmanDesdeBitsRec(bits, index);
			BinaryTreeNode derecho = leerArbolHuffmanDesdeBitsRec(bits, index);
			root=  new BinaryTreeNode(0);
			root.setLeft(izquierdo);
			root.setRight(derecho);
		}
		return root;
	}
	//Este metodo convierte un int en una cadena de string q representa su valor en bits, tiene tamaño 8 siempre
	public static String intToBits(int numero) {
		return String.format("%32s", Integer.toBinaryString(numero)).replace(' ', '0');
	}
	//Recibe los bits del intero y los convierto en el int utilizando la base 2
	public static int bitsToInt(String bits){
		return Integer.parseInt(bits, 2);
	}

	//Este metodo permite serializar un grafo para guardarlo en memoria externa
	public static LinkedList<byte[]> SerializarGrafo(Grafo grafo){
		StringBuilder bits = new StringBuilder();
		LinkedList<byte[]> infoReducida= new LinkedList<>();
		int tamNuevoArray = 0;
		bits.append(intToBits(grafo.getListaVertices().size()));
		Iterator<Nodo> it = grafo.getListaVertices().iterator();
		while(it.hasNext()){
			Nodo current = it.next();
			if(current.getBloque().length>0){
				bits.append('1');
				bits.append(intToBits(current.getBloque().length));
				tamNuevoArray+=current.getBloque().length;
				infoReducida.add(current.getBloque());
			}else
				bits.append('0');
			if(current.getEnlace().size()>0){
				Iterator<Enlace> itEnlance= current.getEnlace().iterator();
				while(itEnlance.hasNext()){
					Enlace currentEnlace = itEnlance.next();
					bits.append('1');
					bits.append(intToBits(currentEnlace.getProximo_nodo()));
					bits.append(intToBits(currentEnlace.getPosicion()));
					bits.append(intToBits(currentEnlace.getLongitud()));
				}
			}
			bits.append('0');
		}
		while (bits.length() % 8 != 0) {
			bits.append('0');
		}
		byte [] bytesInfoGraph= convertStringBitsToBytes(bits.toString());

		byte [] bytesInfoCode = new byte[tamNuevoArray];
		int pos=0;
		Iterator<byte[]> it2 = infoReducida.iterator();
		while(it2.hasNext()){
			byte[] currentArray= it2.next();
			System.arraycopy(currentArray, 0, bytesInfoCode, pos, currentArray.length);
			pos+= currentArray.length;
		}
		LinkedList<byte[]> out = new LinkedList<>();
		out.add( bytesInfoGraph);
		out.add( bytesInfoCode);
		return out;
	}
	public static Grafo obtainGraphToSerialization(LinkedList<byte[]> list){


		//Obtengo los bits de los bytes del grafo
		String bits = convertBytesToStringBits(list.get(0));
		int cantNodos=bitsToInt(bits.substring(0,32));
		Grafo graph = new Grafo(cantNodos);

		//Indica la posicion de la info del grafo
		int iteradorGraph= 32;

		//Indica la posicion de la informacion del byte
		int iteradorInfo=0;

		for (int i= 0; i<cantNodos; i++){
			Nodo nodoCurrent;

			//si tiene 1 es que tiene informacion por tanto hay q obtenerla
			String substring= bits.substring(iteradorGraph, iteradorGraph+1);
			iteradorGraph++;
			if(substring.equals("1")){
				String substring2=bits.substring(iteradorGraph,iteradorGraph+32);
				long tamInfo=0;
				try {
					 tamInfo = bitsToInt(substring2);
				}catch (Exception e){
					System.out.println();
				}
				iteradorGraph+=32;
				byte[] datos = new byte[(int) tamInfo];
				System.arraycopy(list.get(1), iteradorInfo, datos,0 , (int)tamInfo);
				iteradorInfo+= tamInfo;

				//En este punto ya tengo la informacion del nodo
				nodoCurrent= new Nodo(datos);
			}else {
				//Si el nodo no tiene informacion
				nodoCurrent = new Nodo(new byte[0]);
			}
			//Comienzo a iterar por mientra tenga enlaces

			while(bits.substring(iteradorGraph,(iteradorGraph++)+1).equals("1")){
				int posNodo= bitsToInt(bits.substring(iteradorGraph, iteradorGraph+32));
				iteradorGraph+=32;
				int posDatos= bitsToInt(bits.substring(iteradorGraph, iteradorGraph+32));
				iteradorGraph+=32;
				int longitud= bitsToInt(bits.substring(iteradorGraph, iteradorGraph+32));
				iteradorGraph+=32;
				nodoCurrent.addEnlace(new Enlace(posNodo, posDatos, longitud));
			}
			graph.addNodo(nodoCurrent);
		}
		return graph;
	}
	public static String obtenerTamanoConUnidad(long tamanoBytes) {
	    String unidad = "B";
	    double tamano = tamanoBytes;
	    if (tamanoBytes >= 1024) {
	        unidad = "KB";
	        tamano = tamanoBytes / 1024.0;
	    }
	    if (tamanoBytes >= 1024 * 1024) {
	        unidad = "MB";
	        tamano = tamanoBytes / (1024.0 * 1024);
	    }
	    if (tamanoBytes >= 1024 * 1024 * 1024) {
	        unidad = "GB";
	        tamano = tamanoBytes / (1024.0 * 1024 * 1024);
	    }
	    return String.format("%.2f %s", tamano, unidad);
	}

}



