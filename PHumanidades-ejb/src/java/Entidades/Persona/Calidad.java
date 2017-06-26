/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades.Persona;

/**
 *
 * @author vouilloz
 */
public enum Calidad {

    ACTIVO("Activo/a"),
    EGRESADO("Egresado/a");

    private String name;

    private Calidad(String name) {
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
