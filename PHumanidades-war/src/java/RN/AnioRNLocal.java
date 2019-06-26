/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Carreras.Anio;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface AnioRNLocal {

    public void create(Anio anio) throws Exception;

    public void remove(Anio anio);

    public void edit(Anio anio);

    public List<Anio> findAll();

    public Anio findByiD(Long id) throws Exception;

}
