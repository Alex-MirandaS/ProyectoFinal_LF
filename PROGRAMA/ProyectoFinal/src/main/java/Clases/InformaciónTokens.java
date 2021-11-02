/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Alex
 */
public class InformaciónTokens {

    /*DATOS DE LOS TOKENS
    Se incluyen los alfabetos, estados de aceptación y función de transición correspondiente a cada uno de los tipos de token disponibles
    L = Letra
    D = Numero
    C = Caracter
    
    
     */
    public static final String[] alfabetoLetras = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
        "t", "u", "v", "w", "x", "y", "z"};
    public static final String[] alfabetoNumero = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    public static final String[] alfabetoSignosPuntuacion = {".", ",", ";", ":"};
    public static final String[] alfabetoSignosOperacion = {"+", "-", "*", "/", "%"};
    public static final String[] alfabetoSignosAgrupacion = {"(", ")", "[", "]", "{", "}", "“", "”", "'", "<", ">"};
    public static final String[] alfabetoCaracteres = {"_", "="};
    //VALORES INICIALES
    //IDENTIFICADOR
    public static final String[][] inicialesIdentificador = {alfabetoLetras, alfabetoCaracteres};
    public static final String[][] inicialesNumero = {alfabetoNumero, {"-"}};
    public static final String[][] inicialesLiteral = {{"“"}};
    public static final String[][] inicialesComentario = {{"/"}};

    //IDENTIFICADOR
    public static final String[] estadosAceptacionIdentificador = {"S1"};
    public static final String[][] funcionTransicionIdentificador
            = {{"S0", "_", "S1"}, {"S0", "L", "S1"}, {"S0", "D", "ERROR"}, {"S0", "-", "ERROR"},
            {"S1", "_", "S1"}, {"S1", "L", "S1"}, {"S1", "D", "S1"}, {"S1", "-", "S1"}
            };

    //NUMERO
    public static final String[] estadosAceptacionNumero = {"S1", "S2"};
    public static final String[][] funcionTransicionNumero
            = {{"S0", "0", "S1"}, {"S0", "D", "S2"}, {"S0", "-", "S3"},
            {"S1", "0", "ERROR"}, {"S1", "D", "ERROR"}, {"S1", "-", "ERROR"},
            {"S2", "0", "S2"}, {"S2", "D", "S2"}, {"S2", "-", "ERROR"},
            {"S3", "0", "ERROR"}, {"S3", "D", "S2"}, {"S3", "-", "ERROR"}
            };
    //LITERAL
    public static final String[] estadosAceptacionLiteral = {"S2"};
    public static final String[][] funcionTransicionLiteral
            = {{"S0", "“", "S1"}, {"S0", "L", "ERROR"}, {"S0", "C", "ERROR"}, {"S0", "”", "ERROR"},
            {"S1", "“", "ERROR"}, {"S1", "L", "S1"}, {"S1", "C", "S1"}, {"S1", "”", "S2"},
            {"S2", "“", "ERROR"}, {"S2", "L", "ERROR"}, {"S2", "C", "ERROR"}, {"S2", "”", "ERROR"}
            };

    //COMENTARIO
    public static final String[] estadosAceptacionComentario = {"S3"};
    public static final String[][] funcionTransicionComentario
            //PENDIENTE
            = {{"S0", "/", "S1"}, {"S0", "L", "ERROR"}, {"S0", "C", "ERROR"},
            {"S1", "/", "S2"}, {"S1", "L", "ERROR"}, {"S1", "C", "ERROR"},
            {"S2", "/", "ERROR"}, {"S2", "L", "S3"}, {"S2", "C", "S3"},
            {"S3", "/", "ERROR"}, {"S3", "L", "S3"}, {"S3", "C", "S3"}
            };

}
