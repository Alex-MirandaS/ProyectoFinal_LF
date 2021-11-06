/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import Enums.TipoToken;
import Main.Principal;
import Objetos.Texto;
import Objetos.Token;
import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class LectorAnalizadorL {

    private String estadoActual = "S0";
    private String cadena = "";
    private TipoToken tipo;
    private String[] estadosAceptacion;
    private String[][] funcionDeTransicion;
    private ArrayList<String> movimientos;
    private ArrayList<ArrayList<String>> movimientosTotales = new ArrayList<>();

//Se encarga de iniciar el Lector, con un texto y el tipo de token posible, y el arreglo en donde se guardará el resultado
    public int iniciarLector(Texto texto, TipoToken tipo, ArrayList<Token> tokens) {

        reiniciarLector(tipo);
        int columnaInicio = texto.getColumna() - texto.getValor().length();
        if (tipo != TipoToken.ERROR) {
            añadirPalabraRegistro(texto.getValor());
            for (int i = 0; i < texto.getValor().length(); i++) {
                char actual = texto.getValor().charAt(i);
                verificarTokenValido(actual);
                columnaInicio++;
                if (estadoActual == "ERROR") {
                    break;
                }
            }
            guardarMovimientosTotales();
            if (estadoActual != "ERROR" && verificarEstadosAceptacion()) {
                tokens.add(new Token(cadena, texto.getFila(), texto.getColumna(), tipo));
            } else {
                tokens.add(new Token(cadena, texto.getFila(), columnaInicio, TipoToken.ERROR));
            }
        } else {
            tokens.add(new Token(texto.getValor(), texto.getFila(), texto.getColumna(), TipoToken.ERROR));
        }

        return cadena.length();
    }
//Es el encargado de realizar los movimientos respectivos del automata, siguiendo las transiciones

    private void verificarTokenValido(char actual) {
        boolean estadoError = false;
        for (int j = 0; j < funcionDeTransicion.length; j++) {
            char tipoToken = obtenerTipoCaracter(actual);
            String primeraPosicion = funcionDeTransicion[j][0];
            String valor = funcionDeTransicion[j][1];
            String siguiente = funcionDeTransicion[j][2];
            if (estadoActual != "ERROR" && tipoToken != 'E') {
                if (primeraPosicion.equalsIgnoreCase(estadoActual) && valor.equals(String.valueOf(tipoToken))) {
                    añadirMovimientoRegistro(siguiente, actual);
                    estadoActual = siguiente;
                    cadena += actual;
                    break;
                }
            } else {
                estadoError = true;
                break;
            }

        }
        if (estadoError != false) {
            cadena += actual;
            estadoActual = "ERROR";

        }
    }
//Evalua un caracter y devuelve el tipo de caracter al que pertenece segun el alfabeto

    private char obtenerTipoCaracter(char actual) {

        switch (tipo) {
            case NUMERO:
                if (String.valueOf(actual).equalsIgnoreCase("0")) {
                    return '0';
                } else if (String.valueOf(actual).equalsIgnoreCase("-")) {
                    return '-';
                } else if (evaluarCHAR(actual, InformaciónTokens.alfabetoNumero)) {
                    return 'D';
                }
                break;
            case IDENTIFICADOR:
                if (String.valueOf(actual).equalsIgnoreCase("_")) {
                    return '_';
                } else if (String.valueOf(actual).equalsIgnoreCase("-")) {
                    return '-';
                } else if (evaluarCHAR(actual, InformaciónTokens.alfabetoNumero)) {
                    return 'D';
                } else if (evaluarCHAR(actual, InformaciónTokens.alfabetoLetras)) {
                    return 'L';
                }
                break;
            case LITERAL:
                if (String.valueOf(actual).equalsIgnoreCase(String.valueOf('"'))) {
                    return '"';
                }
                else if (String.valueOf(actual).equalsIgnoreCase("“")) {
                    return '“';
                } else if (String.valueOf(actual).equalsIgnoreCase("”")) {
                    return '”';

                } else if (evaluarCHAR(actual, InformaciónTokens.alfabetoLetras)) {
                    return 'L';

                } else if (evaluarCHAR(actual, InformaciónTokens.alfabetoCaracteres)
                        || evaluarCHAR(actual, InformaciónTokens.alfabetoSignosAgrupacion)
                        || evaluarCHAR(actual, InformaciónTokens.alfabetoNumero)
                        || evaluarCHAR(actual, InformaciónTokens.alfabetoSignosOperacion)
                        || evaluarCHAR(actual, InformaciónTokens.alfabetoSignosPuntuacion)) {
                    return 'C';
                }
                break;
            case COMENTARIO:
                if (String.valueOf(actual).equalsIgnoreCase("/")) {
                    return '/';
                } else if (evaluarCHAR(actual, InformaciónTokens.alfabetoLetras)) {
                    return 'L';
                } else if (evaluarCHAR(actual, InformaciónTokens.alfabetoCaracteres)
                        || evaluarCHAR(actual, InformaciónTokens.alfabetoSignosAgrupacion)
                        || evaluarCHAR(actual, InformaciónTokens.alfabetoSignosOperacion)
                        || evaluarCHAR(actual, InformaciónTokens.alfabetoSignosPuntuacion)) {
                    return 'C';
                }
                break;
            case PALABRAR:
                if (evaluarCHAR(actual, InformaciónTokens.alfabetoLetras)) {
                    return actual;
                }
                break;
            case EXTRAS:
                if (evaluarCHAR(actual, InformaciónTokens.inicialesExtras[0])) {
                    return actual;
                }
                break;
        }
        return 'E';
    }

    /*Se encarga de otorgarle los valores al lector, tanto los estados de aceptación y la funcion de transicion
  dependiendo del tipo del token del cual se va a evaluar*/
    private void definirEstadosYFuncion(TipoToken tipo) {
        switch (tipo) {
            case IDENTIFICADOR:
                this.estadosAceptacion = InformaciónTokens.estadosAceptacionIdentificador;
                this.funcionDeTransicion = InformaciónTokens.funcionTransicionIdentificador;
                break;
            case NUMERO:
                this.estadosAceptacion = InformaciónTokens.estadosAceptacionNumero;
                this.funcionDeTransicion = InformaciónTokens.funcionTransicionNumero;
                break;
            case LITERAL:
                this.estadosAceptacion = InformaciónTokens.estadosAceptacionLiteral;
                this.funcionDeTransicion = InformaciónTokens.funcionTransicionLiteral;
                break;
            case COMENTARIO:
                this.estadosAceptacion = InformaciónTokens.estadosAceptacionComentario;
                this.funcionDeTransicion = InformaciónTokens.funcionTransicionComentario;
                break;
            case PALABRAR:
                this.estadosAceptacion = InformaciónTokens.estadosAceptacionPR;
                this.funcionDeTransicion = InformaciónTokens.funcionTransicionPR;
                break;
            case EXTRAS:
                this.estadosAceptacion = InformaciónTokens.estadosAceptacionExtras;
                this.funcionDeTransicion = InformaciónTokens.funcionTransicionExtras;
                break;
        }

    }
//Devuelve una verificación si el char enviado existe dentro de un alfabeto en especifico

    private boolean evaluarCHAR(char charEvaluado, String[] datos) {
        for (int i = 0; i < datos.length; i++) {
            if (String.valueOf(charEvaluado).equalsIgnoreCase(datos[i])) {
                return true;
            }
        }
        return false;
    }
//Verifica si el estado actual corresponde a un estado de aceptación

    private boolean verificarEstadosAceptacion() {
        for (int i = 0; i < estadosAceptacion.length; i++) {
            if (estadoActual.equalsIgnoreCase(estadosAceptacion[i])) {
                return true;
            }
        }
        return false;
    }
//Es el encargado de reiniciar el lector, segun el tipo de token nuevo que se evaluara

    private void reiniciarLector(TipoToken tipo) {
        this.tipo = tipo;
        definirEstadosYFuncion(tipo);
        estadoActual = "S0";
        cadena = "";
    }
//Se encarga de agregar una nueva palabra al registro de AFD optimo

    private void añadirPalabraRegistro(String token) {
        movimientos = new ArrayList<>();
        movimientos.add(token);
    }
//Se encarga de agregar un nuevo movimiento al registro de AFD optimo

    private void añadirMovimientoRegistro(String estado, char valor) {
        movimientos.add("Me moví del estado " + estadoActual + " al estado " + estado + " con una " + valor);
    }
//Se encarga de guardar y agregar el registro total de una palabra a una lista con todos los registros de todos los lexemas

    private void guardarMovimientosTotales() {
        movimientosTotales.add(movimientos);
    }

    public ArrayList<ArrayList<String>> getMovimientosTotales() {
        return movimientosTotales;
    }

}
