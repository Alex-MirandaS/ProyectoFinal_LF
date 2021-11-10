/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import Enums.TipoGramatica;
import Enums.TipoToken;
import Main.Principal;
import Objetos.Gramatica;
import Objetos.Texto;
import Objetos.Token;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author Alex
 */
public class LectorAnalizadorS {

    private String produccionInicial = "I";
    private String[][] tablaAnalisis;
    private ArrayList<Token> tokensLista = new ArrayList<>();
    private Stack pila = new Stack();

//Se encarga de iniciar el Lector, con el indice de un token y el tipo de gramatica posible, y el arreglo en donde se guardará el resultado
    public int iniciarLector(int indice, TipoGramatica tipo, ArrayList<Token> tokens, ArrayList<Gramatica> lista) {

        reiniciarLector(tipo);
        int contador = 0;
        if (tipo != TipoGramatica.ERROR) {

            for (contador = indice; contador < tokens.size(); contador++) {
                tokensLista.add(tokens.get(contador));
                automataPila(contador, tokens);

                if (pilaVacía() || ultimoValorPila() == "ERROR") {
                    break;
                }
            }

            if (pilaVacía()) {
                agregarNuevaGramatica(lista, tipo);
            } else {
                agregarNuevaGramatica(lista, TipoGramatica.ERROR);
            }
        } else {
            agregarNuevaGramatica(lista, TipoGramatica.ERROR);
        }

        return contador;
    }
//Es el encargado de realizar los movimientos respectivos del automata, siguiendo la tabla de analisis sintactico

    private void automataPila(int indice, ArrayList<Token> tokens) {
        boolean reduce = false;
        while (!reduce) {
            reduce = verificarTablaAnalisis(indice, "", tokens);
        }
    }

    private boolean verificarTablaAnalisis(int indice, String valorOp, ArrayList<Token> tokens) {
        boolean estadoError = false;
        String valor;
        int j;
        if (valorOp.equals("")) {
            valor = obtenerTipoToken(tokens.get(indice));
        } else {
            valor = valorOp;
        }

        String ultimaProduccion = ultimoValorPila();
        for (j = 0; j < tablaAnalisis.length; j++) {

            String produccionInicial = tablaAnalisis[j][0];
            String valorTabla = tablaAnalisis[j][1];
            String siguienteProduccion = tablaAnalisis[j][2];

            if (!ultimaProduccion.equals("ERROR")) {
                if (produccionInicial.equals(ultimaProduccion) && valor.equals(valorTabla)) {
                    if (valor.equals(siguienteProduccion)) {
                        pop();
                        if (valor.equals(ultimoValorPila())) {
                            pop();
                            if (!pilaVacía()) {
                                if (ultimoValorPila().equals("N")) {
                                    return verificarTablaAnalisis(indice, "$", tokens);
                                }
                            }
                        }
                        return true;
                    } else {
                        shift(siguienteProduccion);
                        if (valor.equals(ultimoValorPila())) {
                            pop();
                            if (!pilaVacía()) {
                                if (ultimoValorPila().equals("N")) {
                                    boolean xd = verificarTablaAnalisis(indice, "$", tokens);
                                    if (pila.size() > 2) {
                                        return true;
                                    }else{
                                        return xd;
                                    }
                                    
                                }
                            }

                            return true;
                        } else {
                            break;
                        }
                    }

                } else if (esPalabraReservadaExtra(tokens.get(indice)).equals(ultimoValorPila())) {
                    pop();
                    return true;
                }
            } else {
                return true;
            }
        }

        if (j == tablaAnalisis.length) {
            shift("ERROR");
            return true;
        }

        return false;
    }

    private String esPalabraReservadaExtra(Token actual) {

        if (actual.getTipo() == TipoToken.EXTRAS || actual.getTipo() == TipoToken.PALABRAR) {
            return actual.getValor();
        }else{
            return "";
        }
    }

    /*Es el encargado de hacer un shift en la pila, realizando un pop al inicio y si no es un epsilon, sustituye el valor
  eliminado, con las nuevas producciones correspondientes, agregadas de forma inversa  */
    private void shift(String siguienteProduccion) {
        pop();
        if (!siguienteProduccion.equals("Ɛ")) {
            push(darVuelta(siguienteProduccion));
        }
    }
//Se encarga de borrar el ultimo valor de la pila

