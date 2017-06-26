/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import DAO.Contador025FacadeLocal;
import Entidades.Carreras.Contador025;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vouilloz
 */
@Stateless
public class Contador025RN implements Contador025RNLocal {

    @EJB
    private Contador025FacadeLocal contador025FacadeLocal;

    @Override
    public void create(Contador025 contador025) throws Exception {
        contador025FacadeLocal.create(contador025);
    }

    @Override
    public void edit(Contador025 contador025) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Contador025> findContador025s() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Contador025 contador025) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Contador025> findContador025Nombre(String nombre) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Contador025 findByiD(Long id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int findUltimoNumero() throws Exception {
        return contador025FacadeLocal.findUltimoNumero();
    }

}
