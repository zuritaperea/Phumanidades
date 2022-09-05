/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;


import DAO.TarjetaDeCreditoFacadeLocal;
import Entidades.Ingresos.TarjetaDeCredito;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author hugo
 */


@Stateless
public class TarjetaDeCreditoRN implements TarjetaDeCreditoRNLocal {
    
    @EJB
    private TarjetaDeCreditoFacadeLocal tarjetaDeCreditoFacadeLocal;

    @Override
    public void create(TarjetaDeCredito tarjetaDeCredito) throws Exception {
       tarjetaDeCreditoFacadeLocal.create(tarjetaDeCredito);
    }

    @Override
    public void edit(TarjetaDeCredito tarjetaDeCredito) throws Exception {
        tarjetaDeCreditoFacadeLocal.edit(tarjetaDeCredito);
    }

    @Override
    public void remove(TarjetaDeCredito tarjetaDeCredito) throws Exception {
        tarjetaDeCreditoFacadeLocal.remove(tarjetaDeCredito);
    }

    @Override
    public List<TarjetaDeCredito> findAll() throws Exception {
        return tarjetaDeCreditoFacadeLocal.findAll();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
