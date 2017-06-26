/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import DAO.Contador005FacadeLocal;
import Entidades.Carreras.Contador005;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vouilloz
 */
@Stateless
public class Contador005RN implements Contador005RNLocal {

    @EJB
    private Contador005FacadeLocal contador005FacadeLocal;

    @Override
    public void create(Contador005 contador005) throws Exception {
        contador005FacadeLocal.create(contador005);
    }

    @Override
    public void edit(Contador005 contador005) throws Exception {
        contador005FacadeLocal.edit(contador005);
    }

    @Override
    public List<Contador005> findContador005s() throws Exception {
        return contador005FacadeLocal.findAll();
    }

    @Override
    public void remove(Contador005 contador005) {
        contador005FacadeLocal.remove(contador005);
    }

    @Override
    public Contador005 findByiD(Long id) throws Exception {
        return contador005FacadeLocal.find(id);
    }

    @Override
    public int findUltimoNumero() throws Exception {
        return contador005FacadeLocal.findUltimoNumero();
    }

}
