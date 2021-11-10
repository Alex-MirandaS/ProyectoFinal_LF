/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

/**
 *
 * @author Alex
 */
public class InformacionGramaticas {

    /*DATOS DE LOS TOKENS
    Se incluyen los alfabetos, estados de aceptación y función de transición correspondiente a cada uno de los tipos de token disponibles
    L = Letra
    D = Numero
    C = Caracter
    
    
     */
    //NO TERMINAL INICIAL
    public static final String[] inicialesEscritura = {"ESCRIBIR"};
    public static final String[] inicialesRepetir = {"REPETIR"};
    public static final String[] inicialesCondicional = {"SI"};
    public static final String[] inicialesExpresion = {"(", "NUMERO", "IDENTIFICADOR"};//si hay error con identificador y expresion, pasar a asignacion
    public static final String[] inicialesAsignacion = {"IDENTIFICADOR"};

    //ESCRITURA
    public static final String[][] tablaAnalisisEscritura
            = {{"I", "ESCRIBIR", "ESCRIBIR S"}, {"I", "LITERAL", "ERROR"}, {"I", "NUMERO", "ERROR"}, {"I", "IDENTIFICADOR", "ERROR"}, {"I", "FIN", "ERROR"}, {"I", "$", "ERROR"},
            {"S", "ESCRIBIR", "ERROR"}, {"S", "LITERAL", "P F"}, {"S", "NUMERO", "P F"}, {"S", "IDENTIFICADOR", "P F"}, {"S", "FIN", "ERROR"}, {"S", "$", "ERROR"},
            {"P", "ESCRIBIR", "ERROR"}, {"P", "LITERAL", "LITERAL"}, {"P", "NUMERO", "NUMERO"}, {"P", "IDENTIFICADOR", "IDENTIFICDOR"}, {"P", "FIN", "ERROR"}, {"S", "$", "ERROR"},
            {"F", "ESCRIBIR", "ERROR"}, {"F", "LITERAL", "ERROR"}, {"F", "NUMERO", "ERROR"}, {"F", "IDENTIFICADOR", "ERROR"}, {"F", "FIN", "FIN N"}, {"F", "$", "ERROR"},
            {"N", "ESCRIBIR", "ERROR"}, {"N", "LITERAL", "ERROR"}, {"N", "LITERAL", "ERROR"}, {"N", "IDENTIFICADOR", "ERROR"}, {"N", "FIN", "ERROR"}, {"N", "$", "Ɛ"}
            };

