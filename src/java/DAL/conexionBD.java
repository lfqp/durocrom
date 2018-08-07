package DAL;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class conexionBD {

    public static String getRutaPaso() {
        String PATH = null;
        try {
            Context env = (Context) new InitialContext().lookup("java:comp/env");
            String config_path = (String) env.lookup("ConfigPath");
            String[] arr_path = config_path.split(";");
            
            for (int i = 0; i <= arr_path.length; i++) {
                String path = arr_path[i];
                //System.out.println("Probando ruta: " + path + "Durocrom.cfg");
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(path + "Durocrom.cfg"));
                    //System.out.println("ruta encontrada: " + path + "Durocrom.cfg");
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("PATH")) {
                            PATH = line.substring(line.indexOf("=") + 1);
                            return PATH;
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (NamingException ex) {
            ex.printStackTrace();
        }
        
        return PATH;
    }

    public static Connection Conectar(String database) throws Exception {
        Connection con = null;
        String JDBC = null;
        String IP = null;
        String PUERTO = null;
        String DBNAME = null;
        String USUARIO = null;
        String PASSWORD = null;

        try {

            Context env = (Context) new InitialContext().lookup("java:comp/env");
            String config_path = (String) env.lookup("ConfigPath");
            String[] arr_path = config_path.split(";");

            for (int i = 0; i <= arr_path.length; i++) {
                String path = arr_path[i];
                //System.out.println("Probando ruta: " + path + "Durocrom.cfg");
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(path + "Durocrom.cfg"));

                    //System.out.println("ruta encontrada: " + path + "Durocrom.cfg");
                    String line;
                    while ((line = reader.readLine()) != null) {

                        if (line.startsWith("JDBC")) {
                            JDBC = line.substring(line.indexOf("=") + 1);
                        }
                        if (line.startsWith("IP")) {
                            IP = line.substring(line.indexOf("=") + 1);
                        }
                        if (line.startsWith("PUERTO")) {
                            PUERTO = line.substring(line.indexOf("=") + 1);
                        }
                        if (line.startsWith("DBNAME")) {
                            DBNAME = line.substring(line.indexOf("=") + 1);
                        }
                        if (line.startsWith("USUARIO")) {
                            USUARIO = line.substring(line.indexOf("=") + 1);
                        }
                        if (line.startsWith("PASSWORD")) {
                            PASSWORD = line.substring(line.indexOf("=") + 1);
                        }
                    }
                    i = arr_path.length;
                } catch (Exception Ex) {
                    //System.out.println(Ex.getMessage());
                }
            }

            if (IP != null && JDBC != null && PUERTO != null && DBNAME != null && USUARIO != null && PASSWORD != null) {
                String driverClassName = "com.mysql.jdbc.Driver";
                Class.forName(driverClassName);
                String driverUrl = "jdbc:" + JDBC + "://" + IP + ":" + PUERTO + "/" + DBNAME;
                System.out.println("URL "+driverUrl);
                System.out.println("USUARIO "+USUARIO);
                System.out.println("PASS "+PASSWORD);                
                con = DriverManager.getConnection(driverUrl, USUARIO, PASSWORD);
            } else {
                System.out.println("Archivo de configuracion Durocrom.cfg no encontrado");
            }
            //String user = "root";//(String)env.lookup("user");//root
            //String pass = "123456";//(String)env.lookup("pass");//admin
            //String driverUrl = "jdbc:mysql://192.168.255.4:3306/sisventasmetal";

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            System.out.println("ERROR: "+e.getMessage());
        }
        return con;
    }

    public static String agregaCaracter(String texto) {
        String cadena = "";
        int nroCaracter = texto.length();
        int diferencia = 20 - nroCaracter;
        int i = 0;
        cadena = texto;
        while (i <= diferencia) {
            cadena += "&nbsp;";
            i++;
        }
        return cadena;
    }
}