    private void pop() {
        pila.pop();
    }

    private void push(ArrayList<String> temp) {
        for (int i = 0; i < temp.size(); i++) {
            pila.push(temp.get(i));
        }
    }

    private boolean verificarProducciones(String valor, ArrayList<Token> tokens) {
        if (valor.equals(ultimoValorPila())) {
            pop();

            if (!pilaVacía()) {
                if (ultimoValorPila().equals("N")) {
                    //return verificarTablaAnalisis("$", tokens);
                }
            }
        }
        return false;
    }

    /*Es el encargado de invertir los valores de las producciones siguientes de una transicion en la tabla de analisis
    sintactico*/
    private ArrayList<String> darVuelta(String produccion) {
        Stack temp = new Stack();
        ArrayList<String> tempProducciones = new ArrayList<>();
        String texto = "";
        int j = 0;
        for (int i = 0; i < produccion.length(); i++) {
            if (produccion.charAt(i) != ' ') {
                texto += produccion.charAt(i);
            } else if (texto.length() != 0) {
                temp.push(texto);
                texto = "";
            }
        }

        if (texto.length() != 0) {
            temp.push(texto);
            texto = "";
        }

        while (!temp.isEmpty()) {
            tempProducciones.add((String) temp.pop());
        }
        return tempProducciones;
    }
//Evalua un tipo de Token y devuelve el valor corresondiente

    private String obtenerTipoToken(Token actual) {

        if (actual.getTipo() == TipoToken.EXTRAS || actual.getTipo() == TipoToken.PALABRAR) {
//            if (actual.getValor().equals("EXPRESION") || actual.getValor().equals("ESCRITURA")) {
//                LectorAnalizadorS temp = new LectorAnalizadorS();
//                TipoGramatica tempTipoGramatica = null;
//                if (actual.getValor().equals("EXPRESION")) {
//                    tempTipoGramatica = TipoGramatica.EXPRESION;
//                } else {
//                    tempTipoGramatica = TipoGramatica.ESCRITURA;
//                }
//
//                contador = temp.iniciarLector(contador, tempTipoGramatica, tokensLista, listaGramaticas);
//                if (temp.getPila().empty()) {
//                    return actual.getValor();
//                } else {
//                    return "ERROR";
//                }
//            } else {
            return actual.getValor();
            //}

        } else {
            return actual.getTipo().getNombre();
        }

    }

    /*Se encarga de otorgarle los valores al lector, es este caso, la tabla de analisis correspondiente, 
    dependiendo del tipo de gramatica de la cual se va a evaluar*/
    private void definirTablaAnalisis(TipoGramatica tipo) {
        switch (tipo) {
            case ESCRITURA:
                this.tablaAnalisis = InformacionGramaticas.tablaAnalisisEscritura;
                break;
            case REPETIR:
                this.tablaAnalisis = InformacionGramaticas.tablaAnalisisRepetir;
                break;
            case CONDICIONAL:
                this.tablaAnalisis = InformacionGramaticas.tablaAnalisisCondicional;
                break;
            case EXPRESION:
                this.tablaAnalisis = InformacionGramaticas.tablaAnalisisExpresion;
                break;
            case ASIGNACIÓN:
                this.tablaAnalisis = InformacionGramaticas.tablaAnalisisAsignacion;
                break;
        }

    }

//Es el encargado de reiniciar el lector, segun el tipo de gramatica nueva que se evaluara
    private void reiniciarLector(TipoGramatica tipo) {
        definirTablaAnalisis(tipo);
        tokensLista = new ArrayList<>();
        pila.add("$");
        pila.add(produccionInicial);
    }

    //Agrega una gramatica ya sea valida o erronea a una lista de gramaticas
    private void agregarNuevaGramatica(ArrayList<Gramatica> lista, TipoGramatica tipo) {
        lista.add(new Gramatica(tokensLista, tipo));
    }
//Verifica que la pila este vacia

    private boolean pilaVacía() {
        return pila.empty();
    }
//Retorna el ultimo valor de la pila

    private String ultimoValorPila() {
        return (String) pila.peek();
    }

    public Stack getPila() {
        return pila;
    }

}
