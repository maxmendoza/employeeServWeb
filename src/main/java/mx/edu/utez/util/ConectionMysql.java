package mx.edu.utez.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectionMysql {
    public static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/ventas?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","2422");
    }

    public static void main(String[] args) {
        try{
            Connection con = ConectionMysql.getConnection();
            System.out.println("Conexión exitosa");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
