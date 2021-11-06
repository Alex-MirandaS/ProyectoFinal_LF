/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Enums;

/**
 *
 * @author Alex
 */
public enum TipoToken {

    IDENTIFICADOR("IDENTIFICADOR"),
    NUMERO("NUMERO"),
    LITERAL("LITERAL"),
    COMENTARIO("COMENTARIO"),
    EXTRAS("EXTRAS"),
    PALABRAR("PALABRAR"),
    ERROR("ERROR");

    private String nombre;

    private TipoToken(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

}
