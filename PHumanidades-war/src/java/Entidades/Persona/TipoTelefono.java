/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades.Persona;

/**
 *
 * @author vouilloz
 */
public enum TipoTelefono {

    CELULAR("Celular"),
    TRABAJO("Trabajo"),
    HOGAR("Hogar");
    private String name;

    private TipoTelefono(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
