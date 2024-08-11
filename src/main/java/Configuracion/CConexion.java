/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Configuracion;

import java.sql.Connection;
import java.sql.DriverManager;// observacion 1
import javax.swing.JOptionPane;

/**
 *
 * @author 52951
 */
public class CConexion {
    Connection conectar = null;
    String usuario ="root";
    String contrasenia ="SoftDeveloper12#";
    String bd ="dbpos";
    String ip ="127.0.0.1";
    String puerto ="3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    //funcion para abrir y cerrar conecion
    public Connection estableceConexion(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");//observacion 2
            conectar = DriverManager.getConnection(cadena,usuario,contrasenia);
            JOptionPane.showMessageDialog(null, "Conexion Correcta a la BD");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "No se conecto a la BD " + e.toString());
        }
        return conectar;
    }
    
    public void cerrarConecion(){
        try {
            if (conectar != null && !conectar.isClosed()) {
                conectar.close();      
                JOptionPane.showMessageDialog(null, "La Conexion se ha cerrado");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se logro cerrar la conexion "+e.toString());
        }
    
    }    
}
