/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;

import Clases.Analizador;
import GUI.InicioGUI;
import java.util.Scanner;

/**
 *
 * @author Alex
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Principal principal = new Principal();
        InicioGUI inicioGUI = new InicioGUI(principal);
        inicioGUI.setVisible(true);
    }

}
