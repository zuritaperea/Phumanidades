/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import Entidades.Usuarios.Usuarios;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author vouilloz
 */
@Local
public interface UsuariosRNLocal {

    public void create(Usuarios u) throws Exception;

    public void edit(Usuarios u, String contrasena) throws Exception;

    public void remove(Long idUsuario, Boolean bEstado) throws Exception;

    public void findAll();

    public List<Usuarios> findUsuarios(String grupo) throws Exception;

    public Usuarios findUserByNombreContrasena(String nombre, String contrasena) throws Exception;

    public void remove(Usuarios usuario);

    public void edit(Usuarios usuario);

}