    //REPETIR
    public static final String[][] tablaAnalisisRepetir
            = {{"I", "REPETIR", "REPETIR S"}, {"I", "INICIAR", "ERROR"}, {"I", "ESCRIBIR", "ERROR"}, {"I", "LITERAL", "ERROR"}, {"I", "NUMERO", "ERROR"}, {"I", "IDENTIFICADOR", "ERROR"}, {"I", "FIN", "ERROR"}, {"I", "$", "ERROR"},
            {"S", "REPETIR", "ERROR"}, {"S", "INICIAR", "ERROR"}, {"S", "ESCRIBIR", "ERROR"}, {"S", "LITERAL", "ERROR"}, {"S", "NUMERO", "P INICIAR E F"}, {"S", "IDENTIFICADOR", "P INICIAR E F"}, {"S", "FIN", "ERROR"}, {"S", "$", "ERROR"},
            {"P", "REPETIR", "ERROR"}, {"P", "INICIAR", "ERROR"}, {"P", "ESCRIBIR", "ERROR"}, {"P", "LITERAL", "ERROR"}, {"P", "NUMERO", "NUMERO"}, {"P", "IDENTIFICADOR", "IDENTIFICADOR"}, {"P", "FIN", "ERROR"}, {"P", "$", "ERROR"},
            {"E", "REPETIR", "ERROR"}, {"E", "INICIAR", "ERROR"}, {"E", "ESCRIBIR", "D E"}, {"E", "LITERAL", "ERROR"}, {"E", "NUMERO", "ERROR"}, {"E", "IDENTIFICADOR", "ERROR"}, {"E", "FIN", "Ɛ"}, {"E", "$", "ERROR"},
            {"D", "REPETIR", "ERROR"}, {"D", "INICIAR", "ERROR"}, {"D", "ESCRIBIR", "ESCRIBIR G"}, {"D", "LITERAL", "ERROR"}, {"D", "NUMERO", "ERROR"}, {"D", "IDENTIFICADOR", "ERROR"}, {"D", "FIN", "ERROR"}, {"D", "$", "ERROR"},
            {"G", "REPETIR", "ERROR"}, {"G", "INICIAR", "ERROR"}, {"G", "ESCRIBIR", "ERROR"}, {"G", "LITERAL", "H F"}, {"G", "NUMERO", "H F"}, {"G", "IDENTIFICADOR", "H F"}, {"G", "FIN", "ERROR"}, {"G", "$", "ERROR"},
            {"H", "REPETIR", "ERROR"}, {"H", "INICIAR", "ERROR"}, {"H", "ESCRIBIR", "ERROR"}, {"H", "LITERAL", "LITERAL"}, {"H", "NUMERO", "NUMERO"}, {"H", "IDENTIFICADOR", "IDENTIFICADOR"}, {"H", "FIN", "ERROR"}, {"H", "$", "ERROR"},
            {"F", "REPETIR", "ERROR"}, {"F", "INICIAR", "ERROR"}, {"F", "ESCRITURA", "ERROR"}, {"F", "NUMERO", "ERROR"}, {"F", "IDENTIFICADOR", "ERROR"}, {"F", "FIN", "FIN N"}, {"F", "$", "ERROR"},
            {"N", "REPETIR", "ERROR"}, {"N", "INICIAR", "ERROR"}, {"N", "ESCRITURA", "ERROR"}, {"N", "NUMERO", "ERROR"}, {"N", "IDENTIFICADOR", "ERROR"}, {"N", "FIN", "ERROR"}, {"N", "$", "Ɛ"},};

