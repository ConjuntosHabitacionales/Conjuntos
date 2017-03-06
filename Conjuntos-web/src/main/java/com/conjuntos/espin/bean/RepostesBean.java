/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.bean;

import com.conjuntos.espin.model.Persona;
import com.conjuntos.espin.service.CuentaService;
import com.conjuntos.espin.service.PersonaService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author luis
 */
@Named(value = "repostesBean")
@ViewScoped
public class RepostesBean implements Serializable {

    private List<Persona> habitantes;
    private List<Persona> arrendatarios;
    private List<Persona> deudores;

    @Inject
    private PersonaService personaService;
    @Inject
    private CuentaService cuentaService;

    @PostConstruct
    public void init() {
        this.habitantes = this.personaService.obtenerLista();
        if (this.habitantes == null) {
            this.habitantes = new ArrayList<>();
        }
        this.arrendatarios = this.personaService.obtenerListaArrendatarios();
        if (this.arrendatarios == null) {
            this.arrendatarios = new ArrayList<>();
        }
        this.deudores = this.cuentaService.obtenerDeudores();
        if (this.deudores == null) {
            this.deudores = new ArrayList<>();
        }
    }

    public List<Persona> getHabitantes() {
        return habitantes;
    }

    public void setHabitantes(List<Persona> habitantes) {
        this.habitantes = habitantes;
    }

    public List<Persona> getArrendatarios() {
        return arrendatarios;
    }

    public void setArrendatarios(List<Persona> arrendatarios) {
        this.arrendatarios = arrendatarios;
    }

    public List<Persona> getDeudores() {
        return deudores;
    }

    public void setDeudores(List<Persona> deudores) {
        this.deudores = deudores;
    }
}
