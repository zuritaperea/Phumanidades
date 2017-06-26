/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RN;

import DAO.CarreraFacadeLocal;
import Entidades.Carreras.Carrera;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author vouilloz
 */
@Stateless
public class CarrerasRN implements CarrerasRNLocal {

    @EJB
    private CarreraFacadeLocal carreraFacadeLocal;

    @Override
    public void create(Carrera carrera) throws Exception {
        if (validar(carrera)) {
            carreraFacadeLocal.create(carrera);
        } else {
            throw new Exception("Debe ingresar una descripción");
        }
    }

    @Override
    public void edit(Carrera carrera) throws Exception {
        if (validar(carrera)) {
            carreraFacadeLocal.edit(carrera);
        } else {
            throw new Exception("Debe ingresar una descripción");
        }
    }

    @Override
    public void remove(Carrera carrera) {
        carreraFacadeLocal.remove(carrera);
    }

    @Override
    public void findAll() {
        carreraFacadeLocal.findAll();
    }

    @Override
    public List<Carrera> findCarreras() throws Exception {
        return carreraFacadeLocal.findAll();
    }

    @Override
    public List<Carrera> findCarreraNombre(String nombre) {
        return carreraFacadeLocal.findCarreraNombre(nombre);
    }

    @Override
    public Carrera findByiD(Long id) throws Exception {
        return carreraFacadeLocal.find(id);
    }

    private boolean validar(Carrera carrera) {
        return !carrera.getDescripcion().isEmpty();
    }

}
