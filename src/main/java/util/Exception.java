package util;

public class Exception extends RuntimeException {
    /**
     * implementamos un constructor donde recibe como parametro un mensaje
     * luego llamamos a la calase constructor de la clase padre para que
     * lance la exception
     */
    public Exception(String message){
        super(message);
    }
    /**
     * implementamos un constructor donde recibe dos parámetros como el mensaje
     * y la causa de la excepcion. luego se invoca al constructor de la clase padre
     * donde devuelve la causa de forma tecnica
     */
    public Exception(String mensaje, Throwable cause) {
        super(cause);
    }
}
