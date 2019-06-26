/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades.Persona;

/**
 *
 * @author vouilloz
 */
public enum Condicion {

    REGULAR("Regular"),
    LIBRE("Libre");

    private String name;

    private Condicion(String name) {
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
