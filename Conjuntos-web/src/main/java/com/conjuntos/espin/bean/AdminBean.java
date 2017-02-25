/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.bean;

import com.conjuntos.espin.model.Persona;
import com.conjuntos.espin.service.PersonaService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author luis
 */
@Named(value = "adminBean")
@Dependent
public class AdminBean implements Serializable{

    private Persona nuevo;
    private Persona selected;
    private List<Persona> personas;
    
    @Inject
    private PersonaService personaService;
    
    @PostConstruct
    public void init(){
        this.nuevo = new Persona();
        this.personas = this.personaService.obtenerLista();
        if(this.personas == null && this.personas.size() == 0){
            this.personas = new ArrayList<>();
        }
    }
    
    public void onRowSelect(SelectEvent event) {
        this.selected = (Persona) event.getObject();
    }

    public Persona getNuevo() {
        return nuevo;
    }

    public void setNuevo(Persona nuevo) {
        this.nuevo = nuevo;
    }

    public Persona getSelected() {
        return selected;
    }

    public void setSelected(Persona selected) {
        this.selected = selected;
    }

    public List<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }

    public PersonaService getPersonaService() {
        return personaService;
    }

    public void setPersonaService(PersonaService personaService) {
        this.personaService = personaService;
    }
    
}
