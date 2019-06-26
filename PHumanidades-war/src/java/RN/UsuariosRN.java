/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import DAO.UsuariosFacadeLocal;
import Entidades.Usuarios.Usuarios;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vouilloz
 */
@Stateless
public class UsuariosRN implements UsuariosRNLocal {

    @EJB
    private UsuariosFacadeLocal usuarioFacadeLocal;

    @Override
    public void create(Usuarios u) throws Exception {
        usuarioFacadeLocal.create(u);
    }

    @Override
    public void edit(Usuarios u, String contrasena) throws Exception {
        usuarioFacadeLocal.edit(u);
    }

    @Override
    public void remove(Long idUsuario, Boolean bEstado) throws Exception {

    }

    @Override
    public void findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Usuarios> findUsuarios(String grupo) throws Exception {
        return usuarioFacadeLocal.findAll();
    }

    @Override
    public Usuarios findUserByNombreContrasena(String nombre, String contrasena) throws Exception {
        return usuarioFacadeLocal.findUserByNombreContrasena(nombre, contrasena);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void remove(Usuarios usuario) {
        usuarioFacadeLocal.remove(usuario);
    }

    @Override
    public void edit(Usuarios usuario) {
        usuarioFacadeLocal.edit(usuario);
    }

}
