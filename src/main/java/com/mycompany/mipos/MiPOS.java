/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mipos;

/**
 *
 * @author 52951
 */
public class MiPOS {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Configuracion.CConexion objetoConexion = new Configuracion.CConexion();
        objetoConexion.estableceConexion();
        
    }
}
