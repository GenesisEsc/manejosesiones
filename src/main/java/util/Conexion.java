package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static String url = "jdbc:mysql://localhost:3306/sistemaventas?serverTimezone=UTC";
    private static String username = "root";
    private static String password = "Mysql123";

    public static Connection getConnection()throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }
    //metodo de prueba
    public static void main (String [] args) {
        try(Connection conn = Conexion.getConnection()){
            if(conn != null) {
                System.out.println("Conexión exitosa a la base de datos");
            }
        }catch(SQLException e){
            System.out.println("Error al conectarse a la bdd: " + e.getMessage());
        }
    }
}
