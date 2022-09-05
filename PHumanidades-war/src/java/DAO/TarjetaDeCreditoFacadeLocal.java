/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Ingresos.TarjetaDeCredito;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hugo
 */
@Local
public interface TarjetaDeCreditoFacadeLocal {

    void create(TarjetaDeCredito tarjetaDeCredito);

    void edit(TarjetaDeCredito tarjetaDeCredito);

    void remove(TarjetaDeCredito tarjetaDeCredito);

    TarjetaDeCredito find(Object id);

    List<TarjetaDeCredito> findAll();

    List<TarjetaDeCredito> findRange(int[] range);

    int count();
    
}
