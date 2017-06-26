/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Carreras.Carrera;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface CarrerasRNLocal {

    public void create(Carrera carrera) throws Exception;

    public void edit(Carrera carrera) throws Exception;

    public void findAll();

    public List<Carrera> findCarreras() throws Exception;

    public void remove(Carrera carrera);

    public List<Carrera> findCarreraNombre(String nombre);

    public Carrera findByiD(Long id) throws Exception;

}
