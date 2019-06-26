/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import DAO.PersonaFacadeLocal;
import Entidades.Persona.Persona;
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
public class PersonaRN implements PersonaRNLocal {

    @EJB
    private PersonaFacadeLocal personafacadelocal;

    @Override
    public void create(Persona persona) throws Exception {
        //Agregar validaciones
        this.validar(persona);
        personafacadelocal.create(persona);
    }

    @Override
    public void edit(Persona persona) throws Exception {
        //Agregar Validaciones
        this.validar(persona);
        personafacadelocal.edit(persona);
    }

    @Override
    public void remove(Persona persona) throws Exception {
        personafacadelocal.remove(persona);
    }

    @Override
    public List<Persona> findAll() throws Exception {
        return personafacadelocal.findAll();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public Persona buscarPersona(Persona persona) {
        return personafacadelocal.find(persona.getId());
    }

    @Override
    public List<Persona> buscarPersonaNombre(String cadena) throws Exception {

        return personafacadelocal.buscarPersonaNombre(cadena);
    }

    private void validar(Persona persona) throws Exception {

        if (persona.getNombre().isEmpty()) {
            throw new Exception("No ingreso el nombre");
        }//fin if

        if (persona.getApellido().isEmpty()) {
            throw new Exception("No ingreso el apellido");
        }//fin if

        if (persona.getDni().isEmpty()) {
            throw new Exception("No ingreso el DNI");
        }//fin if

        if (validarDni(persona.getDni()) == false) {
            throw new Exception("El Formato del Dni ingresado es incorrecto");
        }
        /* if(personaFacadeLocal.isFindPersonaByNombreApellido(persona.getNombre(), persona.getApellido(), persona.getId(), op)){
         throw new Exception("Ya existe una persona con el nombre y apellido ingresados");
         }//fin if*/

        if (personafacadelocal.FindPersonaByDNI(persona.getDni(), persona.getId())) {
            throw new Exception("Ya existe una persona con el DNI ingresado");
        }//fin if

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
    public List<Persona> findLikeNombreApellido(String cadena) throws Exception {
        return personafacadelocal.buscarPersonaNombre(cadena);
    }

}
