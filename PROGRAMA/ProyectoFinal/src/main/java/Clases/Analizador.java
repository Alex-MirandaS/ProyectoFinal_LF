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
public class Analizador {

    private Lector lector = new Lector();

    //Se encarga de realizar las correcciones al texto y evaluarlo, fila por fila
    public ArrayList<Token> evaluarTextoTotal(ArrayList<String> filas) {
        ArrayList<Token> tokens = new ArrayList<>();
        ArrayList<Token> temp = null;
        for (int i = 0; i < filas.size(); i++) {
            temp = evaluarFilaTexto(filas.get(i), i + 1);
            for (int j = 0; j < temp.size(); j++) {
                tokens.add(temp.get(j));
            }
        }
        return tokens;
    }

    //Se encarga de evaluar el texto de una fila
    public ArrayList<Token> evaluarFilaTexto(String textoNormal, int fila) {
        ArrayList<Texto> textoSeparado = separar(textoNormal, fila);
        return verificar(textoSeparado);
    }

    /*Se encarga de separar el bloque de texto en una lista de palabras, 
    las cuales son determinadas por un salto de linea o un espacio*/
    public ArrayList<Texto> separar(String textoFila, int fila) {
        ArrayList<Texto> temporal = new ArrayList<>();
        String textoTemp = "";
        int i;
        for (i = 0; i < textoFila.length(); i++) {

            if (textoFila.charAt(i) != ' ') {
                textoTemp += textoFila.charAt(i);
            } else if (textoFila.charAt(i) == ' ' && textoTemp.length() != 0) {
                temporal.add(new Texto(textoTemp, fila, i));
                textoTemp = "";
            }
        }
        if (textoTemp.length() != 0) {
            temporal.add(new Texto(textoTemp, fila, i));
        }

        return modificaciones(temporal);
    }

//Se encarga de verificar cada una de las palabras de la lista, para identificar a que tipo de token podrian pertenecer
    private ArrayList<Token> verificar(ArrayList<Texto> textoSeparado) {
        ArrayList<Token> temp = new ArrayList<>();
        for (int i = 0; i < textoSeparado.size(); i++) {
            verificarTipoToken(textoSeparado.get(i), temp);
        }
        return temp;
    }

//Se encarga de clasificar el token al cual podria pertenecer una palabra, segun su caracter inicial
    private void verificarTipoToken(Texto texto, ArrayList<Token> lista) {
        char caracterInicial = texto.getValor().charAt(0);
        TipoToken tipo = null;
        if (evaluarCHAR(caracterInicial, InformaciónTokens.inicialesIdentificador)) {
            tipo = TipoToken.IDENTIFICADOR;
        } else if (evaluarCHAR(caracterInicial, InformaciónTokens.inicialesNumero)) {
            tipo = TipoToken.NUMERO;
        } else if (evaluarCHAR(caracterInicial, InformaciónTokens.inicialesLiteral)) {
            tipo = TipoToken.LITERAL;
        } else if (evaluarCHAR(caracterInicial, InformaciónTokens.inicialesComentario)) {//&& evaluarCHAR(texto.getValor().charAt(1), InformaciónTokens.inicialesComentario)
            tipo = TipoToken.COMENTARIO;
        } else {
            tipo = TipoToken.ERROR;
        }
        evaluarToken(tipo, texto, lista);
    }

    //Se encarga de evaluar un texto, tomandolo como un posible token especifico
    private void evaluarToken(TipoToken tipoToken, Texto texto, ArrayList<Token> lista) {
        int cCaracteres = lector.iniciarLector(texto, tipoToken, lista);
        if (tipoToken != tipoToken.ERROR) {
            darSeguimiento(cCaracteres, texto, lista);
        }
    }

//Se encarga de verificar si un char en espeficico es parte del abecedario aceptado por el automata
    private boolean evaluarCHAR(char charEvaluado, String[][] datos) {
        for (int i = 0; i < datos.length; i++) {
            for (int j = 0; j < datos[i].length; j++) {
                if (String.valueOf(charEvaluado).equalsIgnoreCase(datos[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }
//Se encarga de crear un nuevo texto con el residuo de uno fallido, ya que esto permite la recuperación de errores

    private Texto redimensionarTexto(Texto texto, int cCaracteres) {
        Texto textoTemp = null;
        char[] caracteres = texto.getValor().toCharArray();
        String nuevoTexto = "";
        for (int i = cCaracteres; i < texto.getValor().length(); i++) {
            nuevoTexto += caracteres[i];
        }
        textoTemp = new Texto(nuevoTexto, texto.getFila(), texto.getColumna());
        return textoTemp;
    }

    /*Es el encargado de verficicar si es necesario seguir leyendo un texto en espeficico, solamente si el mismo contiene 
  residuos de texto  */
    private void darSeguimiento(int cCaracteres, Texto texto, ArrayList<Token> lista) {
        if (cCaracteres != texto.getValor().length()) {
            verificarTipoToken(redimensionarTexto(texto, cCaracteres), lista);
        }
    }

    private ArrayList<Texto> modificaciones(ArrayList<Texto> temporal) {
        ArrayList<Texto> textos = new ArrayList<>();
        TipoToken tipoTemp = null;
        String valorTemp = "";
        Texto temp;
        char borrar;
        int i;
        for (i = 0; i < temporal.size(); i++) {
            
            if (tipoTemp == null) {
                if (temporal.get(i).getValor().charAt(0) == '“') {
                    tipoTemp = TipoToken.LITERAL;
                    i--;
                }else if (temporal.get(i).getValor().charAt(0) == '/' && temporal.get(i).getValor().charAt(1) == '/') {
                    tipoTemp = TipoToken.COMENTARIO;
                    i--;
                }
                
            }else{
                switch(tipoTemp){
                    case LITERAL:
                        if (temporal.get(i).getValor().charAt(temporal.get(i).getValor().length()-1) != '”') {
                            valorTemp += temporal.get(i).getValor() + añadirEspacios(temporal.get(i), temporal.get(i + 1));
                        }else{
                            valorTemp += temporal.get(i).getValor();
                        }
                        
                        if (valorTemp.charAt(0) == '“' && valorTemp.charAt(valorTemp.length()-1) == '”') {
                            textos.add(new Texto(valorTemp, temporal.get(i).getFila(), temporal.get(i).getColumna()));
                            valorTemp = "";
                            tipoTemp = null;
                        }
                        break;
                    case COMENTARIO:
                        if (i+1 < temporal.size()) {
                            valorTemp += temporal.get(i).getValor() + añadirEspacios(temporal.get(i), temporal.get(i+1));
                        }else{
                            valorTemp += temporal.get(i).getValor();
                        }
                        
                            
                        break;
                    default:
                          textos.add(temporal.get(i));
                        break;
                }
            }
            
            if(valorTemp.length() != 0 && tipoTemp != TipoToken.LITERAL){
                textos.add(new Texto(valorTemp, temporal.get(i).getFila(), temporal.get(i).getColumna()));
            }
           
        }
        return textos;
    }

    public Lector getLector() {
        return lector;
    }

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
