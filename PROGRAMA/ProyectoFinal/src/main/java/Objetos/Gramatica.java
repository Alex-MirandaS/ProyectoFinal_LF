/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Objetos;

import Enums.TipoGramatica;
import java.util.ArrayList;

/**
 *
 * @author Alex
 */
public class Gramatica {

    private ArrayList<Token> tokensGramatica;
    private TipoGramatica tipo;

    public Gramatica(ArrayList<Token> tokensGramatica, TipoGramatica tipo) {
        this.tokensGramatica = tokensGramatica;
        this.tipo = tipo;
    }

    public TipoGramatica getTipo() {
        return tipo;
    }

    public void setTipo(TipoGramatica tipo) {
        this.tipo = tipo;
    }

    public ArrayList<Token> getTokensGramatica() {
        return tokensGramatica;
    }

    public void setTokensGramatica(ArrayList<Token> tokensGramatica) {
        this.tokensGramatica = tokensGramatica;
    }

}
