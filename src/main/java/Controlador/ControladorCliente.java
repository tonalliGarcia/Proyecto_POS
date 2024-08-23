/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelos.ModeloCliente;
import com.mysql.cj.jdbc.result.ResultSetFactory;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 52951
 */
public class ControladorCliente {
    //Método mostrar clientes
    public void MostrarClientes( JTable tablaTotalClientes){
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        Modelos.ModeloCliente objetoCliente = new Modelos.ModeloCliente();
        DefaultTableModel modelo = new DefaultTableModel();
        
        String sql = "";
        
        modelo.addColumn("id");
        modelo.addColumn("Nombres");
        modelo.addColumn("ApPaterno");
        modelo.addColumn("ApMaterno");
        
        tablaTotalClientes.setModel(modelo);
        
        sql = "SELECT cliente.idcliente,cliente.nombres,cliente.appaterno,cliente.apmaterno FROM cliente";
        
        try {
            Statement st = objetoConexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                objetoCliente.setIdCliente(rs.getInt("idcliente"));
                objetoCliente.setNombres(rs.getString("nombres"));
                objetoCliente.setApPaterno(rs.getString("appaterno"));
                objetoCliente.setApMaterno(rs.getString("apmaterno"));
                
                modelo.addRow(new Object[]{objetoCliente.getIdCliente(), objetoCliente.getNombres(), objetoCliente.getApPaterno(), objetoCliente.getApMaterno()});
            }
            tablaTotalClientes.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al mostrar usuarios, "+e.toString());
        } finally {
            objetoConexion.cerrarConecion();
        }
        
        //bloquear elementos de la tabla clientes
        for (int column = 0; column < tablaTotalClientes.getColumnCount(); column++) {
            Class<?> columClass = tablaTotalClientes.getColumnClass(column);
            tablaTotalClientes.setDefaultEditor(columClass, null);
        }      
        
    }    
    
    //Metodo guardar cliente
    public void AgregarCliente(JTextField nombres, JTextField appaterno,JTextField apmaterno){
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        Modelos.ModeloCliente objetoCliente = new Modelos.ModeloCliente();
        String consulta = "insert into cliente (nombres,appaterno,apmaterno) values (?,?,?);";
        
        try {
            objetoCliente.setNombres(nombres.getText());
            objetoCliente.setApPaterno(appaterno.getText());
            objetoCliente.setApMaterno(apmaterno.getText());
            
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, objetoCliente.getNombres());
            cs.setString(2, objetoCliente.getApPaterno());
            cs.setString(3, objetoCliente.getApMaterno());
            
            cs.execute();
            JOptionPane.showMessageDialog(null,"Se guardó correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al guardar: "+ e.toString());
        } finally {
            objetoConexion.cerrarConecion();
        }
    }
    
    //aqui deja de trabjar propiamente como un controlador
    ////Metodo para seleccionar un  un registro
    public void Seleccionar(JTable totalcliente, JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno){
        int fila = totalcliente.getSelectedRow();
        try {
            if (fila >= 0) {
             id.setText(totalcliente.getValueAt(fila, 0).toString());
             nombres.setText(totalcliente.getValueAt(fila, 1).toString());
             appaterno.setText(totalcliente.getValueAt(fila, 2).toString());
             apmaterno.setText(totalcliente.getValueAt(fila, 3).toString());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar: "+e.toString());
        }
    }
    
    public void ModificarCliente(JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno ){
        
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        Modelos.ModeloCliente objetoCliente = new Modelos.ModeloCliente();
        
        String consulta = "UPDATE cliente SET cliente.nombres = ?, cliente.appaterno=?, cliente.apmaterno=? WHERE cliente.idcliente=?;";
        try {
            objetoCliente.setIdCliente(Integer.parseInt(id.getText()));
            objetoCliente.setNombres(nombres.getText() );
            objetoCliente.setApPaterno(appaterno.getText() );
            objetoCliente.setApMaterno(apmaterno.getText() );
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, objetoCliente.getNombres());
            cs.setString(2, objetoCliente.getApPaterno());
            cs.setString(3, objetoCliente.getApMaterno());
            cs.setInt(4, objetoCliente.getIdCliente());
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se modificó correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar: "+e);
        } finally {
            objetoConexion.cerrarConecion();
        }
    }
    
    public void LimpiarCamposClientes(JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno){
        id.setText("");
        nombres.setText("");
        appaterno.setText("");
        apmaterno.setText("");
    }
    
    public void EliminarCliente(JTextField id){
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        Modelos.ModeloCliente objetoCliente = new Modelos.ModeloCliente();
        String consulta = "DELETE FROM cliente WHERE cliente.idcliente=?;";
        
        try {
            objetoCliente.setIdCliente(Integer.parseInt(id.getText()));
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, objetoCliente.getIdCliente());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se eliminó correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se logro eliminar correctamente, erro: "+e.toString());
        } finally {
            objetoConexion.cerrarConecion();
        }
    }
}
