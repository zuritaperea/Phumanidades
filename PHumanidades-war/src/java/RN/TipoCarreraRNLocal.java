/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Carreras.TipoCarrera;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface TipoCarreraRNLocal {

    public void create(TipoCarrera tipoCarrera) throws Exception;

    public void remove(TipoCarrera tipoCarrera);

    public void edit(TipoCarrera tipoCarrera);

    public List<TipoCarrera> findAll();

    public TipoCarrera findByiD(Long id) throws Exception;

}
