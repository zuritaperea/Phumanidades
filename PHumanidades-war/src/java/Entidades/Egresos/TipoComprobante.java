/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades.Egresos;

/**
 *
 * @author vouilloz
 */
public enum TipoComprobante {

    FACTURAB("Factura B"),
    FACTURAC("Factura C"),
    RECIBOB("Recibo B"),
    RECIBOC("Recibo C"),
    PASAJES("Pasajes"),
    COMPROBANTEINTERNO("Comprobante Interno");

    private String name;

    private TipoComprobante(String name) {
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
