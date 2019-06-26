/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Localidades.Departamento;
import Entidades.Localidades.Provincia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hugo
 */
@Local
public interface DepartamentoRNLocal {

    public void create(Departamento departamento) throws Exception;

    public void edit(Departamento departamento) throws Exception;

    public void remove(Departamento departamento) throws Exception;

    public List<Departamento> findAll() throws Exception;

    public Departamento buscarDepartamento(Departamento departamento);

    public List<Departamento> buscarDptoProvincia(Provincia provincia);

}
