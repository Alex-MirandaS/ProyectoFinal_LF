/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Enums;

/**
 *
 * @author Alex
 */
public enum TipoGramatica {
    ESCRITURA("ESCRITURA"),
    REPETIR("REPETIR"),
    CONDICIONAL("CONDICIONAL"),
    EXPRESION("EXPRESION"),
    ASIGNACIÓN("ASIGNACIÓN"),
    ERROR("ERROR");

    private String nombre;

    private TipoGramatica(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
