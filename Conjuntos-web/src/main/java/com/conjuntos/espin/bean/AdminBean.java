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
public class AdminBean implements Serializable {

    private Persona selected;
    private List<Persona> personas;

    @Inject
    private PersonaService personaService;
    @Inject
    private CuentaBean cuenta;

    @PostConstruct
    public void init() {
        this.personas = this.personaService.obtenerLista();
        if (this.personas == null && this.personas.size() == 0) {
            this.personas = new ArrayList<>();
        }
    }

    public void onRowSelect(SelectEvent event) {
        this.selected = (Persona) event.getObject();
        this.cuenta.enviarPersona(this.selected);
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

    public CuentaBean getCuenta() {
        return cuenta;
    }

    public void setCuenta(CuentaBean cuenta) {
        this.cuenta = cuenta;
    }

}
