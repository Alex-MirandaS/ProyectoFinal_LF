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
    public static final String[] alfabetoSignosAgrupacion = {"(", ")", "[", "]", "{", "}", String.valueOf('"'), "“", "”", "'", "<", ">"};
    public static final String[] alfabetoCaracteres = {"_", "=", " "};
    //VALORES INICIALES
    //IDENTIFICADOR
    public static final String[][] inicialesIdentificador = {alfabetoLetras, {"_"}};
    public static final String[][] inicialesNumero = {alfabetoNumero, {"-"}};
    public static final String[][] inicialesLiteral = {{"“",String.valueOf('"')}};
    public static final String[][] inicialesComentario = {{"/"}};
    public static final String[][] inicialesExtras = {{"(", ")", "+", "*", "="}};
    public static final String[][] inicialesPalabraR = {{"E", "F", "R", "I", "S", "V"}};

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
            = {{"S0", String.valueOf('"'), "S1"}, {"S0", "L", "ERROR"}, {"S0", "C", "ERROR"},
            {"S1", String.valueOf('"'), "S2"}, {"S1", "L", "S1"}, {"S1", "C", "S1"}, 
            {"S2", String.valueOf('"'), "ERROR"}, {"S2", "L", "ERROR"}, {"S2", "C", "ERROR"}
            };

    //COMENTARIO
    public static final String[] estadosAceptacionComentario = {"S3"};
    public static final String[][] funcionTransicionComentario
            = {{"S0", "/", "S1"}, {"S0", "L", "ERROR"}, {"S0", "C", "ERROR"},
            {"S1", "/", "S2"}, {"S1", "L", "ERROR"}, {"S1", "C", "ERROR"},
            {"S2", "/", "ERROR"}, {"S2", "L", "S3"}, {"S2", "C", "S3"},
            {"S3", "/", "ERROR"}, {"S3", "L", "S3"}, {"S3", "C", "S3"}
            };
    //EXTRAS
    public static final String[] estadosAceptacionExtras = {"S1"};
    public static final String[][] funcionTransicionExtras
            = {{"S0", "(", "S1"}, {"S0", ")", "S1"}, {"S0", "+", "S1"}, {"S0", "*", "S1"}, {"S0", "=", "S1"},
            {"S1", "(", "ERROR"}, {"S1", ")", "ERROR"}, {"S1", "+", "ERROR"}, {"S1", "*", "ERROR"}, {"S1", "=", "ERROR"}
            };

    //PALABRAS RESERVADAS
    public static final String[] estadosAceptacionPR = {"S8"};
    public static final String[][] funcionTransicionPR
            = {{"S0", "E", "S1"}, {"S0", "S", "S32"}, {"S0", "C", "ERROR"}, {"S0", "R", "S20"}, {"S0", "I", "S26"}, {"S0", "B", "ERROR"}, {"S0", "P", "ERROR"}, {"S0", "T", "ERROR"}, {"S0", "F", "S15"}, {"S0", "N", "ERROR"}, {"S0", "A", "ERROR"}, {"S0", "V", "S33"}, {"S0", "D", "ERROR"}, {"S0", "O", "ERROR"}, {"S0", "L", "ERROR"},
            {"S1", "E", "ERROR"}, {"S1", "S", "S2"}, {"S1", "C", "ERROR"}, {"S1", "R", "ERROR"}, {"S1", "I", "ERROR"}, {"S1", "B", "ERROR"}, {"S1", "P", "ERROR"}, {"S1", "T", "ERROR"}, {"S1", "F", "ERROR"}, {"S1", "N", "S9"}, {"S1", "A", "ERROR"}, {"S1", "V", "ERROR"}, {"S1", "D", "ERROR"}, {"S1", "O", "ERROR"}, {"S1", "L", "ERROR"},
            {"S2", "E", "ERROR"}, {"S2", "S", "ERROR"}, {"S2", "C", "S3"}, {"S2", "R", "ERROR"}, {"S2", "I", "ERROR"}, {"S2", "B", "ERROR"}, {"S2", "P", "ERROR"}, {"S2", "T", "ERROR"}, {"S2", "F", "ERROR"}, {"S2", "N", "ERROR"}, {"S2", "A", "ERROR"}, {"S2", "V", "ERROR"}, {"S2", "D", "ERROR"}, {"S2", "O", "ERROR"}, {"S2", "L", "ERROR"},
            {"S3", "E", "ERROR"}, {"S3", "S", "ERROR"}, {"S3", "C", "ERROR"}, {"S3", "R", "S4"}, {"S3", "I", "ERROR"}, {"S3", "B", "ERROR"}, {"S3", "P", "ERROR"}, {"S3", "T", "ERROR"}, {"S3", "F", "ERROR"}, {"S3", "N", "ERROR"}, {"S3", "A", "ERROR"}, {"S3", "V", "ERROR"}, {"S3", "D", "ERROR"}, {"S3", "O", "ERROR"}, {"S3", "L", "ERROR"},
            {"S4", "E", "ERROR"}, {"S4", "S", "ERROR"}, {"S4", "C", "ERROR"}, {"S4", "R", "ERROR"}, {"S4", "I", "S5"}, {"S4", "B", "ERROR"}, {"S4", "P", "ERROR"}, {"S4", "T", "ERROR"}, {"S4", "F", "ERROR"}, {"S4", "N", "ERROR"}, {"S4", "A", "ERROR"}, {"S4", "V", "ERROR"}, {"S4", "D", "ERROR"}, {"S4", "O", "ERROR"}, {"S4", "L", "ERROR"},
            {"S5", "E", "ERROR"}, {"S5", "S", "ERROR"}, {"S5", "C", "ERROR"}, {"S5", "R", "ERROR"}, {"S5", "I", "ERROR"}, {"S5", "B", "S6"}, {"S5", "P", "ERROR"}, {"S5", "T", "ERROR"}, {"S5", "F", "ERROR"}, {"S5", "N", "ERROR"}, {"S5", "A", "ERROR"}, {"S5", "V", "ERROR"}, {"S5", "D", "ERROR"}, {"S5", "O", "ERROR"}, {"S5", "L", "ERROR"},
            {"S6", "E", "ERROR"}, {"S6", "S", "ERROR"}, {"S6", "C", "ERROR"}, {"S6", "R", "ERROR"}, {"S6", "I", "S7"}, {"S6", "B", "ERROR"}, {"S6", "P", "ERROR"}, {"S6", "T", "ERROR"}, {"S6", "F", "ERROR"}, {"S6", "N", "ERROR"}, {"S6", "A", "ERROR"}, {"S6", "V", "ERROR"}, {"S6", "D", "ERROR"}, {"S6", "O", "ERROR"}, {"S6", "L", "ERROR"},
            {"S7", "E", "ERROR"}, {"S7", "S", "ERROR"}, {"S7", "C", "ERROR"}, {"S7", "R", "S8"}, {"S7", "I", "ERROR"}, {"S7", "B", "ERROR"}, {"S7", "P", "ERROR"}, {"S7", "T", "ERROR"}, {"S7", "F", "ERROR"}, {"S7", "N", "ERROR"}, {"S7", "A", "ERROR"}, {"S7", "V", "ERROR"}, {"S7", "D", "ERROR"}, {"S7", "O", "ERROR"}, {"S7", "L", "ERROR"},
            {"S8", "E", "ERROR"}, {"S8", "S", "ERROR"}, {"S8", "C", "ERROR"}, {"S8", "R", "ERROR"}, {"S8", "I", "ERROR"}, {"S8", "B", "ERROR"}, {"S8", "P", "ERROR"}, {"S8", "T", "ERROR"}, {"S8", "F", "ERROR"}, {"S8", "N", "ERROR"}, {"S8", "A", "ERROR"}, {"S8", "V", "ERROR"}, {"S8", "D", "ERROR"}, {"S8", "O", "ERROR"}, {"S8", "L", "ERROR"},
            {"S9", "E", "ERROR"}, {"S9", "S", "ERROR"}, {"S9", "C", "ERROR"}, {"S9", "R", "ERROR"}, {"S9", "I", "ERROR"}, {"S9", "B", "ERROR"}, {"S9", "P", "ERROR"}, {"S9", "T", "S10"}, {"S9", "F", "ERROR"}, {"S9", "N", "ERROR"}, {"S9", "A", "ERROR"}, {"S9", "V", "ERROR"}, {"S9", "D", "ERROR"}, {"S9", "O", "ERROR"}, {"S9", "L", "ERROR"},
            {"S10", "E", "ERROR"}, {"S10", "S", "ERROR"}, {"S10", "C", "ERROR"}, {"S10", "R", "ERROR"}, {"S10", "I", "ERROR"}, {"S10", "B", "ERROR"}, {"S10", "P", "ERROR"}, {"S10", "T", "ERROR"}, {"S10", "F", "ERROR"}, {"S10", "N", "ERROR"}, {"S10", "A", "ERROR"}, {"S10", "V", "ERROR"}, {"S10", "D", "ERROR"}, {"S10", "O", "S11"}, {"S10", "L", "ERROR"},
            {"S11", "E", "ERROR"}, {"S11", "S", "ERROR"}, {"S11", "C", "ERROR"}, {"S11", "R", "ERROR"}, {"S11", "I", "ERROR"}, {"S11", "B", "ERROR"}, {"S11", "P", "ERROR"}, {"S11", "T", "ERROR"}, {"S11", "F", "ERROR"}, {"S11", "N", "S12"}, {"S11", "A", "ERROR"}, {"S11", "V", "ERROR"}, {"S11", "D", "ERROR"}, {"S11", "O", "ERROR"}, {"S11", "L", "ERROR"},
            {"S12", "E", "ERROR"}, {"S12", "S", "ERROR"}, {"S12", "C", "S13"}, {"S12", "R", "ERROR"}, {"S12", "I", "ERROR"}, {"S12", "B", "ERROR"}, {"S12", "P", "ERROR"}, {"S12", "T", "ERROR"}, {"S12", "F", "ERROR"}, {"S12", "N", "ERROR"}, {"S12", "A", "ERROR"}, {"S12", "V", "ERROR"}, {"S12", "D", "ERROR"}, {"S12", "O", "ERROR"}, {"S12", "L", "ERROR"},
            {"S13", "E", "S14"}, {"S13", "S", "ERROR"}, {"S13", "C", "ERROR"}, {"S13", "R", "ERROR"}, {"S13", "I", "ERROR"}, {"S13", "B", "ERROR"}, {"S13", "P", "ERROR"}, {"S13", "T", "ERROR"}, {"S13", "F", "ERROR"}, {"S13", "N", "ERROR"}, {"S13", "A", "ERROR"}, {"S13", "V", "ERROR"}, {"S13", "D", "ERROR"}, {"S13", "O", "ERROR"}, {"S13", "L", "ERROR"},
            {"S14", "E", "ERROR"}, {"S14", "S", "S8"}, {"S14", "C", "ERROR"}, {"S14", "R", "ERROR"}, {"S14", "I", "ERROR"}, {"S14", "B", "ERROR"}, {"S14", "P", "ERROR"}, {"S14", "T", "ERROR"}, {"S14", "F", "ERROR"}, {"S14", "N", "ERROR"}, {"S14", "A", "ERROR"}, {"S14", "V", "ERROR"}, {"S14", "D", "ERROR"}, {"S14", "O", "ERROR"}, {"S14", "L", "ERROR"},
            {"S15", "E", "ERROR"}, {"S15", "S", "ERROR"}, {"S15", "C", "ERROR"}, {"S15", "R", "ERROR"}, {"S15", "I", "S19"}, {"S15", "B", "ERROR"}, {"S15", "P", "ERROR"}, {"S15", "T", "ERROR"}, {"S15", "F", "ERROR"}, {"S15", "N", "ERROR"}, {"S15", "A", "S16"}, {"S15", "V", "ERROR"}, {"S15", "D", "ERROR"}, {"S15", "O", "ERROR"}, {"S15", "L", "ERROR"},
            {"S16", "E", "ERROR"}, {"S16", "S", "ERROR"}, {"S16", "C", "ERROR"}, {"S16", "R", "ERROR"}, {"S16", "I", "ERROR"}, {"S16", "B", "ERROR"}, {"S16", "P", "ERROR"}, {"S16", "T", "ERROR"}, {"S16", "F", "ERROR"}, {"S16", "N", "ERROR"}, {"S16", "A", "ERROR"}, {"S16", "V", "ERROR"}, {"S16", "D", "ERROR"}, {"S16", "O", "ERROR"}, {"S16", "L", "S17"},
            {"S17", "E", "ERROR"}, {"S17", "S", "S18"}, {"S17", "C", "ERROR"}, {"S17", "R", "ERROR"}, {"S17", "I", "ERROR"}, {"S17", "B", "ERROR"}, {"S17", "P", "ERROR"}, {"S17", "T", "ERROR"}, {"S17", "F", "ERROR"}, {"S17", "N", "ERROR"}, {"S17", "A", "ERROR"}, {"S17", "V", "ERROR"}, {"S17", "D", "ERROR"}, {"S17", "O", "ERROR"}, {"S17", "L", "ERROR"},
            {"S18", "E", "ERROR"}, {"S18", "S", "ERROR"}, {"S18", "C", "ERROR"}, {"S18", "R", "ERROR"}, {"S18", "I", "ERROR"}, {"S18", "B", "ERROR"}, {"S18", "P", "ERROR"}, {"S18", "T", "ERROR"}, {"S18", "F", "ERROR"}, {"S18", "N", "ERROR"}, {"S18", "A", "ERROR"}, {"S18", "V", "ERROR"}, {"S18", "D", "ERROR"}, {"S18", "O", "S8"}, {"S18", "L", "ERROR"},
            {"S19", "E", "ERROR"}, {"S19", "S", "ERROR"}, {"S19", "C", "ERROR"}, {"S19", "R", "ERROR"}, {"S19", "I", "ERROR"}, {"S19", "B", "ERROR"}, {"S19", "P", "ERROR"}, {"S19", "T", "ERROR"}, {"S19", "F", "ERROR"}, {"S19", "N", "S8"}, {"S19", "A", "ERROR"}, {"S19", "V", "ERROR"}, {"S19", "D", "ERROR"}, {"S19", "O", "ERROR"}, {"S19", "L", "ERROR"},
            {"S20", "E", "S21"}, {"S20", "S", "ERROR"}, {"S20", "C", "ERROR"}, {"S20", "R", "ERROR"}, {"S20", "I", "ERROR"}, {"S20", "B", "ERROR"}, {"S20", "P", "ERROR"}, {"S20", "T", "ERROR"}, {"S20", "F", "ERROR"}, {"S20", "N", "ERROR"}, {"S20", "A", "ERROR"}, {"S20", "V", "ERROR"}, {"S20", "D", "ERROR"}, {"S20", "O", "ERROR"}, {"S20", "L", "ERROR"},
            {"S21", "E", "ERROR"}, {"S21", "S", "ERROR"}, {"S21", "C", "ERROR"}, {"S21", "R", "ERROR"}, {"S21", "I", "ERROR"}, {"S21", "B", "ERROR"}, {"S21", "P", "S22"}, {"S21", "T", "ERROR"}, {"S21", "F", "ERROR"}, {"S21", "N", "ERROR"}, {"S21", "A", "ERROR"}, {"S21", "V", "ERROR"}, {"S21", "D", "ERROR"}, {"S21", "O", "ERROR"}, {"S21", "L", "ERROR"},
            {"S22", "E", "S23"}, {"S22", "S", "ERROR"}, {"S22", "C", "ERROR"}, {"S22", "R", "ERROR"}, {"S22", "I", "ERROR"}, {"S22", "B", "ERROR"}, {"S22", "P", "ERROR"}, {"S22", "T", "ERROR"}, {"S22", "F", "ERROR"}, {"S22", "N", "ERROR"}, {"S22", "A", "ERROR"}, {"S22", "V", "ERROR"}, {"S22", "D", "ERROR"}, {"S22", "O", "ERROR"}, {"S22", "L", "ERROR"},
            {"S23", "E", "ERROR"}, {"S23", "S", "ERROR"}, {"S23", "C", "ERROR"}, {"S23", "R", "ERROR"}, {"S23", "I", "ERROR"}, {"S23", "B", "ERROR"}, {"S23", "P", "ERROR"}, {"S23", "T", "S24"}, {"S23", "F", "ERROR"}, {"S23", "N", "ERROR"}, {"S23", "A", "ERROR"}, {"S23", "V", "ERROR"}, {"S23", "D", "ERROR"}, {"S23", "O", "ERROR"}, {"S23", "L", "ERROR"},
            {"S24", "E", "ERROR"}, {"S24", "S", "ERROR"}, {"S24", "C", "ERROR"}, {"S24", "R", "ERROR"}, {"S24", "I", "S25"}, {"S24", "B", "ERROR"}, {"S24", "P", "ERROR"}, {"S24", "T", "ERROR"}, {"S24", "F", "ERROR"}, {"S24", "N", "ERROR"}, {"S24", "A", "ERROR"}, {"S24", "V", "ERROR"}, {"S24", "D", "ERROR"}, {"S24", "O", "ERROR"}, {"S24", "L", "ERROR"},
            {"S25", "E", "ERROR"}, {"S25", "S", "ERROR"}, {"S25", "C", "ERROR"}, {"S25", "R", "S8"}, {"S25", "I", "ERROR"}, {"S25", "B", "ERROR"}, {"S25", "P", "ERROR"}, {"S25", "T", "ERROR"}, {"S25", "F", "ERROR"}, {"S25", "N", "ERROR"}, {"S25", "A", "ERROR"}, {"S25", "V", "ERROR"}, {"S25", "D", "ERROR"}, {"S25", "O", "ERROR"}, {"S25", "L", "ERROR"},
            {"S26", "E", "ERROR"}, {"S26", "S", "ERROR"}, {"S26", "C", "ERROR"}, {"S26", "R", "ERROR"}, {"S26", "I", "ERROR"}, {"S26", "B", "ERROR"}, {"S26", "P", "ERROR"}, {"S26", "T", "ERROR"}, {"S26", "F", "ERROR"}, {"S26", "N", "S27"}, {"S26", "A", "ERROR"}, {"S26", "V", "ERROR"}, {"S26", "D", "ERROR"}, {"S26", "O", "ERROR"}, {"S26", "L", "ERROR"},
            {"S27", "E", "ERROR"}, {"S27", "S", "ERROR"}, {"S27", "C", "ERROR"}, {"S27", "R", "ERROR"}, {"S27", "I", "S28"}, {"S27", "B", "ERROR"}, {"S27", "P", "ERROR"}, {"S27", "T", "ERROR"}, {"S27", "F", "ERROR"}, {"S27", "N", "ERROR"}, {"S27", "A", "ERROR"}, {"S27", "V", "ERROR"}, {"S27", "D", "ERROR"}, {"S27", "O", "ERROR"}, {"S27", "L", "ERROR"},
            {"S28", "E", "ERROR"}, {"S28", "S", "ERROR"}, {"S28", "C", "S29"}, {"S28", "R", "ERROR"}, {"S28", "I", "ERROR"}, {"S28", "B", "ERROR"}, {"S28", "P", "ERROR"}, {"S28", "T", "ERROR"}, {"S28", "F", "ERROR"}, {"S28", "N", "ERROR"}, {"S28", "A", "ERROR"}, {"S28", "V", "ERROR"}, {"S28", "D", "ERROR"}, {"S28", "O", "ERROR"}, {"S28", "L", "ERROR"},
            {"S29", "E", "ERROR"}, {"S29", "S", "ERROR"}, {"S29", "C", "ERROR"}, {"S29", "R", "ERROR"}, {"S29", "I", "S30"}, {"S29", "B", "ERROR"}, {"S29", "P", "ERROR"}, {"S29", "T", "ERROR"}, {"S29", "F", "ERROR"}, {"S29", "N", "ERROR"}, {"S29", "A", "ERROR"}, {"S29", "V", "ERROR"}, {"S29", "D", "ERROR"}, {"S29", "O", "ERROR"}, {"S29", "L", "ERROR"},
            {"S30", "E", "ERROR"}, {"S30", "S", "ERROR"}, {"S30", "C", "ERROR"}, {"S30", "R", "ERROR"}, {"S30", "I", "ERROR"}, {"S30", "B", "ERROR"}, {"S30", "P", "ERROR"}, {"S30", "T", "ERROR"}, {"S30", "F", "ERROR"}, {"S30", "N", "ERROR"}, {"S30", "A", "S31"}, {"S30", "V", "ERROR"}, {"S30", "D", "ERROR"}, {"S30", "O", "ERROR"}, {"S30", "L", "ERROR"},
            {"S31", "E", "ERROR"}, {"S31", "S", "ERROR"}, {"S31", "C", "ERROR"}, {"S31", "R", "S8"}, {"S31", "I", "ERROR"}, {"S31", "B", "ERROR"}, {"S31", "P", "ERROR"}, {"S31", "T", "ERROR"}, {"S31", "F", "ERROR"}, {"S31", "N", "ERROR"}, {"S31", "A", "ERROR"}, {"S31", "V", "ERROR"}, {"S31", "D", "ERROR"}, {"S31", "O", "ERROR"}, {"S31", "L", "ERROR"},
            {"S32", "E", "ERROR"}, {"S32", "S", "ERROR"}, {"S32", "C", "ERROR"}, {"S32", "R", "ERROR"}, {"S32", "I", "S8"}, {"S32", "B", "ERROR"}, {"S32", "P", "ERROR"}, {"S32", "T", "ERROR"}, {"S32", "F", "ERROR"}, {"S32", "N", "ERROR"}, {"S32", "A", "ERROR"}, {"S32", "V", "ERROR"}, {"S32", "D", "ERROR"}, {"S32", "O", "ERROR"}, {"S32", "L", "ERROR"},
            {"S33", "E", "S34"}, {"S33", "S", "ERROR"}, {"S33", "C", "ERROR"}, {"S33", "R", "ERROR"}, {"S33", "I", "ERROR"}, {"S33", "B", "ERROR"}, {"S33", "P", "ERROR"}, {"S33", "T", "ERROR"}, {"S33", "F", "ERROR"}, {"S33", "N", "ERROR"}, {"S33", "A", "ERROR"}, {"S33", "V", "ERROR"}, {"S33", "D", "ERROR"}, {"S33", "O", "ERROR"}, {"S33", "L", "ERROR"},
            {"S34", "E", "ERROR"}, {"S34", "S", "ERROR"}, {"S34", "C", "ERROR"}, {"S34", "R", "S35"}, {"S34", "I", "ERROR"}, {"S34", "B", "ERROR"}, {"S34", "P", "ERROR"}, {"S34", "T", "ERROR"}, {"S34", "F", "ERROR"}, {"S34", "N", "ERROR"}, {"S34", "A", "ERROR"}, {"S34", "V", "ERROR"}, {"S34", "D", "ERROR"}, {"S34", "O", "ERROR"}, {"S34", "L", "ERROR"},
            {"S35", "E", "ERROR"}, {"S35", "S", "ERROR"}, {"S35", "C", "ERROR"}, {"S35", "R", "ERROR"}, {"S35", "I", "ERROR"}, {"S35", "B", "ERROR"}, {"S35", "P", "ERROR"}, {"S35", "T", "ERROR"}, {"S35", "F", "ERROR"}, {"S35", "N", "ERROR"}, {"S35", "A", "ERROR"}, {"S35", "V", "ERROR"}, {"S35", "D", "S36"}, {"S35", "O", "ERROR"}, {"S35", "L", "ERROR"},
            {"S36", "E", "ERROR"}, {"S36", "S", "ERROR"}, {"S36", "C", "ERROR"}, {"S36", "R", "ERROR"}, {"S36", "I", "ERROR"}, {"S36", "B", "ERROR"}, {"S36", "P", "ERROR"}, {"S36", "T", "ERROR"}, {"S36", "F", "ERROR"}, {"S36", "N", "ERROR"}, {"S36", "A", "S37"}, {"S36", "V", "ERROR"}, {"S36", "D", "ERROR"}, {"S36", "O", "ERROR"}, {"S36", "L", "ERROR"},
            {"S37", "E", "ERROR"}, {"S37", "S", "ERROR"}, {"S37", "C", "ERROR"}, {"S37", "R", "ERROR"}, {"S37", "I", "ERROR"}, {"S37", "B", "ERROR"}, {"S37", "P", "ERROR"}, {"S37", "T", "ERROR"}, {"S37", "F", "ERROR"}, {"S37", "N", "ERROR"}, {"S37", "A", "ERROR"}, {"S37", "V", "ERROR"}, {"S37", "D", "S38"}, {"S37", "O", "ERROR"}, {"S37", "L", "ERROR"},
            {"S38", "E", "S39"}, {"S38", "S", "ERROR"}, {"S38", "C", "ERROR"}, {"S38", "R", "ERROR"}, {"S38", "I", "ERROR"}, {"S38", "B", "ERROR"}, {"S38", "P", "ERROR"}, {"S38", "T", "ERROR"}, {"S38", "F", "ERROR"}, {"S38", "N", "ERROR"}, {"S38", "A", "ERROR"}, {"S38", "V", "ERROR"}, {"S38", "D", "ERROR"}, {"S38", "O", "ERROR"}, {"S38", "L", "ERROR"},
            {"S39", "E", "ERROR"}, {"S39", "S", "ERROR"}, {"S39", "C", "ERROR"}, {"S39", "R", "S40"}, {"S39", "I", "ERROR"}, {"S39", "B", "ERROR"}, {"S39", "P", "ERROR"}, {"S39", "T", "ERROR"}, {"S39", "F", "ERROR"}, {"S39", "N", "ERROR"}, {"S39", "A", "ERROR"}, {"S39", "V", "ERROR"}, {"S39", "D", "ERROR"}, {"S39", "O", "ERROR"}, {"S39", "L", "ERROR"},
            {"S40", "E", "ERROR"}, {"S40", "S", "ERROR"}, {"S40", "C", "ERROR"}, {"S40", "R", "ERROR"}, {"S40", "I", "ERROR"}, {"S40", "B", "ERROR"}, {"S40", "P", "ERROR"}, {"S40", "T", "ERROR"}, {"S40", "F", "ERROR"}, {"S40", "N", "ERROR"}, {"S40", "A", "ERROR"}, {"S40", "V", "ERROR"}, {"S40", "D", "ERROR"}, {"S40", "O", "S8"}, {"S40", "L", "ERROR"}
            };
    
}
