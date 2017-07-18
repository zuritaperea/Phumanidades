/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades.Egresos;

/**
 *
 * @author ruben
 */
public enum RubroPresupuestario {
    
    BIENES_DE_CONSUMO("Bienes de Consumo"),
    SERVICIOS_NO_PERSONALES("Servicios no Personales"),
    BIENES_DE_CAPITAL("Bienes de Capital"),
    TRANSFERENCIAS("Transferencias");
    
    private String name;
    
    private RubroPresupuestario(String name){
        this.name = name;
    }
    
    public String getName(){
       return this.name;
    }
    
    @Override
    public String toString() {
        return name;
    }
}
