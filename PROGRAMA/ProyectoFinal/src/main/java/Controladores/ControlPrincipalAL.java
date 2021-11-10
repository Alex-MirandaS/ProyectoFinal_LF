/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Clases.AnalizadorS;
import Enums.TipoGramatica;
import Enums.TipoToken;
import GUI.PrincipalGUI;
import GUI.TablaResultados;
import Main.Principal;
import Objetos.Contable;
import Objetos.Gramatica;
import Objetos.Token;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 *
 * @author Alex
 */
public class ControlPrincipalAL {

    private Principal principal;

    private ArrayList<String> filasArchivo = new ArrayList<>();
    private ArrayList<Token> tokens = new ArrayList<>();
    private String pathDocumentoActual;
    private boolean conservarTexto = true;
    private boolean esperar = true;

    public ControlPrincipalAL(Principal principal) {
        this.principal = principal;
    }

    private boolean areaTextoVacía() {
        if (!principal.getPrincipalGUI().getAreaTexto().getText().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public void guardarCambios(boolean eleccion) {
        conservarTexto = eleccion;
    }

    private void verificarCambiosArchivo() {
        if (!areaTextoVacía()) {
            principal.getGuardarCambios().setVisible(true);

        }
    }

    public void nuevoArchivo() {
        verificarCambiosArchivo();
        if (conservarTexto) {
            guardarArchivo(principal.getPrincipalGUI().getAreaTexto().getText());
            pathDocumentoActual = "";
            principal.getPrincipalGUI().getAreaTexto().setText("");
        }
    }

//Se encarga de seleecionar el archivo y realizar el proceso correspondiente de extraccion de texto del mismo.
    public ArrayList<String> seleccionarArchivo() {
        verificarCambiosArchivo();

//        if (conservarTexto) {
//            if (!pathDocumentoActual.equals("")) {
//                guardarArchivo(principal.getPrincipalGUI().getAreaTexto().getText());
//            } else {
//                guardarArchivoComo();
//            }
//
//        }
        JFileChooser fileChosser = new JFileChooser();
        int seleccion = fileChosser.showOpenDialog(principal.getPrincipalGUI());

        if (seleccion == JFileChooser.APPROVE_OPTION) {
            //aqui selecciono y guardo el FILE que va a utilizar el FileReader
            File fichero = fileChosser.getSelectedFile();
            try {
                filasArchivo = principal.getLectorArchivos().leerFichero(fichero);
                principal.getPrincipalGUI().getAreaTexto().setText("");
                mostrarTextArea(principal.getPrincipalGUI().getAreaTexto());
                pathDocumentoActual = fichero.getAbsolutePath();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo");
            }
        }

        return filasArchivo;
    }
//Se encarga de guardar un archivo y reiniciar el area de texto, para volver a abrir el archivo

    public void guardarArchivo(String texto) {
//principal.getPrincipalGUI().getAreaTexto().getText()
        try {
            principal.getEscritorArchivos().guardarArchivoTexto(texto);
            JOptionPane.showMessageDialog(null, "ARCHIVO GUARDADO, BUSQUELO EN LA CARPETA: archivos");
        } catch (IOException ex) {
            Logger.getLogger(ControlPrincipalAL.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    //Se encarga de guardar un archivo y reiniciar el area de texto, para volver a abrir el archivo
    public void guardarArchivoComo() {

        try {
            principal.getEscritorArchivos().guardarArchivoTexto(principal.getPrincipalGUI().getAreaTexto().getText());
            JOptionPane.showMessageDialog(null, "ARCHIVO GUARDADO, SE NECESITA VOLVER A ABRIRLO");
            principal.getPrincipalGUI().getAreaTexto().setText("");
        } catch (IOException ex) {
            Logger.getLogger(ControlPrincipalAL.class.getName()).log(Level.SEVERE, null, ex);

        }

    }
//Se encarga de agregar al area de texto, todo el texto ordenado, correspondiente al texto de entrada

    public void mostrarTextArea(JTextArea areaTexto) {
        areaTexto.setText("");
        for (int i = 0; i < filasArchivo.size(); i++) {
            areaTexto.append(filasArchivo.get(i));
            areaTexto.append(System.getProperty("line.separator"));
        }
    }
//Se encarga de verificar todas las palabras que estan incluidas en las filas del archivo de texto cargado

    public void verificarTokens() {
        tokens = principal.getAnalizadorL().evaluarTextoTotal(obtenerFila(principal.getPrincipalGUI().getAreaTexto()));
    }

    private ArrayList<String> obtenerFila(JTextArea areaTexto) {
       ArrayList<String> temp = new ArrayList<>();
       String textoTemp = "";
       String textoTotal = areaTexto.getText();
        for (int i = 0; i < textoTotal.length(); i++) {
            if (textoTotal.charAt(i) != '\n') {
                textoTemp += textoTotal.charAt(i);
            }else if (textoTemp.length() != 0) {
                temp.add(textoTemp);
                textoTemp = "";
            }
        }
       return temp;
    }
//Se encarga de mostrar los reportes, si existe algun error o si el archivo esta libre de errores

    public void mostrarReportes() {
        verificarTokens();
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).getTipo().equals(TipoToken.ERROR)) {
                principal.getReportesGUI().getrErrores().setEnabled(true);
                principal.getReportesGUI().getrLexemas().setEnabled(false);
                principal.getReportesGUI().getrTokens().setEnabled(false);
                principal.getReportesGUI().getAnalisisSintactico().setEnabled(false);
                break;
            }
            principal.getReportesGUI().getrErrores().setEnabled(false);
            principal.getReportesGUI().getrLexemas().setEnabled(true);
            principal.getReportesGUI().getrTokens().setEnabled(true);
            principal.getReportesGUI().getAnalisisSintactico().setEnabled(true);
        }
        principal.getReportesGUI().setVisible(true);
    }
//Se encarga de abrir el reporte de errores del archivo seleccionado

    public void abrirReporteErrores() {

        TablaResultados tabla = new TablaResultados();
        DefaultTableModel modelo = new DefaultTableModel();
        tabla.getTabla().setModel(modelo);
        modelo.addColumn("CADENA DE ERROR");
        modelo.addColumn("FILA");
        modelo.addColumn("COLUMNA");

        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).getTipo().equals(TipoToken.ERROR)) {
                modelo.addRow(new Object[]{tokens.get(i).getValor(), tokens.get(i).getFila(), tokens.get(i).getColumna()});
            }
        }
        tabla.setVisible(true);
    }
