/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import DAO.CuentaFacadeLocal;
import Entidades.Carreras.Cuenta;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vouilloz
 */
@Stateless
public class CuentaRN implements CuentaRNLocal {

    @EJB
    private CuentaFacadeLocal cuentaFacadeLocal;

    @Override
    public void create(Cuenta cuenta) throws Exception {
        cuentaFacadeLocal.create(cuenta);
    }

    @Override
    public void remove(Cuenta cuenta) {
        cuentaFacadeLocal.remove(cuenta);
    }

    @Override
    public void edit(Cuenta cuenta) {
        cuentaFacadeLocal.edit(cuenta);
    }

    @Override
    public List<Cuenta> findAll() {
        return cuentaFacadeLocal.findAll();
    }

    @Override
    public Cuenta findByiD(Long id) throws Exception {
        return cuentaFacadeLocal.find(id);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public Cuenta findAllByCodigo(String codigo) {
        return cuentaFacadeLocal.findAllByCodigo(codigo);
    }
}
