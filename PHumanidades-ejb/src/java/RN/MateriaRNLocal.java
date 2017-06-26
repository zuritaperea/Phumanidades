/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Carreras.Materia;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface MateriaRNLocal {

    public void create(Materia m) throws Exception;

    public void edit(Materia m) throws Exception;

    public void remove(Materia m);

    public List<Materia> findAll();

}
