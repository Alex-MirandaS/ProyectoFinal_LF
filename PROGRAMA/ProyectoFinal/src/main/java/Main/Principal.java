/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Archivos.EscritorArchivosTexto;
import Archivos.LectorArchivosEnTexto;
import Clases.AnalizadorL;
import Clases.AnalizadorS;
import Controladores.ControlPrincipalAL;
import GUI.BusquedaGUI;
import GUI.GuardarCambiosGUI;
import GUI.PrincipalGUI;
import GUI.ReportesGUI;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Alex
 */
public class Principal {

    //GUI
    private PrincipalGUI principalGUI = new PrincipalGUI(this);
    private ReportesGUI reportesGUI = new ReportesGUI(this);
    private BusquedaGUI busquedaGUI;
    private GuardarCambiosGUI guardarCambios = new GuardarCambiosGUI(this);
    //CLASES
    private LectorArchivosEnTexto lectorArchivos = new LectorArchivosEnTexto();
    private EscritorArchivosTexto escritorArchivos = new EscritorArchivosTexto();
    private AnalizadorL analizadorL = new AnalizadorL();
    private AnalizadorS analizadorS = new AnalizadorS();
    private ControlPrincipalAL controlPrincipal = new ControlPrincipalAL(this);

    public void inicio() {
        principalGUI.setExtendedState(JFrame.MAXIMIZED_BOTH);
        principalGUI.setVisible(true);
    }

    public void nuevoArchivo() {
        controlPrincipal.nuevoArchivo();
    }

    public void cargarArchivo() {
        controlPrincipal.seleccionarArchivo();

    }

    public void guardarArchivo() {
        controlPrincipal.guardarArchivo(principalGUI.getAreaTexto().getText());
    }

    public void reportes() {
        controlPrincipal.mostrarReportes();
    }

    public void reporteErrores() {
        controlPrincipal.abrirReporteErrores();
    }

    public void reporteTokens() {
        controlPrincipal.abrirReporteTokens();
    }

    public void recuentoLexemas() {
        controlPrincipal.abrirRecuentoLexemas();
    }

    public void reporteAFD() {
        controlPrincipal.abrirReporteAFD();
    }

    public void analisisSintactico() {
        controlPrincipal.analisisSintactico();
    }

    public void busquedaPatrones() {
        busquedaGUI = new BusquedaGUI(this);
        busquedaGUI.setVisible(true);
    }

    public PrincipalGUI getPrincipalGUI() {
        return principalGUI;
    }

    public LectorArchivosEnTexto getLectorArchivos() {
        return lectorArchivos;
    }

    public EscritorArchivosTexto getEscritorArchivos() {
        return escritorArchivos;
    }

    public AnalizadorL getAnalizadorL() {
        return analizadorL;
    }
    
    public AnalizadorS getAnalizadorS() {
        return analizadorS;
    }

    public ControlPrincipalAL getControlPrincipal() {
        return controlPrincipal;
    }

    public ReportesGUI getReportesGUI() {
        return reportesGUI;
    }

    public BusquedaGUI getBusquedaGUI() {
        return busquedaGUI;
    }

    public GuardarCambiosGUI getGuardarCambios() {
        return guardarCambios;
    }

}