    //CONDICIONAL
    public static final String[][] tablaAnalisisCondicional
            = {{"I", "SI", "SI B S"}, {"I", "ENTONCES", "ERROR"}, {"I", "VERDADERO", "ERROR"}, {"I", "FALSO", "ERROR"}, {"I", "ESCRIBIR", "ERROR"}, {"I", "LITERAL", "ERROR"}, {"I", "NUMERO", "ERROR"}, {"I", "IDENTIFICADOR", "ERROR"}, {"I", "FIN", "ERROR"}, {"I", "$", "ERROR"},
            {"S", "SI", "ERROR"}, {"S", "ENTONCES", "ENTONCES E F"}, {"S", "VERDADERO", "ERROR"}, {"S", "FALSO", "ERROR"}, {"S", "ESCRIBIR", "ERROR"}, {"S", "LITERAL", "ERROR"}, {"S", "NUMERO", "ERROR"}, {"S", "IDENTIFICADOR", "ERROR"}, {"S", "FIN", "ERROR"}, {"S", "$", "ERROR"},
            {"B", "SI", "ERROR"}, {"B", "ENTONCES", "ERROR"}, {"B", "VERDADERO", "VERDADERO"}, {"B", "FALSO", "FALSO"}, {"B", "ESCRIBIR", "ERROR"}, {"B", "LITERAL", "ERROR"}, {"B", "NUMERO", "ERROR"}, {"B", "IDENTIFICADOR", "ERROR"}, {"B", "FIN", "ERROR"}, {"B", "$", "ERROR"},
            {"E", "SI", "ERROR"}, {"E", "ENTONCES", "ERROR"}, {"E", "VERDADERO", "ERROR"}, {"E", "FALSO", "ERROR"}, {"E", "ESCRIBIR", "D E"}, {"E", "LITERAL", "ERROR"}, {"E", "NUMERO", "ERROR"}, {"E", "IDENTIFICADOR", "ERROR"}, {"E", "FIN", "Ɛ"}, {"E", "$", "ERROR"},
            {"D", "SI", "ERROR"}, {"D", "ENTONCES", "ERROR"}, {"D", "VERDADERO", "ERROR"}, {"D", "FALSO", "ERROR"}, {"D", "ESCRIBIR", "ESCRIBIR G"}, {"D", "LITERAL", "ERROR"}, {"D", "NUMERO", "ERROR"}, {"D", "IDENTIFICADOR", "ERROR"}, {"D", "FIN", "ERROR"}, {"D", "$", "ERROR"},
            {"G", "SI", "ERROR"}, {"G", "ENTONCES", "ERROR"}, {"G", "VERDADERO", "ERROR"}, {"G", "FALSO", "ERROR"}, {"G", "ESCRIBIR", "ERROR"}, {"G", "LITERAL", "H F"}, {"G", "NUMERO", "H F"}, {"G", "IDENTIFICADOR", "H F"}, {"G", "FIN", "ERROR"}, {"G", "$", "ERROR"},
            {"H", "SI", "ERROR"}, {"H", "ENTONCES", "ERROR"}, {"H", "VERDADERO", "ERROR"}, {"H", "FALSO", "ERROR"}, {"H", "ESCRIBIR", "ERROR"}, {"H", "LITERAL", "LITERAL"}, {"H", "NUMERO", "NUMERO"}, {"H", "IDENTIFICADOR", "IDENTIFICADOR"}, {"H", "FIN", "ERROR"}, {"H", "$", "ERROR"},
            {"F", "SI", "ERROR"}, {"F", "ENTONCES", "ERROR"}, {"F", "VERDADERO", "ERROR"}, {"F", "FALSO", "ERROR"}, {"F", "ESCRIBIR", "ERROR"}, {"F", "LITERAL", "LITERAL"}, {"F", "NUMERO", "NUMERO"}, {"F", "IDENTIFICADOR", "IDENTIFICADOR"}, {"F", "FIN", "FIN N"}, {"F", "$", "ERROR"},
            {"N", "SI", "ERROR"}, {"N", "ENTONCES", "ERROR"}, {"N", "VERDADERO", "ERROR"}, {"N", "FALSO", "ERROR"}, {"N", "ESCRIBIR", "ERROR"}, {"N", "LITERAL", "LITERAL"}, {"N", "NUMERO", "NUMERO"}, {"N", "IDENTIFICADOR", "IDENTIFICADOR"}, {"N", "FIN", "ERROR"}, {"N", "$", "Ɛ"}
            };

    //EXPRESION
    public static final String[][] tablaAnalisisExpresion
            = {{"I", "(", "( E ) S"}, {"I", ")", "ERROR"}, {"I", "NUMERO", "E"}, {"I", "IDENTIFICADOR", "E"}, {"I", "+", "ERROR"}, {"I", "*", "ERROR"}, {"I", "$", "ERROR"},
            {"E", "(", "ERROR"}, {"E", ")", "ERROR"}, {"E", "NUMERO", "V S"}, {"E", "IDENTIFICADOR", "V S"}, {"E", "+", "ERROR"}, {"E", "*", "ERROR"}, {"E", "$", "ERROR"},
            {"V", "(", "ERROR"}, {"V", ")", "ERROR"}, {"V", "NUMERO", "NUMERO"}, {"V", "IDENTIFICADOR", "IDENTIFICADOR"}, {"V", "+", "ERROR"}, {"V", "*", "ERROR"}, {"V", "$", "ERROR"},
            {"O", "(", "ERROR"}, {"O", ")", "ERROR"}, {"O", "NUMERO", "ERROR"}, {"O", "IDENTIFICADOR", "ERROR"}, {"O", "+", "+"}, {"O", "*", "*"}, {"O", "$", "ERROR"},
            {"S", "(", "ERROR"}, {"S", ")", "Ɛ"}, {"S", "NUMERO", "ERROR"}, {"S", "IDENTIFICADOR", "ERROR"}, {"S", "+", "O I"}, {"S", "*", "O I"}, {"S", "$", "Ɛ"},};

