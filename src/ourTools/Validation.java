package ourTools;

import java.io.File;
import java.io.FileNotFoundException;

public class Validation {
    public static void existFile(File file) throws FileNotFoundException {
        if(!file.exists())
            throw new FileNotFoundException(file.getAbsolutePath()+" (El sistema no puede encontrar la ruta especificada)");
    }
    public static boolean validarNombre(String cadena) {
    	boolean salida= true;
        // Verifica si la cadena no está vacía
        if (cadena.isEmpty()) {
        	salida= false;
        }
        
        // Verifica si la cadena contiene puntos
        else if (cadena.contains(".")) {
        	salida= false;
        }
        
        // Verifica si la cadena tiene 25 caracteres
        else if (cadena.length() >= 20) {
        	salida= false;
        }
        else if (cadena.matches(".*[<>:\"/\\\\|?*].*")) {
        	salida=  false;
        }
        
        // Si la cadena pasa todas las validaciones, devuelve true
        return salida;
    }
}
