/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Ingresos.TarjetaDeCredito;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hugo
 */
@Local
public interface TarjetaDeCreditoRNLocal {
    
    public void create(TarjetaDeCredito tarjetaDeCredito) throws Exception;

    public void edit(TarjetaDeCredito tarjetaDeCredito) throws Exception;

    public void remove(TarjetaDeCredito tarjetaDeCredito) throws Exception;

    public List<TarjetaDeCredito> findAll() throws Exception;
    
}
