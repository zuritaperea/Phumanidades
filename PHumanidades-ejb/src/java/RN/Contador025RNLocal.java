/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Carreras.Contador025;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface Contador025RNLocal {

    public void create(Contador025 contador025) throws Exception;

    public void edit(Contador025 contador025) throws Exception;

    public List<Contador025> findContador025s() throws Exception;

    public void remove(Contador025 contador025);

    public List<Contador025> findContador025Nombre(String nombre);

    public Contador025 findByiD(Long id) throws Exception;

    public int findUltimoNumero() throws Exception;

}