//Se encarga de abrir el reporte de tokens del archivo seleccionado

    public void abrirReporteTokens() {
        TablaResultados tabla = new TablaResultados();
        DefaultTableModel modelo = new DefaultTableModel();
        tabla.getTabla().setModel(modelo);
        modelo.addColumn("TOKEN");
        modelo.addColumn("LEXEMA");
        modelo.addColumn("FILA");
        modelo.addColumn("COLUMNA");

        for (int i = 0; i < tokens.size(); i++) {
            if (!tokens.get(i).getTipo().equals(TipoToken.ERROR)) {
                modelo.addRow(new Object[]{tokens.get(i).getTipo().getNombre(), tokens.get(i).getValor(), tokens.get(i).getFila(), tokens.get(i).getColumna()});
            }
        }
        tabla.setVisible(true);

    }
//Se encarga de abrir el reporte de recuento de lexemas del archivo seleccionado

    public void abrirRecuentoLexemas() {
        ArrayList<Contable> contables = recontarTokens();
        TablaResultados tabla = new TablaResultados();
        DefaultTableModel modelo = new DefaultTableModel();
        tabla.getTabla().setModel(modelo);

        modelo.addColumn("LEXEMA");
        modelo.addColumn("TOKEN");
        modelo.addColumn("CANTIDAD");

        for (int i = 0; i < contables.size(); i++) {
            modelo.addRow(new Object[]{contables.get(i).getToken().getValor(), contables.get(i).getToken().getTipo().getNombre(), contables.get(i).getCantidad()});
        }
        tabla.setVisible(true);
    }
//Se encarta de realizar el conteo respectivo de los tokens, evaluando si existen errores

    public ArrayList<Contable> recontarTokens() {
        ArrayList<Token> tokensTemp = new ArrayList<>();
        ArrayList<Contable> contables = new ArrayList<>();
        boolean valorEncontrado = false;
        Token temp;
        Contable tempc;
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).getTipo() != TipoToken.ERROR) {
                tokensTemp.add(tokens.get(i));
            }
        }

        for (int i = 0; i < tokensTemp.size(); i++) {
            valorEncontrado = false;
            temp = tokensTemp.get(i);
            if (contables.size() != 0) {
                for (int j = 0; j < contables.size(); j++) {
                    tempc = contables.get(j);
                    if (temp.getValor().equals(tempc.getToken().getValor())) {
                        contables.get(j).sumarUnidadCantidad();
                        valorEncontrado = true;
                        break;
                    }
                }
                if (!valorEncontrado) {
                    contables.add(new Contable(tokensTemp.get(i)));
                }
            } else {
                contables.add(new Contable(tokensTemp.get(i)));
            }

        }
        burbuja(contables);
        return contables;
    }
