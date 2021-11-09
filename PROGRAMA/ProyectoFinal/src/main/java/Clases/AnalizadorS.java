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

/**
 *
 * @author Alex
 */
public class AnalizadorS {

    private LectorAnalizadorS lector = new LectorAnalizadorS();

    //Se encarga de realizar las correcciones al texto y evaluarlo, fila por fila
    public ArrayList<Gramatica> evaluarTokensTotales(ArrayList<Token> tokens) {
        ArrayList<Gramatica> gramaticas = new ArrayList<>();
        if (true) {//verificar que no haya tokens de error
            gramaticas = verificar(tokens);
        }
        return gramaticas;
    }

//Se encarga de verificar cada una de las palabras de la lista, para identificar a que tipo de token podrian pertenecer
    private ArrayList<Gramatica> verificar(ArrayList<Token> tokens) {
        ArrayList<Gramatica> temp = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            i = verificarTipoGramatica(i, tokens, temp);
        }
        return temp;
    }

//Se encarga de clasificar el token al cual podria pertenecer una palabra, segun su caracter inicial
    private int verificarTipoGramatica(int indice, ArrayList<Token> tokens, ArrayList<Gramatica> lista) {

        TipoGramatica tipoGramatica = null;
        if (evaluarTipoToken(tokens.get(indice), InformacionGramaticas.inicialesEscritura)) {
            tipoGramatica = TipoGramatica.ESCRITURA;
        } else if (evaluarTipoToken(tokens.get(indice), InformacionGramaticas.inicialesRepetir)) {
            tipoGramatica = TipoGramatica.REPETIR;
        } else if (evaluarTipoToken(tokens.get(indice), InformacionGramaticas.inicialesCondicional)) {
            tipoGramatica = TipoGramatica.CONDICIONAL;
        } else if (evaluarTipoToken(tokens.get(indice), InformacionGramaticas.inicialesAsignacion)) {
            tipoGramatica = TipoGramatica.ASIGNACIÓN;
        } else {
            tipoGramatica = TipoGramatica.ERROR;
        }
        return evaluarGramatica(indice, tipoGramatica, tokens, lista);
    }

    //Se encarga de evaluar un texto, tomandolo como un posible token especifico
    private int evaluarGramatica(int indice, TipoGramatica tipo, ArrayList<Token> tokens, ArrayList<Gramatica> lista) {
        int nuevoIndice = lector.iniciarLector(indice, tipo, tokens, lista);
//        if (lista.get(lista.size()-1).getTipo() == TipoGramatica.ERROR && tipo.equals(TipoGramatica.EXPRESION)) {
//            lista.remove(lista.size()-1);
//            nuevoIndice = evaluarGramatica(indice, TipoGramatica.ASIGNACIÓN, tokens, lista);
//        }
        //ver errores
        return nuevoIndice;
    }

//Se encarga de verificar si un char en espeficico es parte del abecedario aceptado por el automata
    private boolean evaluarTipoToken(Token token, String[] datos) {
        String tipoValor = "";
        if (token.getTipo().equals(TipoToken.PALABRAR) || token.getTipo().equals(TipoToken.EXTRAS)) {
            tipoValor = token.getValor();
        } else {
            tipoValor = token.getTipo().getNombre();
        }

        for (int i = 0; i < datos.length; i++) {
            if (tipoValor.equals(datos[i])) {
                return true;
            }
        }
        return false;
    }
//Se encarga de crear un nuevo texto con el residuo de uno fallido, ya que esto permite la recuperación de errores

    private Texto redimensionarTexto(Texto texto, int cCaracteres, boolean iniciarPrincipio) {
        Texto textoTemp = null;
        char[] caracteres = texto.getValor().toCharArray();
        String nuevoTexto = "";
        if (iniciarPrincipio) {
            for (int i = cCaracteres; i < texto.getValor().length(); i++) {
                nuevoTexto += caracteres[i];
            }
        } else {
            for (int i = 0; i < texto.getValor().length() - cCaracteres; i++) {
                nuevoTexto += caracteres[i];
            }
        }

        textoTemp = new Texto(nuevoTexto, texto.getFila(), texto.getColumna());
        return textoTemp;
    }

    /*Es el encargado de verficicar si es necesario seguir leyendo un texto en espeficico, solamente si el mismo contiene 
  residuos de texto  */
