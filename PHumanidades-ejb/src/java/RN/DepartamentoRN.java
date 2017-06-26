/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import DAO.DepartamentoFacadeLocal;
import Entidades.Localidades.Departamento;
import Entidades.Localidades.Provincia;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author hugo
 */
@Stateless
public class DepartamentoRN implements DepartamentoRNLocal {

    @EJB
    private DepartamentoFacadeLocal departamentoFacadeLocal;

    @Override
    public void create(Departamento departamento) throws Exception {
        //Agregar validaciones
        departamentoFacadeLocal.create(departamento);
    }

    @Override
    public void edit(Departamento departamento) throws Exception {
        departamentoFacadeLocal.edit(departamento);
    }

    @Override
    public void remove(Departamento departamento) throws Exception {
        departamentoFacadeLocal.remove(departamento);
    }

    @Override
    public List<Departamento> findAll() throws Exception {
        return departamentoFacadeLocal.findAll();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Departamento buscarDepartamento(Departamento departamento) {
        return departamentoFacadeLocal.find(departamento.getId());
    }

    @Override
    public List<Departamento> buscarDptoProvincia(Provincia provincia) {
        return departamentoFacadeLocal.buscarDptosProvincia(provincia);
    }

}
