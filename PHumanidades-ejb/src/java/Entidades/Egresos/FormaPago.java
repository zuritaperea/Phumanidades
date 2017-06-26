/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades.Egresos;

/**
 *
 * @author vouilloz
 */
public enum FormaPago {

    EFECTIVO("Efectivo"),
    CHEQUE("Cheque"),
    DEPOSITO("Deposito"),
    TARJETA("Tarjeta"),
    RAPIPAGO("Rapipago");
    private String name;

    private FormaPago(String name) {
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
