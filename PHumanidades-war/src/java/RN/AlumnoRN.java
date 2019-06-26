/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import DAO.AlumnoFacadeLocal;
import Entidades.Persona.Alumno;
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
public class AlumnoRN implements AlumnoRNLocal {

    @EJB
    private AlumnoFacadeLocal alumnoFacadeLocal;

    @Override
    public void create(Alumno alumno) throws Exception {
        this.validar(alumno);
        alumnoFacadeLocal.create(alumno);
    }

    @Override
    public void edit(Alumno alumno) throws Exception {
        alumnoFacadeLocal.edit(alumno);
    }

    @Override
    public void remove(Alumno alumno) throws Exception {
        alumnoFacadeLocal.remove(alumno);
    }

    @Override
    public List<Alumno> findAll() throws Exception {
        return alumnoFacadeLocal.findAll();
    }

    @Override
    public Alumno buscarAlumno(Alumno alumno) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Alumno> buscarAlumnoNombre(String cadena) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Alumno> findLikeNombreApellido(String cadena) throws Exception {
        return alumnoFacadeLocal.findLikeNombreApellido(cadena);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private void validar(Alumno alumno) throws Exception {

        if (alumno.getNombre().isEmpty()) {
            throw new Exception("No ingreso el nombre");
        }//fin if

        if (alumno.getApellido().isEmpty()) {
            throw new Exception("No ingreso el apellido");
        }//fin if

        if (alumno.getDni().isEmpty()) {
            throw new Exception("No ingreso el DNI");
        }//fin if

        if (validarDni(alumno.getDni()) == false) {
            throw new Exception("El Formato del Dni ingresado es incorrecto");
        }

        //  Alumno al = new Alumno();
        //  al = (Alumno) alumnoFacadeLocal.findAlumnoDni(alumno.getDni());
        //  System.out.println("almuno:"+al.getApellido());
        //validamos que no se encuentre cargada la persona
        if (alumnoFacadeLocal.findAlumnoDni(alumno.getDni().trim()) != null) {
            throw new Exception("Ya existe una persona con el DNI ingresado");
        }

    }//fin validar

    @Override
    public Alumno findByAlumnoDni(String dni) throws Exception {
        Alumno al = (Alumno) alumnoFacadeLocal.findAlumnoDni(dni);
        if (al == null) {
            throw new Exception("Alumno Inexistente");
        } else {
            return al;
        }
    }

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

}
