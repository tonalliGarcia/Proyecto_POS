/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelos.ModeloCliente;
import java.sql.CallableStatement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.concurrent.Callable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author 52951
 */
public class ControladorVenta {

    public void BuscarProducto(JTextField nombreProducto, JTable tablaproductos) {
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        Modelos.ModeloProducto objetoProducto = new Modelos.ModeloProducto();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("id");
        modelo.addColumn("NombreP");
        modelo.addColumn("precioProducto");
        modelo.addColumn("Stock");

        tablaproductos.setModel(modelo);

        try {
            String consulta = "SELECT * FROM producto WHERE producto.nombre like concat('%',?,'%');";
            PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
            ps.setString(1, nombreProducto.getText());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                objetoProducto.setIdproducto(rs.getInt("idproducto"));
                objetoProducto.setNombreProducto(rs.getString("nombre"));
                objetoProducto.setPrecioProducto(rs.getDouble("precioProducto"));
                objetoProducto.setStockProducto(rs.getInt("stock"));

                modelo.addRow(new Object[]{objetoProducto.getIdproducto(), objetoProducto.getNombreProducto(), objetoProducto.getPrecioProducto(), objetoProducto.getStockProducto()});
            }
            tablaproductos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar " + e.toString());
        } finally {
            objetoConexion.cerrarConecion();
        }
        for (int column = 0; column < tablaproductos.getColumnCount(); column++) {
            Class<?> columnClass = tablaproductos.getColumnClass(column);
            tablaproductos.setDefaultEditor(columnClass, null);
        }
    }

    public void SeleccionarProductoVenta(JTable tablaProducto, JTextField id, JTextField nombres, JTextField precioProducto, JTextField stock, JTextField precioFinal) {
        int fila = tablaProducto.getSelectedRow();

        try {
            if (fila >= 0) {
                id.setText(tablaProducto.getValueAt(fila, 0).toString());
                nombres.setText(tablaProducto.getValueAt(fila, 1).toString());
                precioProducto.setText(tablaProducto.getValueAt(fila, 2).toString());
                stock.setText(tablaProducto.getValueAt(fila, 3).toString());
                precioFinal.setText(tablaProducto.getValueAt(fila, 2).toString());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de selección " + e.toString());
        }
    }

    public void BuscarCliente(JTextField nombreCliente, JTable tablaclientes) {
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        Modelos.ModeloCliente objetoCliente = new Modelos.ModeloCliente();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("id");
        modelo.addColumn("Nombres");
        modelo.addColumn("ApPaterno");
        modelo.addColumn("ApMaterno");

        tablaclientes.setModel(modelo);

        try {
            String consulta = "SELECT * FROM cliente WHERE cliente.nombres like concat('%',?,'%');";
            PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
            ps.setString(1, nombreCliente.getText());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                objetoCliente.setIdCliente(rs.getInt("idcliente"));
                objetoCliente.setNombres(rs.getString("nombres"));
                objetoCliente.setApPaterno(rs.getString("appaterno"));
                objetoCliente.setApMaterno(rs.getString("apmaterno"));

                modelo.addRow(new Object[]{objetoCliente.getIdCliente(), objetoCliente.getNombres(), objetoCliente.getApPaterno(), objetoCliente.getApMaterno()});
            }
            tablaclientes.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar " + e.toString());
        } finally {
            objetoConexion.cerrarConecion();
        }
        for (int column = 0; column < tablaclientes.getColumnCount(); column++) {
            Class<?> columnClass = tablaclientes.getColumnClass(column);
            tablaclientes.setDefaultEditor(columnClass, null);
        }
    }

    public void SeleccionarClienteVenta(JTable tablaCliente, JTextField id, JTextField nombres, JTextField appaterno, JTextField apmaterno) {
        int fila = tablaCliente.getSelectedRow();

        try {
            if (fila >= 0) {
                id.setText(tablaCliente.getValueAt(fila, 0).toString());
                nombres.setText(tablaCliente.getValueAt(fila, 1).toString());
                appaterno.setText(tablaCliente.getValueAt(fila, 2).toString());
                apmaterno.setText(tablaCliente.getValueAt(fila, 3).toString());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de selección " + e.toString());
        }
    }

    public void pasarProductosVenta(JTable tablaResumen, JTextField idproducto, JTextField nombreProducto, JTextField precioProducto, JTextField cantidadVenta, JTextField stock) {
        DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();
        int stockDisponible = Integer.parseInt(stock.getText());
        String idProducto = idproducto.getText();
        for (int i = 0; i < modelo.getRowCount(); i++) {
            String idExistente = (String) modelo.getValueAt(i, 0);
            if (idExistente.equals(idProducto)) {
                JOptionPane.showMessageDialog(null, "Producto ya registrado");
                return;
            }
        }
        String nProducto = nombreProducto.getText();
        double precioUnitario = Double.parseDouble(precioProducto.getText());
        int cantidad = Integer.parseInt(cantidadVenta.getText());
        if (cantidad > stockDisponible) {
            JOptionPane.showMessageDialog(null, "Cantidad mayor al stock disponible");
            return;
        }
        double subtotal = precioUnitario * cantidad;
        modelo.addRow(new Object[]{idProducto, nProducto, precioUnitario, cantidad, subtotal});
    }

    public void eliminarProductosSeleccionadosResumenVenta(JTable tablaResumen) {

        try {
            DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();
            int indiceSeleccionado = tablaResumen.getSelectedRow();

            if (indiceSeleccionado != -1) {
                modelo.removeRow(indiceSeleccionado);
            } else {
                JOptionPane.showMessageDialog(null, "Seleccione una fila para eliminar");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al seleccionar: " + e.toString());
        }
    }

    public void calcularTotalPagar(JTable tablaResumen, JLabel IVA, JLabel totalPagar) {
        DefaultTableModel modelo = (DefaultTableModel) tablaResumen.getModel();
        double totalSubtotal = 0;
        double iva = 0.18;
        double totaliva = 0;

        DecimalFormat formato = new DecimalFormat("#.##");
        for (int i = 0; i < modelo.getRowCount(); i++) {
            totalSubtotal = Double.parseDouble(formato.format(totalSubtotal + (double) modelo.getValueAt(i, 4)));
            totaliva = Double.parseDouble(formato.format(iva * totalSubtotal));
        }
        totalPagar.setText(String.valueOf(totalSubtotal));
        IVA.setText(String.valueOf(totaliva));
    }
    
    public void crearFactura(JTextField codCliente){
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        Modelos.ModeloCliente objetoCliente = new Modelos.ModeloCliente();
        String consulta = "INSERT INTO factura (fechaFactura,fkcliente) values (curdate(),?);";
        try {
            objetoCliente.setIdCliente(Integer.parseInt(codCliente.getText()));
            //CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            CallableStatement cs = objetoConexion.estableceConexion().prepareCall(consulta);
            cs.setInt(1, objetoCliente.getIdCliente());
            cs.execute();
            JOptionPane.showMessageDialog(null,"Factura creada");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro al crear factura: "+e.toString());
        } finally {
            objetoConexion.cerrarConecion();
        }
    }
    
    public void realizarVenta(JTable tablaResumenVenta){
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        String consultaDetalle = "INSERT INTO detalle (fkfactura,fkproducto,cantidad,precioVenta) values ((SELECT MAX(idfactura)FROM factura),?,?,?);";
        String consultaStock = "UPDATE producto SET producto.stock = stock - ? WHERE idproducto = ?;";
        try {
            PreparedStatement psDetalle = objetoConexion.estableceConexion().prepareStatement(consultaDetalle);
            PreparedStatement psStock = objetoConexion.estableceConexion().prepareStatement(consultaStock);
            int filas = tablaResumenVenta.getRowCount();
            for (int i = 0; i < filas; i++) {
                
                int idProducto = Integer.parseInt(tablaResumenVenta.getValueAt(i, 0).toString());
                int cantidad = Integer.parseInt(tablaResumenVenta.getValueAt(i, 3).toString());
                double precioVenta = Double.parseDouble(tablaResumenVenta.getValueAt(i, 2).toString());
                
                psDetalle.setInt(1,idProducto);
                psDetalle.setInt(2,cantidad);
                psDetalle.setDouble(3,precioVenta);                
                psDetalle.executeUpdate();
                
                psStock.setInt(1,cantidad);
                psStock.setInt(2,idProducto);
                psStock.executeUpdate();                
            }
            JOptionPane.showMessageDialog(null,"Venta Realizada");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al vender: " + e.toString());
        } finally {
            objetoConexion.cerrarConecion();
        }    
    }
    //tbclientes, tbproductos, tbresumenventa, lbliva, lbltotal
    public void limpiarCamposLuegoAgregar(JTextField buscarCliente, JTextField buscarProducto, JTextField selectIdCliente, 
                                          JTextField selectNombreCliente, JTextField selectAppaternoCliente,
                                          JTextField selectApmaternoCliente, JTextField selectIdProducto, JTextField selectNombreProducto,
                                          JTextField selectPrecioProducto, JTextField selectStockProducto, JTextField precioVenta,
                                          JTextField cantidadVenta){
        buscarCliente.setText("");
        buscarCliente.requestFocus();        
        buscarProducto.setText("");        
        selectIdCliente.setText("");
        selectNombreCliente.setText("");
        selectAppaternoCliente.setText("");
        selectApmaternoCliente.setText("");        
        selectIdProducto.setText("");
        selectNombreProducto.setText("");
        selectPrecioProducto.setText("");
        selectStockProducto.setText("");        
        precioVenta.setText("");
        precioVenta.setEnabled(false);
        cantidadVenta.setText("");        
    }    
    public void limpiarCamposLuegoVenta(JTextField buscarCliente, JTable tablaCliente, JTextField buscarProducto, JTable tablaproducto,
                                        JTextField selectIdCliente, JTextField selectNombreCliente, JTextField selectAppaternoCliente,
                                        JTextField selectApmaternoCliente, JTextField selectIdProducto, JTextField selectNombreProducto,
                                        JTextField selectPrecioProducto, JTextField selectStockProducto, JTextField precioVenta,
                                        JTextField cantidadVenta, JTable tablaresumen, JLabel IVA, JLabel total){
        buscarCliente.setText("");
        buscarCliente.requestFocus();
        DefaultTableModel modeloCliente = (DefaultTableModel) tablaCliente.getModel();
        modeloCliente.setRowCount(0);
        
        buscarProducto.setText("");
        DefaultTableModel modeloProducto = (DefaultTableModel) tablaproducto.getModel();
        modeloProducto.setRowCount(0);
        
        selectIdCliente.setText("");
        selectNombreCliente.setText("");
        selectAppaternoCliente.setText("");
        selectApmaternoCliente.setText("");
        
        selectIdProducto.setText("");
        selectNombreProducto.setText("");
        selectPrecioProducto.setText("");
        selectStockProducto.setText("");
        
        precioVenta.setText("");
        precioVenta.setEnabled(false);
        cantidadVenta.setText("");
        
        DefaultTableModel modeloResumenVenta = (DefaultTableModel) tablaresumen.getModel();
        modeloResumenVenta.setRowCount(0);
        
        IVA.setText("---");
        total.setText("---");
    }
    
    public void MostrarUltimaFactura(JLabel ultimaFactura){
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        try {
            String consulta = "SELECT MAX(idfactura) as UltimaFactura FROM factura";
            PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ultimaFactura.setText(String.valueOf(rs.getInt("UltimaFactura")));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error al buscar la ultima factura: "+e.toString());
        } finally {
            objetoConexion.cerrarConecion();
        }
    
    } 
}
