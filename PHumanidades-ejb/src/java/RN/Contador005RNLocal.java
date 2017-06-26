/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Carreras.Contador005;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface Contador005RNLocal {

    public void create(Contador005 contador005) throws Exception;

    public void edit(Contador005 contador005) throws Exception;

    public List<Contador005> findContador005s() throws Exception;

    public void remove(Contador005 contador005);

    public Contador005 findByiD(Long id) throws Exception;

    public int findUltimoNumero() throws Exception;
}
