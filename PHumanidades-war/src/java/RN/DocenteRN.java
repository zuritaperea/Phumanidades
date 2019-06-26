/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import DAO.DocenteFacadeLocal;
import Entidades.Persona.Docente;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vouilloz
 */
@Stateless
public class DocenteRN implements DocenteRNLocal {

    @EJB
    private DocenteFacadeLocal docentefacadelocal;

    @Override
    public void create(Docente docente) throws Exception {
        //Agregar validaciones
        this.validar(docente);
        docentefacadelocal.create(docente);
    }

    @Override
    public void edit(Docente docente) throws Exception {
        docentefacadelocal.edit(docente);
    }

    @Override
    public void remove(Docente docente) throws Exception {
        docentefacadelocal.remove(docente);
    }

    @Override
    public List<Docente> findAll() throws Exception {
        return docentefacadelocal.findAll();
    }

    @Override
    public Docente buscarDocente(Docente docente) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Docente> buscarDocenteNombre(String cadena) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Docente> findLikeNombreApellido(String cadena) throws Exception {
        return docentefacadelocal.findLikeNombreApellido(cadena);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private void validar(Docente docente) throws Exception {

        if (docente.getNombre().isEmpty()) {
            throw new Exception("No ingreso el nombre");
        }//fin if

        if (docente.getApellido().isEmpty()) {
            throw new Exception("No ingreso el apellido");
        }//fin if

        if (docente.getDni().isEmpty()) {
            throw new Exception("No ingreso el DNI");
        }//fin if

        if (validarDni(docente.getDni()) == false) {
            throw new Exception("El Formato del Dni ingresado es incorrecto");
        }
        /* if(docenteFacadeLocal.isFindPersonaByNombreApellido(docente.getNombre(), docente.getApellido(), docente.getId(), op)){
         throw new Exception("Ya existe una docente con el nombre y apellido ingresados");
         }//fin if*/

        /*    if(docentefacadelocal.FindPersonaByDNI(docente.getDni(), docente.getId())){
         throw new Exception("Ya existe una docente con el DNI ingresado");
         }//fin if*/
        if (docentefacadelocal.findDocenteDni(docente.getDni().trim()) != null) {
            throw new Exception("Ya esxiste una persona con el Dni ingresado");
        }
    }//fin validar

    public static boolean validarDni(String dni) {
        boolean flag = false;
        Pattern patron = Pattern.compile("[0-9]{6,9}");
        Matcher encaja = patron.matcher(dni);
        if (encaja.matches()) {
            flag = true;
            System.out.println("Con " + dni.length() + " digitos");
        } else {
            patron = Pattern.compile("[0-9][0-9].[0-9][0-9][0-9].[0-9][0-9]" + "[0-9]");
            encaja = patron.matcher(dni);
            if (encaja.matches()) {
                flag = true;
            } else {
                patron = Pattern.compile("[0-9].[0-9][0-9][0-9].[0-9][0-9]" + "[0-9]");
                encaja = patron.matcher(dni);
                if (encaja.matches()) {
                    flag = true;
                } else {
                    patron = Pattern.compile("[0-9][0-9][0-9].[0-9][0-9]" + "[0-9]");
                    encaja = patron.matcher(dni);
                    if (encaja.matches()) {
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    @Override
    public List<Docente> findByDocenteDni(String dni) {
        return docentefacadelocal.findByDocenteDni(dni);
    }
}
