/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Carreras.Cuenta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface CuentaRNLocal {

    public void create(Cuenta cuenta) throws Exception;

    public void remove(Cuenta cuenta);

    public void edit(Cuenta cuenta);

    public List<Cuenta> findAll();

    public Cuenta findByiD(Long id) throws Exception;

    public Cuenta findAllByCodigo(String codigo);

}