//    private void darSeguimiento(int cCaracteres, Texto texto, ArrayList<Token> lista) {
//        if (cCaracteres != texto.getValor().length()) {
//            verificarTipoToken(redimensionarTexto(texto, cCaracteres, true), lista);
//        }
//    }
    private ArrayList<Texto> modificaciones(ArrayList<Texto> temporal) {
        ArrayList<Texto> textos = new ArrayList<>();
        TipoToken tipoTemp = null;
        String valorTemp = "";
        int i;
        for (i = 0; i < temporal.size(); i++) {

            if (tipoTemp == null) {
                String borrar = temporal.get(i).getValor();
                if (temporal.get(i).getValor().charAt(0) == '"') {
                    tipoTemp = TipoToken.LITERAL;
                } else if (temporal.get(i).getValor().charAt(0) == '/' && temporal.get(i).getValor().charAt(1) == '/') {
                    tipoTemp = TipoToken.COMENTARIO;
                } else if (temporal.get(i).getValor().charAt(0) == '(' || temporal.get(i).getValor().charAt(temporal.get(i).getValor().length() - 1) == ')') {
                    tipoTemp = TipoToken.EXTRAS;
                } else {
                    tipoTemp = TipoToken.ERROR;
                }
                i--;
            } else {
                switch (tipoTemp) {
                    case LITERAL:
                        if (temporal.get(i).getValor().charAt(temporal.get(i).getValor().length() - 1) != '"') {
                            valorTemp += temporal.get(i).getValor() + añadirEspacios(temporal.get(i), temporal.get(i + 1));
                        } else {
                            valorTemp += temporal.get(i).getValor();
                        }

                        if (valorTemp.charAt(0) == '"' && valorTemp.charAt(valorTemp.length() - 1) == '"') {
                            textos.add(new Texto(valorTemp, temporal.get(i).getFila(), temporal.get(i).getColumna()));
                            valorTemp = "";
                            tipoTemp = null;
                        }
                        break;
                    case COMENTARIO:
                        if (i + 1 < temporal.size()) {
                            valorTemp += temporal.get(i).getValor() + añadirEspacios(temporal.get(i), temporal.get(i + 1));
                        } else {
                            valorTemp += temporal.get(i).getValor();
                        }
                        break;
                    case EXTRAS:

                        if (temporal.get(i).getValor().charAt(0) == '(') {
                            for (int k = 0; k < temporal.get(i).getValor().length(); k++) {
                                if (temporal.get(i).getValor().charAt(k) != '(') {
                                    valorTemp += temporal.get(i).getValor().charAt(k);
                                } else {
                                    textos.add(new Texto(String.valueOf(temporal.get(i).getValor().charAt(k)), temporal.get(i).getFila(), temporal.get(i).getColumna() - (temporal.get(i).getValor().length() - (k + 1))));
                                }
                            }
                        } else if (temporal.get(i).getValor().charAt(temporal.get(i).getValor().length() - 1) == ')') {
                            for (int k = 0; k < temporal.get(i).getValor().length(); k++) {
                                if (temporal.get(i).getValor().charAt(k) != ')') {
                                    valorTemp += temporal.get(i).getValor().charAt(k);
                                } else {
                                    if (valorTemp.length() != 0) {
                                        textos.add(new Texto(valorTemp, temporal.get(i).getFila(), temporal.get(i).getColumna()));
                                        valorTemp = "";

                                    }

                                    textos.add(new Texto(String.valueOf(temporal.get(i).getValor().charAt(k)), temporal.get(i).getFila(), temporal.get(i).getColumna() - (temporal.get(i).getValor().length() - (k + 1))));
                                }
                            }
                        }
                        tipoTemp = null;
                        break;

                    default:
                        textos.add(temporal.get(i));
                        tipoTemp = null;
                        break;
                }
            }

            if (valorTemp.length() != 0 && tipoTemp != TipoToken.LITERAL) {
                textos.add(new Texto(valorTemp, temporal.get(i).getFila(), temporal.get(i).getColumna()));
                valorTemp = "";
                tipoTemp = null;
            }

        }
        return textos;
    }

//    public LectorAnalizadorL getLector() {
//        return lector;
//    }
    private String añadirEspacios(Texto temp1, Texto temp2) {
        String espacios = "";
        if (temp1 != null && temp2 != null) {
            if (temp1.getValor().charAt(temp1.getValor().length() - 1) != '”') {
                for (int i = 0; i < ((temp2.getColumna() - temp2.getValor().length()) - temp1.getColumna()); i++) {
                    espacios += " ";
                }
            }

        }
        return espacios;
    }

}
