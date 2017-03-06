/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conjuntos.espin.bean;

import com.conjuntos.espin.model.Casa;
import com.conjuntos.espin.model.Persona;
import com.conjuntos.espin.service.CasaService;
import com.conjuntos.espin.service.PersonaService;
import com.conjuntos.espin.service.UsuarioService;
import com.conjuntos.espin.util.FacesUtil;
import com.conjuntos.espin.util.SessionUtil;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author luis
 */
@Named(value = "usuarioBean")
@ViewScoped
public class UsuarioBean implements Serializable {

    private Persona persona;
    private Casa casa;
    private String password;

    @Inject
    private UsuarioService usuarioService;
    @Inject
    private PersonaService personaService;
    @Inject
    private CasaService casaService;

    @PostConstruct
    public void init() {
        this.persona = this.personaService.findByCodPersona(new Persona(
                SessionUtil.sessionVarNumeric("codPersona")));
        if (this.persona.getId() == null) {
            this.persona = new Persona();
            this.password = "";
            this.casa = new Casa();
        } else {
            this.password = this.persona.getUsuario().getPassword();
            this.casa = this.persona.getCasa();
            if (this.casa == null) {
                this.casa = new Casa();
            }
        }
    }

    public void editPersonaUsuario(ActionEvent evt) {
        if (this.password.equals(this.persona.getUsuario().getPassword())) {
            Boolean exitoU = this.usuarioService.update(this.persona.getUsuario());
            Boolean exitoP = this.personaService.update(this.persona);
            if (exitoU && exitoP) {
                FacesUtil.addMessageInfo("Se ha actualizado con exito.");
            } else {
                FacesUtil.addMessageError(null, "No de ha actualizado.");
            }
        } else {
            FacesUtil.addMessageError(null, "El password es incorrecto.");
        }
    }

    public void addCasa(ActionEvent evt) {
        Casa registrada = this.casaService.findByNumero(this.casa);
        if (registrada.getCodCasa() == null) {
            this.casaService.insert(this.casa);
            this.persona.setCasa(this.casa);
            this.personaService.insert(this.persona);
            FacesUtil.addMessageInfo("Infomacion completa.");
        } else {
            this.persona.setCasa(registrada);
            this.personaService.insert(this.persona);
            FacesUtil.addMessageInfo("Infomacion completa.");
        }
    }

    public void bloquearArrendatario() {
        this.getPersona().setArrendatario(Boolean.FALSE);
    }

    public void bloquearPropietario() {
        this.getPersona().setPropietario(Boolean.FALSE);
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Casa getCasa() {
        return casa;
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
