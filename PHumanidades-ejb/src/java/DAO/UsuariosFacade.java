/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Entidades.Usuarios.Usuarios;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author vouilloz
 */
@Stateless
public class UsuariosFacade extends AbstractFacade<Usuarios> implements UsuariosFacadeLocal {

    @PersistenceContext(unitName = "PHumanidades-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UsuariosFacade() {
        super(Usuarios.class);
    }

    @Override
    public Usuarios findUserByNombreContrasena(String nombre, String contrasena) {
        try {
            Query q = em.createNamedQuery("Usuarios.findUserByNombreContrasena");
            q.setParameter("usuario", nombre);
            q.setParameter("contrasena", contrasena);
            return (Usuarios) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