//Se encarga de abrir el reporte de los movimientos de AFD optimo del archivo seleccionado

    public void abrirReporteAFD() {

        TablaResultados tabla = new TablaResultados();
        DefaultTableModel modelo = new DefaultTableModel();
        tabla.getTabla().setModel(modelo);
        modelo.addColumn("REPORTE AFD OPTIMO");
        for (int i = 0; i < principal.getAnalizadorL().getLector().getMovimientosTotales().size(); i++) {
            modelo.addRow(new Object[]{principal.getAnalizadorL().getLector().getMovimientosTotales().get(i).get(0)});
            for (int j = 1; j < principal.getAnalizadorL().getLector().getMovimientosTotales().get(i).size(); j++) {
                modelo.addRow(new Object[]{"        " + principal.getAnalizadorL().getLector().getMovimientosTotales().get(i).get(j)});
            }
            tabla.setVisible(true);
        }
    }
//Se encarga de abrir la busqueda de patrones del archivo seleccionado

    public void abrirBusquedaPatrones() {
        principal.getBusquedaGUI().setVisible(true);
    }
//Se encarga de buscar y resaltar los valores que se asemejan a un valor ingresado

    public void buscarPatron(JTextArea areaTexto, String texto) {
        if (texto.length() >= 1) {
            DefaultHighlighter.DefaultHighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
            Highlighter h = areaTexto.getHighlighter();
            h.removeAllHighlights();
            String text = areaTexto.getText();
            String caracteres = texto;
            Pattern p = Pattern.compile("(?i)" + caracteres);
            Matcher m = p.matcher(text);
            while (m.find()) {
                try {
                    h.addHighlight(m.start(), m.end(), highlightPainter);
                } catch (BadLocationException ex) {
                    Logger.getLogger(Color.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            JOptionPane.showMessageDialog(areaTexto, "la palabra a buscar no puede ser vacia");
        }
    }
//Se encarga de ordenar la lista de contables, de mayor a menor.

    public void burbuja(ArrayList<Contable> contables) {
        int i, j;
        Contable aux;
        for (i = 0; i < contables.size() - 1; i++) {
            for (j = 0; j < contables.size() - i - 1; j++) {
                //cambiar a <
                if (contables.get(j + 1).getCantidad() > contables.get(j).getCantidad()) {
                    aux = contables.get(j + 1);
                    contables.set(j + 1, contables.get(j));
                    contables.set(j, aux);
                }
            }
        }
    }

    public void analisisSintactico() {
        boolean estadoError = false;
        principal.getAnalizadorS().evaluarTokensTotales(tokens);
        ArrayList<Gramatica> gramaticas = principal.getAnalizadorS().evaluarTokensTotales(tokens);

        for (int i = 0; i < gramaticas.size(); i++) {

            if (gramaticas.get(i).getTipo().equals(TipoGramatica.ERROR)) {
                estadoError = true;
                break;
            }

//            System.out.println(gramaticas.get(i).getTipo().getNombre());
//            for (int j = 0; j < gramaticas.get(i).getTokensGramatica().size(); j++) {
//
//                System.out.println(gramaticas.get(i).getTokensGramatica().get(j).getValor());
//            }
//            System.out.println("SI FUNCIONA WEY :D");
//            System.out.println("");
        }

        if (estadoError) {
            TablaResultados tabla = new TablaResultados();
            DefaultTableModel modelo = new DefaultTableModel();
            tabla.getTabla().setModel(modelo);
            modelo.addColumn("TIPO GRAMATICA");
            modelo.addColumn("LEXEMA");
            modelo.addColumn("FILA");
            modelo.addColumn("COLUMNA");

            for (int i = 0; i < gramaticas.size(); i++) {

                if (gramaticas.get(i).getTipo().equals(TipoGramatica.ERROR)) {
                    modelo.addRow(new Object[]{gramaticas.get(i).getTipo().getNombre(),
                        unirValoresGramatica(gramaticas.get(i).getTokensGramatica()),
                        gramaticas.get(i).getTokensGramatica().get(gramaticas.get(i).getTokensGramatica().size() - 1).getFila(),
                        gramaticas.get(i).getTokensGramatica().get(gramaticas.get(i).getTokensGramatica().size() - 1).getColumna()});
                }
            }
            tabla.setVisible(true);
        } else {
            ArrayList<String> temp = modificarGramatica(gramaticas);
            String valorEscribir = "";
            for (int i = 0; i < temp.size(); i++) {
                valorEscribir += temp.get(i) + "\n";
            }
            guardarArchivo(valorEscribir);
        }

    }

    private String unirValoresGramatica(ArrayList<Token> lista) {
        String valor = "";
        for (int i = 0; i < lista.size(); i++) {
            valor += lista.get(i).getValor() + " ";
        }
        return valor;
    }

    private ArrayList<String> modificarGramatica(ArrayList<Gramatica> gramaticas) {
        ArrayList<String> temp = new ArrayList<>();

        for (int i = 0; i < gramaticas.size(); i++) {
            if (!gramaticas.get(i).getTipo().equals(TipoGramatica.ERROR)) {
                temp.add(verificarGramatica("", gramaticas.get(i)));
            }
        }

        return temp;
    }

    private String verificarGramatica(String dato, Gramatica gramatica) {
        switch (gramatica.getTipo()) {
            case ESCRITURA:
                for (int i = 0; i < gramatica.getTokensGramatica().size(); i++) {
                    if (i != 0 && i != gramatica.getTokensGramatica().size() - 1) {
                        if (gramatica.getTokensGramatica().get(i).getTipo().equals(TipoToken.LITERAL)) {
                            dato += gramatica.getTokensGramatica().get(i).getValor().replace(String.valueOf('"'), "");
                        } else if (gramatica.getTokensGramatica().get(i).getTipo().equals(TipoToken.NUMERO)) {
                            dato += gramatica.getTokensGramatica().get(i).getValor();
                        } else {
                            dato += gramatica.getTokensGramatica().get(i).getValor();
                        }
                    }
                }
                break;

            case REPETIR:
                for (int j = 0; j < Integer.parseInt(gramatica.getTokensGramatica().get(1).getValor()); j++) {
                    for (int i = 0; i < gramatica.getTokensGramatica().size(); i++) {

                        if (i != 0 && i != 1 && i != 2 && i != gramatica.getTokensGramatica().size() - 1) {

                            if (!gramatica.getTokensGramatica().get(i).getTipo().equals(TipoToken.PALABRAR)) {
                                if (gramatica.getTokensGramatica().get(i).getTipo().equals(TipoToken.LITERAL)) {
                                    dato += gramatica.getTokensGramatica().get(i).getValor().replace(String.valueOf('"'), "") + "\n";
                                } else if (gramatica.getTokensGramatica().get(i).getTipo().equals(TipoToken.NUMERO)) {
                                    dato += gramatica.getTokensGramatica().get(i).getValor() + "\n";
                                } else {
                                    dato += gramatica.getTokensGramatica().get(i).getValor() + "\n";
                                }
                            }
                        }
                    }

                }
                break;
            case CONDICIONAL:
                for (int i = 0; i < gramatica.getTokensGramatica().size(); i++) {
                    if (i != 0 && i != 1 && i != 2 && i != gramatica.getTokensGramatica().size() - 1) {
                        if (!gramatica.getTokensGramatica().get(i).getTipo().equals(TipoToken.PALABRAR) && gramatica.getTokensGramatica().get(1).getValor().equals("VERDADERO")) {
                            if (gramatica.getTokensGramatica().get(i).getTipo().equals(TipoToken.LITERAL)) {
                                dato += gramatica.getTokensGramatica().get(i).getValor().replace(String.valueOf('"'), "") + "\n";
                            } else if (gramatica.getTokensGramatica().get(i).getTipo().equals(TipoToken.NUMERO)) {
                                dato += gramatica.getTokensGramatica().get(i).getValor() + "\n";
                            } else {
                                dato += gramatica.getTokensGramatica().get(i).getValor() + "\n";
                            }
                        }
                    }
                }
                break;

            case ASIGNACIÓN:
                for (int i = 0; i < gramatica.getTokensGramatica().size(); i++) {
                    if (i != gramatica.getTokensGramatica().size() - 1) {

                        dato += gramatica.getTokensGramatica().get(i).getValor() + " ";

                    }
                }
                break;
        }
        return dato;
    }

}
