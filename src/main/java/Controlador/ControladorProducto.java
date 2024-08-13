/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

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
public class ControladorProducto {
    //Metodo mostrar productos
    public void MostrarProductos( JTable tablaTotalProductos){
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        Modelos.ModeloProducto objetoProducto = new Modelos.ModeloProducto();
        DefaultTableModel modelo = new DefaultTableModel();
        
        String sql = "";
        
        modelo.addColumn("id");
        modelo.addColumn("NombreProd");
        modelo.addColumn("Precio");
        modelo.addColumn("Stock");
        
        tablaTotalProductos.setModel(modelo);
        
        sql = "SELECT producto.idproducto, producto.nombre, producto.precioProducto, producto.stock FROM producto;";
        
        try {
            Statement st = objetoConexion.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                objetoProducto.setIdproducto(rs.getInt("idproducto"));
                objetoProducto.setNombreProducto(rs.getString("nombre"));
                objetoProducto.setPrecioProducto(rs.getDouble("precioProducto"));
                objetoProducto.setStockProducto(rs.getInt("stock"));
                
                modelo.addRow(new Object[]{objetoProducto.getIdproducto(), objetoProducto.getNombreProducto(), objetoProducto.getPrecioProducto(), objetoProducto.getStockProducto()});
            }
            tablaTotalProductos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al mostrar productos, "+e.toString());
        } finally {
            objetoConexion.cerrarConecion();
        }
    }
    
    //Metodo agregar producto
    public void AgregarProducto(JTextField nombres, JTextField precioProducto,JTextField stock){
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        Modelos.ModeloProducto objetoProducto = new Modelos.ModeloProducto();
        String consulta = "INSERT INTO producto(nombre,precioProducto,stock) values (?,?,?);";
        
        try {
            objetoProducto.setNombreProducto(nombres.getText());
            objetoProducto.setPrecioProducto(Double.valueOf(precioProducto.getText()));
            objetoProducto.setStockProducto(Integer.parseInt(stock.getText()));
            
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, objetoProducto.getNombreProducto());
            cs.setDouble(2, objetoProducto.getPrecioProducto());
            cs.setInt(3, objetoProducto.getStockProducto());
            
            cs.execute();
            JOptionPane.showMessageDialog(null,"Se guardó correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al guardar: "+ e.toString());
        } finally {
            objetoConexion.cerrarConecion();
        }
    }
    
    //Método seleccionar producto
    public void Seleccionar(JTable totalProducto, JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock){
        int fila = totalProducto.getSelectedRow();
        try {
            if (fila >= 0) {
             id.setText(totalProducto.getValueAt(fila, 0).toString());
             nombres.setText(totalProducto.getValueAt(fila, 1).toString());
             precioProducto.setText(totalProducto.getValueAt(fila, 2).toString());
             stock.setText(totalProducto.getValueAt(fila, 3).toString());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar: "+e.toString());
        }
    }
    
    //metpdp modificar producto
    public void ModificarProducto(JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock ){
        
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        Modelos.ModeloProducto objetoProducto = new Modelos.ModeloProducto();
        
        String consulta = "UPDATE producto SET producto.nombre = ?, producto.precioProducto=?, producto.stock=? WHERE producto.idproducto=?;";
        try {
            objetoProducto.setIdproducto(Integer.parseInt(id.getText()));
            objetoProducto.setNombreProducto(nombres.getText() );
            objetoProducto.setPrecioProducto(Double.valueOf(precioProducto.getText()));
            objetoProducto.setStockProducto(Integer.parseInt(stock.getText()));
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setString(1, objetoProducto.getNombreProducto());
            cs.setDouble(2, objetoProducto.getPrecioProducto());
            cs.setInt(3, objetoProducto.getStockProducto());
            cs.setInt(4, objetoProducto.getIdproducto());
            cs.execute();
            
            JOptionPane.showMessageDialog(null, "Se modificó correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar: "+e);
        } finally {
            objetoConexion.cerrarConecion();
        }
    }
    
    //metodo limpiar campos productos
    public void LimpiarCamposProducto(JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock){
        id.setText("");
        nombres.setText("");
        precioProducto.setText("");
        stock.setText("");
    }
    
    //metodo eliminar productos
    public void EliminarProductos(JTextField id){
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        Modelos.ModeloProducto objetoProducto = new Modelos.ModeloProducto();
        String consulta = "DELETE FROM producto WHERE producto.idproducto=?;";
        
        try {
            objetoProducto.setIdproducto(Integer.parseInt(id.getText()));
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, objetoProducto.getIdproducto());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se eliminó correctamente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se logro eliminar correctamente, erro: "+e.toString());
        } finally {
            objetoConexion.cerrarConecion();
        }
    }

}