    //ASIGNACION
    public static final String[][] tablaAnalisisAsignacion
            = {{"I", "IDENTIFICADOR", "IDENTIFICADOR S"}, {"I", "=", "ERROR"}, {"I", "(", "ERROR"}, {"I", ")", "ERROR"}, {"I", "NUMERO", "ERROR"}, {"I", "+", "ERROR"}, {"I", "*", "ERROR"}, {"I", "FIN", "ERROR"}, {"I", "$", "ERROR"},
            {"S", "IDENTIFICADOR", "ERROR"}, {"S", "=", "= E"}, {"S", "(", "ERROR"}, {"S", ")", "ERROR"}, {"S", "NUMERO", "ERROR"}, {"S", "+", "ERROR"}, {"S", "*", "ERROR"}, {"S", "FIN", "ERROR"}, {"S", "$", "ERROR"},
            {"E", "IDENTIFICADOR", "D F"}, {"E", "=", "ERROR"}, {"E", "(", "D F"}, {"S", ")", "ERROR"}, {"E", "NUMERO", "D F"}, {"E", "+", "ERROR"}, {"E", "*", "ERROR"}, {"E", "FIN", "ERROR"}, {"E", "$", "ERROR"},
            {"D", "IDENTIFICADOR", "X"}, {"D", "=", "ERROR"}, {"D", "(", "( X ) W"}, {"D", ")", "ERROR"}, {"D", "NUMERO", "X"}, {"D", "+", "ERROR"}, {"D", "*", "ERROR"}, {"D", "FIN", "Ɛ"}, {"D", "$", "ERROR"},
            {"X", "IDENTIFICADOR", "V W"}, {"X", "=", "ERROR"}, {"X", "(", "ERROR"}, {"X", ")", "ERROR"}, {"X", "NUMERO", "V W"}, {"X", "+", "ERROR"}, {"X", "*", "ERROR"}, {"X", "FIN", "ERROR"}, {"X", "$", "ERROR"},
            {"V", "IDENTIFICADOR", "IDENTIFICADOR"}, {"V", "=", "ERROR"}, {"V", "(", "ERROR"}, {"V", ")", "ERROR"}, {"V", "NUMERO", "NUMERO"}, {"V", "+", "ERROR"}, {"V", "*", "ERROR"}, {"V", "FIN", "ERROR"}, {"V", "$", "ERROR"},
            {"O", "IDENTIFICADOR", "ERROR"}, {"O", "=", "ERROR"}, {"O", "(", "ERROR"}, {"O", ")", "ERROR"}, {"O", "NUMERO", "ERROR"}, {"O", "+", "+"}, {"O", "*", "*"}, {"O", "FIN", "ERROR"}, {"O", "$", "ERROR"},
            {"W", "IDENTIFICADOR", "ERROR"}, {"W", "=", "ERROR"}, {"W", "(", "ERROR"}, {"W", ")", "Ɛ"}, {"W", "NUMERO", "ERROR"}, {"W", "+", "O D"}, {"W", "*", "O D"}, {"W", "FIN", "Ɛ"}, {"W", "$", "ERROR"},
            {"F", "IDENTIFICADOR", "ERROR"}, {"F", "=", "ERROR"}, {"F", "(", "ERROR"}, {"F", ")", "ERROR"}, {"F", "NUMERO", "ERROR"}, {"F", "+", "ERROR"}, {"F", "*", "ERROR"},  {"F", "FIN", "FIN N"}, {"F", "$", "ERROR"},
            {"N", "IDENTIFICADOR", "ERROR"}, {"N", "=", "ERROR"}, {"N", "(", "ERROR"}, {"N", ")", "ERROR"}, {"N", "NUMERO", "ERROR"}, {"N", "+", "ERROR"}, {"N", "*", "ERROR"}, {"N", "FIN", "ERROR"}, {"N", "$", "Ɛ"}
            };
}
